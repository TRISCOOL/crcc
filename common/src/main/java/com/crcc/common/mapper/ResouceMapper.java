package com.crcc.common.mapper;

import com.crcc.common.model.Resouce;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResouceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Resouce record);

    int insertSelective(Resouce record);

    Resouce selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resouce record);

    int updateByPrimaryKey(Resouce record);

    List<Resouce> listResourceForRole(@Param("roleId")Long roleId);

    List<Resouce> listResourceForUser(@Param("userId")Long userId);
}