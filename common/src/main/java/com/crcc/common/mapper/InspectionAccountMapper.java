package com.crcc.common.mapper;

import com.crcc.common.model.InspectionAccount;
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
                                        @Param("offset") Integer offset, @Param("length") Integer length);

    Integer listForPageSize(@Param("projectId")Long projectId,@Param("projectName") String projectName,
                            @Param("subcontractorName") String subcontractorName,
                            @Param("valuationType") Integer valuationType,
                            @Param("minUnderRate") Double minUnderRate,
                            @Param("maxUnderRate") Double maxUnderRate,
                            @Param("valuationTime") String valuationTime);

    InspectionAccount getDetails(@Param("inspectionAccountId")Long inspectionAccountId);

    Double sumPriceByProjectIdAndSubcontractorIdAndLaborAccountId(@Param("projectId")Long projectId,
                                                                  @Param("subcontractorId")Long subcontractorId,
                                                                  @Param("laborAccountId")Long laborAccountId);
}