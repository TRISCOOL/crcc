package com.crcc.common.mapper;

import com.crcc.common.model.UserRoleRel;

public interface UserRoleRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRel record);

    int insertSelective(UserRoleRel record);

    UserRoleRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleRel record);

    int updateByPrimaryKey(UserRoleRel record);
}