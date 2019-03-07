package com.crcc.common.mapper;

import com.crcc.common.model.LiabilityCost;

public interface LiabilityCostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LiabilityCost record);

    int insertSelective(LiabilityCost record);

    LiabilityCost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiabilityCost record);

    int updateByPrimaryKeyWithBLOBs(LiabilityCost record);

    int updateByPrimaryKey(LiabilityCost record);
}