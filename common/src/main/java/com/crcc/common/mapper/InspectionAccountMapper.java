package com.crcc.common.mapper;

import com.crcc.common.model.InspectionAccount;
import com.crcc.common.model.InspectionCountForLabor;
import com.crcc.common.model.InspectionCountForProject;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface InspectionAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InspectionAccount record);

    int insertSelective(InspectionAccount record);

    InspectionAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InspectionAccount record);

    int updateByPrimaryKey(InspectionAccount record);

    List<InspectionAccount> listForPage(@Param("projectId")Long projectId,@Param("projectName") String projectName,
                                        @Param("subcontractorName") String subcontractorName,
                                        @Param("valuationType") Integer valuationType,
                                        @Param("valuationTime") String valuationTime,
                                        @Param("minUnderRate") Double minUnderRate,
                                        @Param("maxUnderRate") Double maxUnderRate,
                                        @Param("offset") Integer offset, @Param("length") Integer length,
                                        @Param("teamName")String teamName);

    Integer listForPageSize(@Param("projectId")Long projectId,@Param("projectName") String projectName,
                            @Param("subcontractorName") String subcontractorName,
                            @Param("valuationType") Integer valuationType,
                            @Param("minUnderRate") Double minUnderRate,
                            @Param("maxUnderRate") Double maxUnderRate,
                            @Param("valuationTime") String valuationTime,
                            @Param("teamName")String teamName);

    InspectionAccount getDetails(@Param("inspectionAccountId")Long inspectionAccountId);

    Double sumPriceByProjectIdAndSubcontractorIdAndLaborAccountId(@Param("projectId")Long projectId,
                                                                  @Param("subcontractorId")Long subcontractorId,
                                                                  @Param("laborAccountId")Long laborAccountId);

    List<InspectionAccount> foundInspectionByValuationType(@Param("valuationType")Integer valuationType,
                                                           @Param("laborAccountId")Long laborAccountId,
                                                           @Param("projectId")Long projectId,
                                                           @Param("subcontractorId")Long subcontractorId);

    int logicDeleteById(@Param("id")Long id,@Param("updateUser")Long updateUser,@Param("updateTime")Date updateTime);

    List<InspectionAccount> findInspectionAccountByProjectAndSubAndTeam(@Param("projectId")Long projectId,
                                                                        @Param("subcontractorId")Long subcontractorId,
                                                                        @Param("teamName")String teamName);

    List<InspectionAccount> findByValuationPeriod(@Param("projectId")Long projectId,
                                                  @Param("subcontractorId")Long subcontractorId,
                                                  @Param("laborAccountId")Long laborAccountId,
                                                  @Param("valuationPeriod")Integer valuationPeriod);

    List<InspectionCountForLabor> listInspectionCountForLabor(@Param("subcontractorName")String subcontractorName,
                                                              @Param("projectName")String projectName,
                                                              @Param("teamName")String teamName,
                                                              @Param("offset")Integer offset,
                                                              @Param("length")Integer length,@Param("projectId")Long projectId);

    InspectionAccount getNew(@Param("projectId")Long projectId,@Param("subcontractorId")Long subcontractorId,
                             @Param("laborAccountId")Long laborAccountId);

    Integer listInspectionCountForLaborCount(@Param("subcontractorName")String subcontractorName,
                                             @Param("projectName")String projectName,
                                             @Param("teamName")String teamName,@Param("projectId")Long projectId);

    Integer listInspectionCountForProjectCount(@Param("projectId")Long projectId,@Param("projectName")String projectName);

    List<InspectionCountForProject> listInspectionCountForProject(@Param("projectId")Long projectId,
                                                                  @Param("projectName")String projectName,
                                                                  @Param("offset")Integer offset,
                                                                  @Param("length")Integer length);
}