package com.crcc.common.mapper;

import com.crcc.common.model.Resouce;
import com.crcc.common.model.RoleResourceRelKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleResourceRelMapper {
    int deleteByPrimaryKey(RoleResourceRelKey key);

    int insert(RoleResourceRelKey record);

    int insertSelective(RoleResourceRelKey record);

    int deleteRelByRole(@Param("roleId") Long roleId);
}