package com.crcc.common.mapper;

import com.crcc.common.model.MeteringAccount;
import com.crcc.common.model.MeteringAccountForProjectCount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeteringAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MeteringAccount record);

    int insertSelective(MeteringAccount record);

    MeteringAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MeteringAccount record);

    int updateByPrimaryKey(MeteringAccount record);

    MeteringAccount getDetailsById(@Param("mcId")Long mcId);

    List<MeteringAccount> listMeteringAccountForPage(@Param("projectId") Long projectId,
                                                     @Param("projectName") String projectName,
                                                     @Param("meteringTime") String meteringTime,
                                                     @Param("minPayProportion") Double minPayProportion,
                                                     @Param("maxPayProportion") Double maxPayProportion,
                                                     @Param("minProductionValue") Double minProductionValue,
                                                     @Param("maxProductionValue") Double maxProductionValue,
                                                     @Param("offset") Integer offset,@Param("length") Integer length);

    Integer listMeteringAccountForPageSize(@Param("projectId") Long projectId,
                                           @Param("projectName") String projectName,
                                           @Param("meteringTime") String meteringTime,
                                           @Param("minPayProportion") Double minPayProportion,
                                           @Param("maxPayProportion") Double maxPayProportion,
                                           @Param("minProductionValue") Double minProductionValue,
                                           @Param("maxProductionValue") Double maxProductionValue);

    int logicDeletedById(@Param("id")Long id,
                         @Param("updateUser")Long updateUser,
                         @Param("updateTime")Date updateTime);

    List<MeteringAccountForProjectCount> listCountForProject(@Param("projectId")Long projectId,
                                                             @Param("projectName")String projectName,
                                                             @Param("offset")Integer offset,@Param("length")Integer length);

    Integer listCountForProjectCount(@Param("projectId")Long projectId,@Param("projectName")String projectName);
}