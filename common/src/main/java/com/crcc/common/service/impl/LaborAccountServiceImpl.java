package com.crcc.common.service.impl;

import com.crcc.common.mapper.LaborAccountMapper;
import com.crcc.common.model.LaborAccount;
import com.crcc.common.model.Subcontractor;
import com.crcc.common.service.LaborAccountService;
import com.crcc.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class LaborAccountServiceImpl implements LaborAccountService{

    @Autowired
    private LaborAccountMapper laborAccountMapper;

    @Autowired
    private RedisService redisService;

    private static String LABORACCOUNT_CONTRACT_CODE_KEY = "contract_code_key";

    @Override
    public Long addLaborAccount(LaborAccount laborAccount) {
        laborAccount.setCreateTime(new Date());
        laborAccount.setContractCode(getContractCode(laborAccount));
        if (laborAccount.getTeamName() != null){
            laborAccount.setTeamName(laborAccount.getTeamName().trim());
        }
        laborAccountMapper.insertSelective(laborAccount);
        return laborAccount.getId();
    }

    private String getContractCode(LaborAccount laborAccount){
        if (laborAccount != null && laborAccount.getContractType() == 0){
            return laborAccount.getContractCode();
        }

        String label = "";
        Long num = redisService.incrby(LABORACCOUNT_CONTRACT_CODE_KEY+laborAccount.getContractCode(),1);
        if (num<10)
            label = "00"+num;

        if (num >= 10 && num <100)
            label = "0"+num;

        if (num>=100)
            label = num+"";

        return laborAccount.getContractCode()+"-补"+label;
    }

    @Override
    public boolean update(LaborAccount laborAccount) {
        laborAccount.setUpdateTime(new Date());
        if (laborAccount.getTeamName() != null){
            laborAccount.setTeamName(laborAccount.getTeamName().trim());
        }
        int result = laborAccountMapper.updateByPrimaryKey(laborAccount);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public boolean updateSective(LaborAccount laborAccount) {
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

    @Override
    public Double getSumContractAmount(Long projectId, Long subcontractorId, String teamName) {
        return laborAccountMapper.getSumContractAmount(projectId,subcontractorId,teamName);
    }

    @Override
    public List<Subcontractor> selectSubcontractorByProject(Long projectId) {
        return laborAccountMapper.selectSubcontractorByProject(projectId);
    }

    @Override
    public List<LaborAccount> selectTeamByProjectAndSub(Long projectId, Long subcontractorId) {
        return laborAccountMapper.selectTeamByProjectAndSub(projectId,subcontractorId);
    }
}
