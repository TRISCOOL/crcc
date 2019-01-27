package com.crcc.common.mapper;

import com.crcc.common.model.EngineeringChangeMonthly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EngineeringChangeMonthlyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EngineeringChangeMonthly record);

    int insertSelective(EngineeringChangeMonthly record);

    EngineeringChangeMonthly selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EngineeringChangeMonthly record);

    int updateByPrimaryKeyWithBLOBs(EngineeringChangeMonthly record);

    int updateByPrimaryKey(EngineeringChangeMonthly record);

    List<EngineeringChangeMonthly> listForPage(@Param("projectName")String projectName,
                                               @Param("projectId")Long projectId,@Param("year")String year,
                                               @Param("quarter")Integer quarter,@Param("offset")Integer offset,
                                               @Param("length")Integer length);

    Integer listForPageSize(@Param("projectName")String projectName,
                            @Param("projectId")Long projectId,@Param("year")String year,
                            @Param("quarter")Integer quarter);

    EngineeringChangeMonthly getDetails(@Param("id")Long id);

    List<EngineeringChangeMonthly> listStatisticsForPage(@Param("projectName")String projectName,
                                                         @Param("projectId")Long projectId,@Param("offset")Integer offset,
                                                         @Param("length")Integer length);

    Integer listStatisticsForPageSize(@Param("projectName")String projectName,
                                      @Param("projectId")Long projectId);
}