package com.crcc.common.service;

import com.crcc.common.model.ConfirmationOfRights;

import java.util.List;

public interface ConfirmationOfRightsService {
    ConfirmationOfRights add(ConfirmationOfRights confirmationOfRights);

    boolean updateConfirmationOfRights(ConfirmationOfRights confirmationOfRights);

    List<ConfirmationOfRights> listForPage(Long projectId,String projectName,String year,String quarter,
                                           Integer offset,Integer length);

    Integer listForPageSize(Long projectId,String projectName,String year,String quarter);

    ConfirmationOfRights foundConfirmForLastYear(Long projectId);
}
