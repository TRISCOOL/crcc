package com.crcc.common.mapper;

import com.crcc.common.model.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    List<Project> findProjectsByUserId(@Param("userId")Long userId);

    List<Project> onlyList();

    List<Project> listProjectForPage(@Param("offset")Integer offset,@Param("length")Integer length,
                                     @Param("status")Integer status,@Param("dictId")Long dictId,
                                     @Param("projectName")String projectName,@Param("code")String code);

    Integer listProjectForPageSize(@Param("status")Integer status,@Param("dictId")Long dictId,
                                   @Param("projectName")String projectName,@Param("code")String code);

    Project getDetails(@Param("projectId")Long projectId);
}