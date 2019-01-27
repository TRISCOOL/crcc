package com.crcc.common.mapper;

import com.crcc.common.model.OutOfContractCompensationStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutOfContractCompensationStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OutOfContractCompensationStatistics record);

    int insertSelective(OutOfContractCompensationStatistics record);

    OutOfContractCompensationStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutOfContractCompensationStatistics record);

    int updateByPrimaryKey(OutOfContractCompensationStatistics record);

    OutOfContractCompensationStatistics getDetailsById(@Param("id")Long id);

    List<OutOfContractCompensationStatistics> listForPage(@Param("projectName")String projectName,
                                                          @Param("subcontractorName")String subcontractorName,
                                                          @Param("teamName")String teamName,
                                                          @Param("year")String year,
                                                          @Param("quarter")Integer quarter,
                                                          @Param("offset")Integer offset,@Param("limit")Integer limit,
                                                          @Param("projectId")Long projectId);

    Integer listForPageSize(@Param("projectName")String projectName,
                            @Param("subcontractorName")String subcontractorName,
                            @Param("teamName")String teamName,
                            @Param("year")String year,
                            @Param("quarter")Integer quarter,
                            @Param("projectId")Long projectId);

    List<OutOfContractCompensationStatistics> listStatisticsForPage(@Param("projectName")String projectName,
                                                                    @Param("projectId")Long projectId,
                                                                    @Param("offset")Integer offset,
                                                                    @Param("length")Integer length);

    Integer listStatisticsForPageSize(@Param("projectName")String projectName,
                                      @Param("projectId")Long projectId);

    Double getSumPriceByProject(@Param("projectId")Long projectId);
}