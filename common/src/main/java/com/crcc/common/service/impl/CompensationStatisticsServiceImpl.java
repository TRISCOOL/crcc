package com.crcc.common.service.impl;

import com.crcc.common.mapper.InspectionAccountMapper;
import com.crcc.common.mapper.OutOfContractCompensationStatisticsMapper;
import com.crcc.common.model.OutOfContractCompensationStatistics;
import com.crcc.common.model.OutOfContractCompensationStatisticsTotal;
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

        //改为手动输入
/*        compensationStatistics.forEach(com -> {
            resetAmount(com);
        });*/

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
//            Double sum = compensationStatisticsMapper.getSumPriceByProject(c.getProjectId());
//            if (sum != null){
//                c.setStatisticsTotalAmountContract(new BigDecimal(sum).setScale(2,BigDecimal.ROUND_HALF_UP));
//            }
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

    @Override
    public OutOfContractCompensationStatisticsTotal getTotal(String projectName, String subcontractName, String teamName, String year, Integer quarter, Long projectId) {
        List<OutOfContractCompensationStatistics> outOfContractCompensationStatistics = listForPage(projectName,subcontractName,teamName,year,quarter,null,null,projectId);
        OutOfContractCompensationStatisticsTotal total = new OutOfContractCompensationStatisticsTotal();
        BigDecimal sumTotalAmountContract = new BigDecimal(0);
        BigDecimal sumMechanicalClass = new BigDecimal(0);
        BigDecimal sumSporadicEmployment = new BigDecimal(0);
        BigDecimal sumDailyWorkSubtotal = new BigDecimal(0);
        BigDecimal sumOutIn = new BigDecimal(0);
        BigDecimal sumDisasterDamage = new BigDecimal(0);
        BigDecimal sumWorkStop = new BigDecimal(0);
        BigDecimal sumOther = new BigDecimal(0);
        BigDecimal sumCompensationSubtotal = new BigDecimal(0);
        BigDecimal sumTotal = new BigDecimal(0);
        BigDecimal sumAmountAlreadyDisbursed = new BigDecimal(0);
        BigDecimal sumEstimateMechanicalClass = new BigDecimal(0);
        BigDecimal sumEstimateSporadicEmployment = new BigDecimal(0);
        BigDecimal sumEstimateDailyWorkSubtotal = new BigDecimal(0);
        BigDecimal sumEstimateOutIn = new BigDecimal(0);
        BigDecimal sumEstimateDisasterDamage = new BigDecimal(0);
        BigDecimal sumEstimateWorkStop = new BigDecimal(0);
        BigDecimal sumEstimateOther = new BigDecimal(0);
        BigDecimal sumEstimateCompensationSubtotal = new BigDecimal(0);
        BigDecimal sumEstimateTotal = new BigDecimal(0);
        BigDecimal sumDailyPercentage = new BigDecimal(0);
        BigDecimal sumCompensationPercentage = new BigDecimal(0);
        BigDecimal sumDisbursedPercentage = new BigDecimal(0);
        if (outOfContractCompensationStatistics != null && outOfContractCompensationStatistics.size() > 0){
            for (OutOfContractCompensationStatistics out : outOfContractCompensationStatistics){
                sumTotalAmountContract = Utils.addBigDecimal(sumTotalAmountContract,out.getTotalAmountContract());
                sumMechanicalClass = Utils.addBigDecimal(sumMechanicalClass,out.getMechanicalClass());
                sumSporadicEmployment = Utils.addBigDecimal(sumSporadicEmployment,out.getSporadicEmployment());
                sumDailyWorkSubtotal = Utils.addBigDecimal(sumDailyWorkSubtotal,out.getDailyWorkSubtotal());
                sumOutIn = Utils.addBigDecimal(sumOutIn,out.getOutIn());
                sumDisasterDamage = Utils.addBigDecimal(sumDisasterDamage,out.getDisasterDamage());
                sumWorkStop = Utils.addBigDecimal(sumWorkStop,out.getWorkStop());
                sumOther = Utils.addBigDecimal(sumOther,out.getOther());
                sumCompensationSubtotal = Utils.addBigDecimal(sumCompensationSubtotal,out.getCompensationSubtotal());
                sumTotal = Utils.addBigDecimal(sumTotal,out.getTotal());
                sumAmountAlreadyDisbursed = Utils.addBigDecimal(sumAmountAlreadyDisbursed,out.getAmountAlreadyDisbursed());
                sumEstimateMechanicalClass = Utils.addBigDecimal(sumEstimateMechanicalClass,out.getEstimateMechanicalClass());
                sumEstimateSporadicEmployment = Utils.addBigDecimal(sumEstimateSporadicEmployment,out.getEstimateSporadicEmployment());
                sumEstimateDailyWorkSubtotal = Utils.addBigDecimal(sumEstimateDailyWorkSubtotal,out.getEstimateDailyWorkSubtotal());
                sumEstimateOutIn = Utils.addBigDecimal(sumEstimateOutIn,out.getEstimateOutIn());
                sumEstimateDisasterDamage = Utils.addBigDecimal(sumEstimateDisasterDamage,out.getEstimateDisasterDamage());
                sumEstimateOther = Utils.addBigDecimal(sumEstimateOther,out.getEstimateOther());
                sumEstimateWorkStop = Utils.addBigDecimal(sumEstimateWorkStop,out.getEstimateWorkStop());
                sumEstimateCompensationSubtotal = Utils.addBigDecimal(sumEstimateCompensationSubtotal,out.getEstimateCompensationSubtotal());
                sumEstimateTotal = Utils.addBigDecimal(sumEstimateTotal,out.getEstimateTotal());
            }
            sumDailyPercentage = Utils.computerDivide(sumDailyWorkSubtotal,sumTotal,4);
            total.setSumDailyPercentage(new BigDecimal(sumDailyPercentage.doubleValue()*100));
            sumCompensationPercentage = Utils.computerDivide(sumCompensationSubtotal,sumTotal,4);
            total.setSumCompensationPercentage(new BigDecimal(sumCompensationPercentage.doubleValue()*100));
            sumDisbursedPercentage = Utils.computerDivide(sumAmountAlreadyDisbursed,sumCompensationSubtotal,4);
            total.setSumDisbursedPercentage(new BigDecimal(sumDisbursedPercentage.doubleValue()*100));

            total.setSumTotalAmountContract(sumTotalAmountContract);
            total.setSumMechanicalClass(sumMechanicalClass);
            total.setSumSporadicEmployment(sumSporadicEmployment);
            total.setSumDailyWorkSubtotal(sumDailyWorkSubtotal);
            total.setSumOutIn(sumOutIn);
            total.setSumDisasterDamage(sumDisasterDamage);
            total.setSumWorkStop(sumWorkStop);
            total.setSumOther(sumOther);
            total.setSumCompensationSubtotal(sumCompensationSubtotal);
            total.setSumTotal(sumTotal);
            total.setSumAmountAlreadyDisbursed(sumAmountAlreadyDisbursed);
            total.setSumEstimateSporadicEmployment(sumEstimateSporadicEmployment);
            total.setSumEstimateMechanicalClass(sumEstimateMechanicalClass);
            total.setSumEstimateDailyWorkSubtotal(sumEstimateDailyWorkSubtotal);
            total.setSumEstimateOutIn(sumEstimateOutIn);
            total.setSumEstimateDisasterDamage(sumEstimateDisasterDamage);
            total.setSumEstimateOther(sumEstimateOther);
            total.setSumEstimateWorkStop(sumEstimateWorkStop);
            total.setSumEstimateCompensationSubtotal(sumEstimateCompensationSubtotal);
            total.setSumEstimateTotal(sumEstimateTotal);
        }
        return total;
    }

    @Override
    public OutOfContractCompensationStatisticsTotal getTotalStatistics(String projectName, Long projectId) {
        List<OutOfContractCompensationStatistics> outs = listStatisticsForPage(projectName,projectId,null,null);
        OutOfContractCompensationStatisticsTotal total = new OutOfContractCompensationStatisticsTotal();
        BigDecimal sumStatisticsAlreadySubtotal = new BigDecimal(0);
        BigDecimal sumStatisticsCompensationSubtotal = new BigDecimal(0);
        BigDecimal sumStatisticsDailyWorkSubtotal = new BigDecimal(0);
        BigDecimal sumStatisticsEstimateCompensationSubtotal = new BigDecimal(0);
        BigDecimal sumStatisticsEstimateDailyWorkSubtotal = new BigDecimal(0);
        BigDecimal sumStatisticsEstimateSubtotal = new BigDecimal(0);
        BigDecimal sumStatisticsTotalAmountContract = new BigDecimal(0);
        if (outs != null && outs.size() > 0){
            for (OutOfContractCompensationStatistics out : outs){
                sumStatisticsAlreadySubtotal = Utils.addBigDecimal(sumStatisticsAlreadySubtotal,out.getStatisticsAlreadySubtotal());
                sumStatisticsCompensationSubtotal = Utils.addBigDecimal(sumStatisticsCompensationSubtotal,out.getStatisticsCompensationSubtotal());
                sumStatisticsDailyWorkSubtotal = Utils.addBigDecimal(sumStatisticsDailyWorkSubtotal,out.getStatisticsDailyWorkSubtotal());
                sumStatisticsEstimateCompensationSubtotal = Utils.addBigDecimal(sumStatisticsCompensationSubtotal,out.getStatisticsEstimateCompensationSubtotal());
                sumStatisticsEstimateDailyWorkSubtotal = Utils.addBigDecimal(sumStatisticsEstimateDailyWorkSubtotal,out.getStatisticsEstimateDailyWorkSubtotal());
                sumStatisticsEstimateSubtotal = Utils.addBigDecimal(sumStatisticsEstimateSubtotal,out.getStatisticsEstimateSubtotal());
                sumStatisticsTotalAmountContract = Utils.addBigDecimal(sumStatisticsTotalAmountContract,out.getStatisticsTotalAmountContract());
            }

            total.setSumStatisticsAlreadySubtotal(sumStatisticsAlreadySubtotal);
            total.setSumStatisticsCompensationSubtotal(sumStatisticsCompensationSubtotal);
            total.setSumStatisticsDailyWorkSubtotal(sumStatisticsDailyWorkSubtotal);
            total.setSumStatisticsEstimateCompensationSubtotal(sumStatisticsEstimateCompensationSubtotal);
            total.setSumStatisticsEstimateDailyWorkSubtotal(sumStatisticsEstimateDailyWorkSubtotal);
            total.setSumStatisticsEstimateSubtotal(sumStatisticsEstimateSubtotal);
            total.setSumStatisticsTotalAmountContract(sumStatisticsTotalAmountContract);
        }
        return total;
    }

    @Override
    public boolean deleteOneById(Long id) {
        int result = compensationStatisticsMapper.deleteByPrimaryKey(id);
        if (result != 0){
            return true;
        }
        return false;
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
            //改为手输
           /* Double sum = inspectionAccountMapper.sumPriceByProjectIdAndSubcontractorIdAndLaborAccountId(projectId,subId,laborAccountId);
            if (sum != null){
                compensationStatistics.setTotalAmountContract(new BigDecimal(sum));
            }*/

        }

        //计日工小计
        BigDecimal mechanicalClass = compensationStatistics.getMechanicalClass() == null?new BigDecimal("0"):compensationStatistics.getMechanicalClass();
        BigDecimal employment = compensationStatistics.getSporadicEmployment() == null?new BigDecimal("0"):compensationStatistics.getSporadicEmployment();
        if (mechanicalClass != null && employment != null){
            compensationStatistics.setDailyWorkSubtotal(mechanicalClass.add(employment));
        }

        //赔偿小计
        BigDecimal outIn = compensationStatistics.getOutIn() == null?new BigDecimal("0"):compensationStatistics.getOutIn();
        BigDecimal damage = compensationStatistics.getDisasterDamage() == null?new BigDecimal("0"):compensationStatistics.getDisasterDamage();
        BigDecimal other = compensationStatistics.getOther() == null?new BigDecimal("0"):compensationStatistics.getOther();
        BigDecimal stop = compensationStatistics.getWorkStop() == null?new BigDecimal("0"):compensationStatistics.getWorkStop();
        if (outIn != null && damage != null && other != null && stop != null){
            compensationStatistics.setCompensationSubtotal(outIn.add(damage).add(other).add(stop));
        }

        //已计价金额合计
        BigDecimal totalAmountContract = compensationStatistics.getTotalAmountContract() == null?new BigDecimal("0"):compensationStatistics.getTotalAmountContract();
        BigDecimal dailyWorkSubtotal = compensationStatistics.getDailyWorkSubtotal()== null?new BigDecimal("0"):compensationStatistics.getDailyWorkSubtotal();
        BigDecimal compensationSubtotal = compensationStatistics.getCompensationSubtotal() == null?new BigDecimal("0"):compensationStatistics.getCompensationSubtotal();
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
        BigDecimal amountAlreadyDisbursed = compensationStatistics.getAmountAlreadyDisbursed()== null?new BigDecimal("0"):compensationStatistics.getAmountAlreadyDisbursed();
        if (dailyWorkSubtotal != null && compensationSubtotal != null && amountAlreadyDisbursed != null){
            BigDecimal sum = dailyWorkSubtotal.add(compensationSubtotal);
            compensationStatistics.setDisbursedPercentage(Utils.computerDivide(amountAlreadyDisbursed,sum,4));
        }

        //预估计日工小计
        BigDecimal esMec = compensationStatistics.getEstimateMechanicalClass()== null?new BigDecimal("0"):compensationStatistics.getEstimateMechanicalClass();
        BigDecimal esSe  = compensationStatistics.getEstimateSporadicEmployment()== null?new BigDecimal("0"):compensationStatistics.getEstimateSporadicEmployment();
        BigDecimal esDailyTotal = null;
        if(esMec != null && esSe != null){
            esDailyTotal = esMec.add(esSe);
            compensationStatistics.setEstimateDailyWorkSubtotal(esDailyTotal);
        }

        //预估赔偿小计
        BigDecimal esOutIn = compensationStatistics.getEstimateOutIn()== null?new BigDecimal("0"):compensationStatistics.getEstimateOutIn();
        BigDecimal esDamage = compensationStatistics.getEstimateDisasterDamage()== null?new BigDecimal("0"):compensationStatistics.getEstimateDisasterDamage();
        BigDecimal esOther = compensationStatistics.getEstimateOther()== null?new BigDecimal("0"):compensationStatistics.getEstimateOther();
        BigDecimal esStop = compensationStatistics.getEstimateWorkStop()== null?new BigDecimal("0"):compensationStatistics.getEstimateWorkStop();
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
