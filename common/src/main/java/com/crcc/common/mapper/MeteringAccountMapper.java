package com.crcc.common.mapper;

import com.crcc.common.model.MeteringAccount;

public interface MeteringAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MeteringAccount record);

    int insertSelective(MeteringAccount record);

    MeteringAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MeteringAccount record);

    int updateByPrimaryKey(MeteringAccount record);
}