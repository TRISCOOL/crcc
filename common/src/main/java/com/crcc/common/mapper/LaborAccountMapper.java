package com.crcc.common.mapper;

import com.crcc.common.model.LaborAccount;

public interface LaborAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaborAccount record);

    int insertSelective(LaborAccount record);

    LaborAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaborAccount record);

    int updateByPrimaryKey(LaborAccount record);
}