package com.crcc.common.mapper;

import com.crcc.common.model.ConfirmationOfRights;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConfirmationOfRightsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ConfirmationOfRights record);

    int insertSelective(ConfirmationOfRights record);

    ConfirmationOfRights selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConfirmationOfRights record);

    int updateByPrimaryKey(ConfirmationOfRights record);

    ConfirmationOfRights foundConfirmByProjectAndYear(@Param("projectId")Long projectId,@Param("year")String year);

    List<ConfirmationOfRights> listForPage(@Param("projectId")Long projectId,@Param("projectName")String projectName,
                                           @Param("year")String year,@Param("quarter")String quarter,
                                           @Param("offset")Integer offset,@Param("length")Integer length);

    Integer listForPageSize(@Param("projectId")Long projectId,@Param("projectName")String projectName,
                            @Param("year")String year,@Param("quarter")String quarter);
}