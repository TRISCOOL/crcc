package com.crcc.common.mapper;

import com.crcc.common.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> listRoleForPage(@Param("name")String name,@Param("offset")Integer offset,@Param("length")Integer length);

    Role findRoleByUserId(@Param("userId")Long userId);
}