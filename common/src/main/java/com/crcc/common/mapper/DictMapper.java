package com.crcc.common.mapper;

import com.crcc.common.model.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    List<Dict> findByType(@Param("type")String type);
}