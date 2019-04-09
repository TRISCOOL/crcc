package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.InspectionAccountMapper;
import com.crcc.common.model.InspectionAccount;
import com.crcc.common.model.InspectionAccountTotal;
import com.crcc.common.model.LaborAccount;
import com.crcc.common.service.ForDownAccountService;
import com.crcc.common.service.LaborAccountService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ForDownAccountServiceImpl implements ForDownAccountService{

    @Autowired
    private InspectionAccountMapper inspectionAccountMapper;

    @Autowired
    private LaborAccountService laborAccountService;

    @Override
    @Transactional
    public Long addInsepectionAccount(InspectionAccount inspectionAccount) {

        List<InspectionAccount> exists = inspectionAccountMapper.findByValuationPeriod(inspectionAccount.getProjectId(),inspectionAccount.getSubcontractorId(),
                inspectionAccount.getLaborAccountId(),inspectionAccount.getValuationPeriod());
        if (exists != null && exists.size()>0)
            throw new CrccException(ResponseCode.INSPECTION_HAVE_SAME_PERIOD);

        inspectionAccount.setCreateTime(new Date());
        if (inspectionAccount.getValuationPrice() != null && inspectionAccount.getEndedPrice() != null){
            BigDecimal midValue = inspectionAccount.getValuationPrice().add(inspectionAccount.getEndedPrice());
            if (midValue.doubleValue() != 0d){
                inspectionAccount.setUnderRate(inspectionAccount.getValuationPrice().divide(
                        midValue,4,BigDecimal.ROUND_HALF_UP));
            }
        }

        int result = inspectionAccountMapper.insertSelective(inspectionAccount);
        if (result != 0){
/*            LaborAccount laborAccount = new LaborAccount();
            laborAccount.setId(inspectionAccount.getLaborAccountId());
            laborAccount.setSettlementAmount(inspectionAccount.getValuationPrice() != null?inspectionAccount.getValuationPrice():new BigDecimal(0));
            laborAccountService.updateSective(laborAccount);*/
        }

        return inspectionAccount.getId();
    }

    //补充对下验工计价信息
    private void supplementInspectionAccount(InspectionAccount inspectionAccount){
        if (inspectionAccount.getValuationPrice() != null && inspectionAccount.getEndedPrice() != null){
            BigDecimal midValue = inspectionAccount.getValuationPrice().add(inspectionAccount.getEndedPrice());
            if (midValue.doubleValue() != 0d){
                inspectionAccount.setUnderRate(inspectionAccount.getValuationPrice().divide(
                        midValue,4,BigDecimal.ROUND_HALF_UP));
            }
        }
    }

    @Override
    public boolean update(InspectionAccount inspectionAccount) {

        supplementInspectionAccount(inspectionAccount);
        int result = inspectionAccountMapper.updateByPrimaryKeySelective(inspectionAccount);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public List<InspectionAccount> listForPage(Long projectId,String projectName, String subcontractorName,
                                               Integer valuationType, Date valuationTime, Integer offset,Integer length,
                                               Double maxUnderRate, Double minUnderRate,String teamName) {
        String time = DateTimeUtil.getYYYYMM(valuationTime);
        return inspectionAccountMapper.listForPage(projectId,projectName,subcontractorName,valuationType,time,
                minUnderRate,maxUnderRate,offset,length,teamName);
    }

    @Override
    public Integer listForPageSize(Long projectId, String projectName, String subcontractorName,
                                   Integer valuationType, Date valuationTime, Double maxUnderRate, Double minUnderRate,
                                   String teamName) {
        String time = DateTimeUtil.getYYYYMM(valuationTime);
        return inspectionAccountMapper.listForPageSize(projectId,projectName,subcontractorName,valuationType,minUnderRate,
                maxUnderRate,time,teamName);
    }

    @Override
    public InspectionAccount getDetails(Long inspectionAccountId) {
        InspectionAccount inspectionAccount = inspectionAccountMapper.getDetails(inspectionAccountId);
        if (inspectionAccount != null){
            Double sumContract = laborAccountService.getSumContractAmount(inspectionAccount.getProjectId(),
                    inspectionAccount.getSubcontractorId(),inspectionAccount.getTeamName());

            inspectionAccount.setSumContractAmount(sumContract);
        }

        return inspectionAccount;
    }

    @Override
    public InspectionAccountTotal getTotal(Long projectId, String projectName, String subcontractorName,
                                           Integer valuationType, Date valuationTime, Double maxUnderRate,
                                           Double minUnderRate,String teamName) {

        List<InspectionAccount> inspectionAccounts = listForPage(projectId,projectName,
                subcontractorName,valuationType,valuationTime,null,null,maxUnderRate,minUnderRate,teamName);

        InspectionAccountTotal inspectionAccountTotal = new InspectionAccountTotal();
        BigDecimal sumPrice = new BigDecimal(0);
        BigDecimal sumEndPrice = new BigDecimal(0);
        BigDecimal sumValuationPrice = new BigDecimal(0);
        BigDecimal sumValuationPriceReduce = new BigDecimal(0);
        BigDecimal sumWarranty = new BigDecimal(0);
        BigDecimal sumPerformanceBond = new BigDecimal(0);
        BigDecimal sumShouldAmount = new BigDecimal(0);
        BigDecimal sumCompensation = new BigDecimal(0);
        if (inspectionAccounts != null && inspectionAccounts.size() > 0){
            for (InspectionAccount inspectionAccount : inspectionAccounts){
                sumPrice = Utils.addBigDecimal(sumPrice,inspectionAccount.getValuationPrice());
                sumEndPrice = Utils.addBigDecimal(sumEndPrice,inspectionAccount.getEndedPrice());
                sumValuationPrice = Utils.addBigDecimal(sumValuationPrice,inspectionAccount.getValuationPrice());
                sumValuationPriceReduce = Utils.addBigDecimal(sumValuationPriceReduce,inspectionAccount.getValuationPriceReduce());
                sumWarranty = Utils.addBigDecimal(sumWarranty,inspectionAccount.getWarranty());
                sumPerformanceBond = Utils.addBigDecimal(sumPerformanceBond,inspectionAccount.getPerformanceBond());
                sumShouldAmount = Utils.addBigDecimal(sumShouldAmount,inspectionAccount.getShouldAmount());
                sumCompensation = Utils.addBigDecimal(sumCompensation,inspectionAccount.getCompensation());
            }
        }

        inspectionAccountTotal.setSumEndPrice(sumEndPrice);
        inspectionAccountTotal.setSumValuationPrice(sumValuationPrice);
        inspectionAccountTotal.setSumValuationPriceReduce(sumValuationPriceReduce);
        inspectionAccountTotal.setSumWarranty(sumWarranty);
        inspectionAccountTotal.setSumPerformanceBond(sumPerformanceBond);
        inspectionAccountTotal.setSumShouldAmount(sumShouldAmount);
        inspectionAccountTotal.setSumCompensation(sumCompensation);
        if (sumPrice != null && sumEndPrice != null){
            Double sumPre = ExcelUtils.computerDivide(sumPrice,sumPrice.add(sumEndPrice),4);
            inspectionAccountTotal.setPercentage(new BigDecimal(sumPre*100));

        }

        return inspectionAccountTotal;
    }

    @Override
    public boolean logicDeleteById(Long id, Long updateUser, Date updateTime) {
        int result = inspectionAccountMapper.logicDeleteById(id,updateUser,updateTime);
        if (result != 0){
            return true;
        }
        return false;
    }

    @Override
    public List<InspectionAccount> findInspectionAccountByProjectAndSubAndTeam(Long projectId, Long subcontractorId, String teamName) {
        return inspectionAccountMapper.findInspectionAccountByProjectAndSubAndTeam(projectId,subcontractorId,teamName);
    }
}
