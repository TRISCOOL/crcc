package com.crcc.common.mapper;

import com.crcc.common.model.RoleResourceRelKey;

public interface RoleResourceRelMapper {
    int deleteByPrimaryKey(RoleResourceRelKey key);

    int insert(RoleResourceRelKey record);

    int insertSelective(RoleResourceRelKey record);
}