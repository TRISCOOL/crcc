package com.crcc.common.mapper;

import com.crcc.common.model.Subcontractor;

public interface SubcontractorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Subcontractor record);

    int insertSelective(Subcontractor record);

    Subcontractor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Subcontractor record);

    int updateByPrimaryKey(Subcontractor record);
}