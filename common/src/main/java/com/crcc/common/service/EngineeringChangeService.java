package com.crcc.common.service;

import com.crcc.common.model.EngineerChangeTotal;
import com.crcc.common.model.EngineeringChangeMonthly;

import java.util.List;

public interface EngineeringChangeService {
    Long addEngineeringChangeMonthly(EngineeringChangeMonthly engineeringChangeMonthly);

    boolean updateEngineeringChangeMonthly(EngineeringChangeMonthly engineeringChangeMonthly);

    EngineeringChangeMonthly getDetails(Long id);

    List<EngineeringChangeMonthly> listForPage(Long projectId, String projectName,String year,Integer quarter,
                                               Integer offset,Integer length);

    Integer listForPageSize(String projectName,Long projectId,String year,Integer q);

    List<EngineeringChangeMonthly> listStatisticsForPage(Long projectId, String projectName,Integer offset,Integer length);

    Integer listStatisticsForPageSize(Long projectId, String projectName);

    EngineerChangeTotal getTotal(String projectName,Long projectId,String year,Integer q);

    EngineerChangeTotal getStatisticsTotal(Long projectId,String projectName);

    boolean deleteOneById(Long id);

}
