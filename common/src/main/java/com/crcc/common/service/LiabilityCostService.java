package com.crcc.common.service;

import com.crcc.common.model.LiabilityCost;

import java.util.List;

public interface LiabilityCostService {
    Long add(LiabilityCost liabilityCost);

    boolean update(LiabilityCost liabilityCost);

    List<LiabilityCost> listForPage(String projectName,Integer year,Integer month,Integer offset,Integer length);

    Integer listForPageSize(String projectName,Integer year,Integer month);


}
