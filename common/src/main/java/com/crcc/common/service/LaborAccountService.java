package com.crcc.common.service;

import com.crcc.common.model.LaborAccount;
import com.crcc.common.model.Subcontractor;

import java.util.List;

public interface LaborAccountService {

    Long addLaborAccount(LaborAccount laborAccount);

    boolean update(LaborAccount laborAccount);

    boolean updateSective(LaborAccount laborAccount);

    LaborAccount getDetails(Long laborAccountId);

    List<LaborAccount> listLaborAccount(Long projectId,String projectName,String subcontractorName,Integer status,Integer approval,
                                        Integer offset,Integer length);

    Integer listLaborAccountSize(Long projectId,String projectName,String subcontractorName,Integer status,Integer approval);

    List<LaborAccount> onlyLIst();

    Double getSumContractAmount(Long projectId,Long subcontractorId,String teamName);

    List<Subcontractor> selectSubcontractorByProject(Long projectId);

    List<LaborAccount> selectTeamByProjectAndSub(Long projectId,Long subcontractorId);
}
