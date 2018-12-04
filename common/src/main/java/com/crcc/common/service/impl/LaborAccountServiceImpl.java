package com.crcc.common.service.impl;

import com.crcc.common.mapper.LaborAccountMapper;
import com.crcc.common.model.LaborAccount;
import com.crcc.common.service.LaborAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class LaborAccountServiceImpl implements LaborAccountService{

    @Autowired
    private LaborAccountMapper laborAccountMapper;

    @Override
    public Long addLaborAccount(LaborAccount laborAccount) {
        laborAccount.setCreateTime(new Date());
        laborAccountMapper.insertSelective(laborAccount);
        return laborAccount.getId();
    }

    @Override
    public boolean update(LaborAccount laborAccount) {
        laborAccount.setUpdateTime(new Date());
        int result = laborAccountMapper.updateByPrimaryKeySelective(laborAccount);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public LaborAccount getDetails(Long laborAccountId) {
        return laborAccountMapper.getDetailsById(laborAccountId);
    }

    @Override
    public List<LaborAccount> listLaborAccount(Long projectId,String projectName, String subcontractorName, Integer status, Integer approval, Integer offset, Integer length) {
        return laborAccountMapper.listForPage(projectId,projectName,subcontractorName,status,approval,offset,length);
    }

    @Override
    public Integer listLaborAccountSize(Long projectId, String projectName, String subcontractorName, Integer status, Integer approval) {
        return laborAccountMapper.listForPageSize(projectId,projectName,subcontractorName,status,approval);
    }

    @Override
    public List<LaborAccount> onlyLIst() {
        return laborAccountMapper.onlyList();
    }
}
