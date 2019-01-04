package com.crcc.common.service.impl;

import com.crcc.common.mapper.InspectionAccountMapper;
import com.crcc.common.model.InspectionAccount;
import com.crcc.common.model.LaborAccount;
import com.crcc.common.service.ForDownAccountService;
import com.crcc.common.service.LaborAccountService;
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
            LaborAccount laborAccount = new LaborAccount();
            laborAccount.setId(inspectionAccount.getLaborAccountId());
            laborAccount.setSettlementAmount(inspectionAccount.getValuationPrice() != null?inspectionAccount.getValuationPrice():new BigDecimal(0));
            laborAccountService.updateSective(laborAccount);
        }

        return inspectionAccount.getId();
    }

    @Override
    public boolean update(InspectionAccount inspectionAccount) {
        int result = inspectionAccountMapper.updateByPrimaryKey(inspectionAccount);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public List<InspectionAccount> listForPage(Long projectId,String projectName, String subcontractorName,
                                               Integer valuationType, Date valuationTime, Integer offset,Integer length,
                                               Double maxUnderRate, Double minUnderRate) {
        return inspectionAccountMapper.listForPage(projectId,projectName,subcontractorName,valuationType,valuationTime,
                minUnderRate,maxUnderRate,offset,length);
    }

    @Override
    public Integer listForPageSize(Long projectId, String projectName, String subcontractorName,
                                   Integer valuationType, Date valuationTime, Double maxUnderRate, Double minUnderRate) {
        return inspectionAccountMapper.listForPageSize(projectId,projectName,subcontractorName,valuationType,minUnderRate,
                maxUnderRate,valuationTime);
    }

    @Override
    public InspectionAccount getDetails(Long inspectionAccountId) {
        return inspectionAccountMapper.getDetails(inspectionAccountId);
    }
}
