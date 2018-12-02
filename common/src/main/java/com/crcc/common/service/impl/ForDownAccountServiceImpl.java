package com.crcc.common.service.impl;

import com.crcc.common.mapper.InspectionAccountMapper;
import com.crcc.common.model.InspectionAccount;
import com.crcc.common.service.ForDownAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<InspectionAccount> listForPage(Long projectId,String projectName, String subcontractorName, Integer valuationType, Date valuationTime, Integer offset, Integer length) {
        return inspectionAccountMapper.listForPage(projectId,projectName,subcontractorName,valuationType,valuationTime,
                offset,length);
    }

    @Override
    public InspectionAccount getDetails(Long inspectionAccountId) {
        return inspectionAccountMapper.getDetails(inspectionAccountId);
    }
}
