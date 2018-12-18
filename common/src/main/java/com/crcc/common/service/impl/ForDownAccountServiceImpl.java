package com.crcc.common.service.impl;

import com.crcc.common.mapper.InspectionAccountMapper;
import com.crcc.common.model.InspectionAccount;
import com.crcc.common.service.ForDownAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ForDownAccountServiceImpl implements ForDownAccountService{

    @Autowired
    private InspectionAccountMapper inspectionAccountMapper;

    @Override
    public Long addInsepectionAccount(InspectionAccount inspectionAccount) {
        inspectionAccount.setCreateTime(new Date());
        if (inspectionAccount.getValuationPrice() != null && inspectionAccount.getEndedPrice() != null){
            BigDecimal midValue = inspectionAccount.getValuationPrice().add(inspectionAccount.getEndedPrice());
            if (midValue.doubleValue() != 0d){
                inspectionAccount.setUnderRate(inspectionAccount.getValuationPrice().divide(midValue));
            }
        }

        inspectionAccountMapper.insertSelective(inspectionAccount);
        return inspectionAccount.getId();
    }

    @Override
    public boolean update(InspectionAccount inspectionAccount) {
        int result = inspectionAccountMapper.updateByPrimaryKeySelective(inspectionAccount);
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
