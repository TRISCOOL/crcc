package com.crcc.common.mapper;

import com.crcc.common.model.Role;
import com.crcc.common.model.UserRoleRel;
import org.apache.ibatis.annotations.Param;

public interface UserRoleRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRel record);

    int insertSelective(UserRoleRel record);

    UserRoleRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleRel record);

    int updateByPrimaryKey(UserRoleRel record);

    int deleteUserRel(@Param("userId")Long userId);

    Role findRoleByUser(@Param("userId") Long userId);
}