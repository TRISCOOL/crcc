package com.crcc.common.mapper;

import com.crcc.common.model.ProjectInfo;

public interface ProjectInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProjectInfo record);

    int insertSelective(ProjectInfo record);

    ProjectInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProjectInfo record);

    int updateByPrimaryKey(ProjectInfo record);
}