package com.crcc.common.service;

import com.crcc.common.model.InspectionAccount;

import java.util.Date;
import java.util.List;

public interface ForDownAccountService {

    Long addInsepectionAccount(InspectionAccount inspectionAccount);

    boolean update(InspectionAccount inspectionAccount);

    List<InspectionAccount> listForPage(Long projectId,String projectName, String subcontractorName, Integer valuationType,
                                        Date valuationTime,Integer offset,Integer length);

    InspectionAccount getDetails(Long inspectionAccountId);
}
