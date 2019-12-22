package com.crcc.common.service;

import com.crcc.common.model.InspectionAccount;
import com.crcc.common.model.InspectionAccountTotal;
import com.crcc.common.model.InspectionCountForLabor;
import com.crcc.common.model.InspectionCountForProject;

import java.util.Date;
import java.util.List;

public interface ForDownAccountService {

    Long addInsepectionAccount(InspectionAccount inspectionAccount);

    boolean update(InspectionAccount inspectionAccount);

    List<InspectionAccount> listForPage(Long projectId,String projectName, String subcontractorName, Integer valuationType,
                                        Date valuationTime,Integer offset,Integer length,Double maxUnderRate,
                                        Double minUnderRate,String teamName);

    Integer listForPageSize(Long projectId,String projectName, String subcontractorName, Integer valuationType,
                            Date valuationTime,Double maxUnderRate,Double minUnderRate,String teamName);

    InspectionAccount getDetails(Long inspectionAccountId);

    InspectionAccountTotal getTotal(Long projectId,String projectName, String subcontractorName, Integer valuationType,
                                    Date valuationTime,Double maxUnderRate,Double minUnderRate,String teamName);

    boolean logicDeleteById(Long id,Long updateUser,Date updateTime);

    List<InspectionAccount> findInspectionAccountByProjectAndSubAndTeam(Long projectId,Long subcontractorId,String teamName);

    List<InspectionCountForLabor> listCountForLabor(String projectName,String subcontractorName,String teamName,
                                                    Integer offset,Integer length,Long projectId);

    Integer listCountForLaborCount(String projectName,String subcontractorName,String teamName,Long projectId);

    List<InspectionCountForProject> listInspectionCountForProject(Long projectId,String projectName,Integer offset,Integer length);

    Integer listInspectionCountForProjectCount(Long projectId,String projectName);
}
