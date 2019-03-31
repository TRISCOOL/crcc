package com.crcc.common.service;

import com.crcc.common.model.LaborAccount;
import com.crcc.common.model.LaborAccountTotal;
import com.crcc.common.model.Subcontractor;

import java.util.Date;
import java.util.List;

public interface LaborAccountService {

    Long addLaborAccount(LaborAccount laborAccount);

    boolean update(LaborAccount laborAccount);

    boolean updateSective(LaborAccount laborAccount);

    LaborAccount getDetails(Long laborAccountId);

    List<LaborAccount> listLaborAccount(Long projectId,String projectName,String subcontractorName,Integer status,Integer approval,
                                        String contractPerson,Integer offset,Integer length);

    Integer listLaborAccountSize(Long projectId,String projectName,String subcontractorName,Integer status,Integer approval,
                                 String contractPerson);

    LaborAccountTotal getTotal(Long projectId,String projectName,String subcontractorName,Integer status,Integer approval,
                               String contractPerson);

    List<LaborAccount> onlyLIst();

    Double getSumContractAmount(Long projectId,Long subcontractorId,String teamName);

    List<Subcontractor> selectSubcontractorByProject(Long projectId);

    List<LaborAccount> selectTeamByProjectAndSub(Long projectId,Long subcontractorId);

    boolean logicDeleteById(Long id, Long updateUser, Date updateTime);

    LaborAccount getTeamByContract(Long projectId,Long subcontractorId,String teamName,Integer contractType);
}
