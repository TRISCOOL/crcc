package com.crcc.common.mapper;

import com.crcc.common.model.Resouce;

public interface ResouceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Resouce record);

    int insertSelective(Resouce record);

    Resouce selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resouce record);

    int updateByPrimaryKey(Resouce record);
}