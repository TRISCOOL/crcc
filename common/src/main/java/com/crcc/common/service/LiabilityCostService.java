package com.crcc.common.service;

import com.crcc.common.model.LiabilityCost;
import com.crcc.common.model.LiabilityCostForList;

import java.util.List;

public interface LiabilityCostService {
    Long add(LiabilityCost liabilityCost);

    boolean update(LiabilityCost liabilityCost);

    List<LiabilityCostForList> listForPage(Long projectId, String projectName, Integer year, Integer month, Integer offset, Integer length);

    Integer listForPageSize(Long projectId,String projectName,Integer year,Integer month);


}
