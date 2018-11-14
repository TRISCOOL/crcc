package com.crcc.common.mapper;

import com.crcc.common.model.Personnel;

public interface PersonnelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Personnel record);

    int insertSelective(Personnel record);

    Personnel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Personnel record);

    int updateByPrimaryKey(Personnel record);
}