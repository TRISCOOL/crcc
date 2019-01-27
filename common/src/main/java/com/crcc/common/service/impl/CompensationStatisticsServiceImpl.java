package com.crcc.common.service.impl;

import com.crcc.common.mapper.InspectionAccountMapper;
import com.crcc.common.mapper.OutOfContractCompensationStatisticsMapper;
import com.crcc.common.model.OutOfContractCompensationStatistics;
import com.crcc.common.service.CompensationStatisticsService;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class CompensationStatisticsServiceImpl implements CompensationStatisticsService{

    @Autowired
    private OutOfContractCompensationStatisticsMapper compensationStatisticsMapper;

    @Autowired
    private InspectionAccountMapper inspectionAccountMapper;

    @Override
    public Long addCompensationStatistics(OutOfContractCompensationStatistics contractCompensationStatistics) {
        contractCompensationStatistics.setCreateTime(new Date());
        supplement(contractCompensationStatistics);

        compensationStatisticsMapper.insertSelective(contractCompensationStatistics);
        return contractCompensationStatistics.getId();
    }

    @Override
    public OutOfContractCompensationStatistics getDetailsById(Long id) {
        OutOfContractCompensationStatistics compensationStatistics = compensationStatisticsMapper.getDetailsById(id);
        if (compensationStatistics != null){
            Double sum = inspectionAccountMapper.sumPriceByProjectIdAndSubcontractorIdAndLaborAccountId(compensationStatistics.getProjectId(),
                    compensationStatistics.getSubcontractorId(),compensationStatistics.getLaborAccountId());

            if (sum != null){
                resetAmount(compensationStatistics);
            }
        }

        return compensationStatistics;
    }

    @Override
    public boolean updateCompensationStatistics(OutOfContractCompensationStatistics outOfContractCompensationStatistics) {
        outOfContractCompensationStatistics.setUpdateTime(new Date());
        supplement(outOfContractCompensationStatistics);

        int result = compensationStatisticsMapper.updateByPrimaryKeySelective(outOfContractCompensationStatistics);
        if (result > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<OutOfContractCompensationStatistics> listForPage(String projectName,
                                                                 String subcontractName,
                                                                 String teamName, String year, Integer quarter,
                                                                 Integer offset, Integer limit,Long projectId) {

        List<OutOfContractCompensationStatistics> compensationStatistics =
                compensationStatisticsMapper.listForPage(projectName,subcontractName,teamName,year,quarter,offset,limit,projectId);

        if (compensationStatistics == null)
            return null;

        compensationStatistics.forEach(com -> {
            resetAmount(com);
        });

        return compensationStatistics;
    }

    private void resetAmount(OutOfContractCompensationStatistics com){
        Double sum = inspectionAccountMapper.sumPriceByProjectIdAndSubcontractorIdAndLaborAccountId(com.getProjectId(),
                com.getSubcontractorId(),com.getLaborAccountId());

        if (sum != null){
            BigDecimal sumResult = new BigDecimal(sum);
            com.setTotalAmountContract(sumResult.setScale(2,BigDecimal.ROUND_HALF_UP));
            if (com.getDailyWorkSubtotal() != null && com.getCompensationSubtotal() != null)
                com.setTotal(sumResult.add(com.getDailyWorkSubtotal()).add(com.getCompensationSubtotal()).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
    }

    @Override
    public Integer listForPageSize(String projectName, String subcontractName, String teamName, String year,
                                   Integer quarter,Long projectId) {
        return compensationStatisticsMapper.listForPageSize(projectName,subcontractName,teamName,year,quarter,projectId);
    }

    @Override
    public List<OutOfContractCompensationStatistics> listStatisticsForPage(String projectName, Long projectId, Integer offset, Integer length) {

        List<OutOfContractCompensationStatistics> compensationStatistics =
                compensationStatisticsMapper.listStatisticsForPage(projectName,projectId,offset,length);

        compensationStatistics.forEach(c->{
            Double sum = compensationStatisticsMapper.getSumPriceByProject(c.getProjectId());
            c.setStatisticsTotalAmountContract(new BigDecimal(sum).setScale(2,BigDecimal.ROUND_HALF_UP));
            if (c.getStatisticsDailyWorkSubtotal() != null && c.getStatisticsCompensationSubtotal() != null){
                c.setStatisticsAlreadySubtotal(c.getStatisticsDailyWorkSubtotal().add(c.getStatisticsCompensationSubtotal()));
            }

            if (c.getStatisticsEstimateDailyWorkSubtotal() != null &&
                    c.getStatisticsEstimateCompensationSubtotal() != null){
                c.setStatisticsEstimateSubtotal(c.getStatisticsEstimateDailyWorkSubtotal().add(c.getStatisticsEstimateCompensationSubtotal()));
            }

        });
        return compensationStatistics;
    }

    @Override
    public Integer listStatisticsForPageSize(String projectName, Long projectId) {
        return compensationStatisticsMapper.listStatisticsForPageSize(projectName,projectId);
    }

    private void supplement(OutOfContractCompensationStatistics compensationStatistics){

        if (compensationStatistics == null)
            return;


        if (compensationStatistics.getReportTime() != null){
            compensationStatistics.setQuarter(Utils.getQuarter(compensationStatistics.getReportTime()));
        }

        Long projectId = compensationStatistics.getProjectId();
        Long subId = compensationStatistics.getSubcontractorId();
        Long laborAccountId = compensationStatistics.getLaborAccountId();
        //合同内计量
        if (projectId != null && subId != null && laborAccountId != null){
            Double sum = inspectionAccountMapper.sumPriceByProjectIdAndSubcontractorIdAndLaborAccountId(projectId,subId,laborAccountId);
            compensationStatistics.setTotalAmountContract(new BigDecimal(sum));
        }

        //计日工小计
        BigDecimal mechanicalClass = compensationStatistics.getMechanicalClass();
        BigDecimal employment = compensationStatistics.getSporadicEmployment();
        if (mechanicalClass != null && employment != null){
            compensationStatistics.setDailyWorkSubtotal(mechanicalClass.add(employment));
        }

        //赔偿小计
        BigDecimal outIn = compensationStatistics.getOutIn();
        BigDecimal damage = compensationStatistics.getDisasterDamage();
        BigDecimal other = compensationStatistics.getOther();
        BigDecimal stop = compensationStatistics.getWorkStop();
        if (outIn != null && damage != null && other != null && stop != null){
            compensationStatistics.setCompensationSubtotal(outIn.add(damage).add(other).add(stop));
        }

        //已计价金额合计
        BigDecimal totalAmountContract = compensationStatistics.getTotalAmountContract();
        BigDecimal dailyWorkSubtotal = compensationStatistics.getDailyWorkSubtotal();
        BigDecimal compensationSubtotal = compensationStatistics.getCompensationSubtotal();
        BigDecimal total = null;
        if (totalAmountContract != null && dailyWorkSubtotal != null && compensationSubtotal != null){
            total = totalAmountContract.add(dailyWorkSubtotal).add(compensationSubtotal);
            compensationStatistics.setTotal(total);
        }

        //计日工占已计价金额比例
        if (total != null && dailyWorkSubtotal != null){
            compensationStatistics.setDailyPercentage(Utils.computerDivide(dailyWorkSubtotal,total,4));
        }

        //合同外补偿/赔偿占已计价金额比例
        if (total != null && compensationSubtotal != null){
            compensationStatistics.setCompensationPercentage(Utils.computerDivide(compensationSubtotal,total,4));
        }

        //计日工及补偿已拨付金额拨付率
        BigDecimal amountAlreadyDisbursed = compensationStatistics.getAmountAlreadyDisbursed();
        if (dailyWorkSubtotal != null && compensationSubtotal != null && amountAlreadyDisbursed != null){
            BigDecimal sum = dailyWorkSubtotal.add(compensationSubtotal);
            compensationStatistics.setDisbursedPercentage(Utils.computerDivide(amountAlreadyDisbursed,sum,4));
        }

        //预估计日工小计
        BigDecimal esMec = compensationStatistics.getEstimateMechanicalClass();
        BigDecimal esSe  = compensationStatistics.getEstimateSporadicEmployment();
        BigDecimal esDailyTotal = null;
        if(esMec != null && esSe != null){
            esDailyTotal = esMec.add(esSe);
            compensationStatistics.setEstimateDailyWorkSubtotal(esDailyTotal);
        }

        //预估赔偿小计
        BigDecimal esOutIn = compensationStatistics.getEstimateOutIn();
        BigDecimal esDamage = compensationStatistics.getEstimateDisasterDamage();
        BigDecimal esOther = compensationStatistics.getEstimateOther();
        BigDecimal esStop = compensationStatistics.getEstimateWorkStop();
        BigDecimal esCompensationSubtotal = null;
        if (esOutIn != null && esDamage != null && esOther != null && esStop != null){
            esCompensationSubtotal = esOutIn.add(esDamage).add(esOther).add(esStop);
            compensationStatistics.setEstimateCompensationSubtotal(esCompensationSubtotal);
        }

        //预估合计
        if (esCompensationSubtotal != null && esDailyTotal != null){
            compensationStatistics.setEstimateTotal(esCompensationSubtotal.add(esDailyTotal));
        }



    }
}
