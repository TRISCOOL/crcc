package com.crcc.common.mapper;

import com.crcc.common.model.ProjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProjectInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProjectInfo record);

    int insertSelective(ProjectInfo record);

    ProjectInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProjectInfo record);

    int updateByPrimaryKey(ProjectInfo record);

    List<ProjectInfo> projectInfoListByUser(@Param("userId")Long userId,
                                            @Param("projectName")String projectName, @Param("status")Integer status,
                                            @Param("projectManager")String projectManager,
                                            @Param("projectSecretary")String projectSecretary,
                                            @Param("chiefEngineer")String chiefEngineer,
                                            @Param("contractStartTime")Date contractStartTime,
                                            @Param("contractEndTime")Date contractEndTime,
                                            @Param("realContractStartTime")Date realContractStartTime,
                                            @Param("realContractEndTime")Date realContractEndTime,
                                            @Param("offset")Integer offset,@Param("length")Integer length);

    ProjectInfo getDetails(@Param("projectInfoId")Long projectInfoId);
}