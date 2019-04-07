package com.crcc.common.service;

import com.crcc.common.model.InspectionAccount;
import com.crcc.common.model.InspectionAccountTotal;

import java.util.Date;
import java.util.List;

public interface ForDownAccountService {

    Long addInsepectionAccount(InspectionAccount inspectionAccount);

    boolean update(InspectionAccount inspectionAccount);

    List<InspectionAccount> listForPage(Long projectId,String projectName, String subcontractorName, Integer valuationType,
                                        Date valuationTime,Integer offset,Integer length,Double maxUnderRate,
                                        Double minUnderRate);

    Integer listForPageSize(Long projectId,String projectName, String subcontractorName, Integer valuationType,
                            Date valuationTime,Double maxUnderRate,Double minUnderRate);

    InspectionAccount getDetails(Long inspectionAccountId);

    InspectionAccountTotal getTotal(Long projectId,String projectName, String subcontractorName, Integer valuationType,
                                    Date valuationTime,Double maxUnderRate,Double minUnderRate);

    boolean logicDeleteById(Long id,Long updateUser,Date updateTime);

    List<InspectionAccount> findInspectionAccountByProjectAndSubAndTeam(Long projectId,Long subcontractorId,String teamName);
}
