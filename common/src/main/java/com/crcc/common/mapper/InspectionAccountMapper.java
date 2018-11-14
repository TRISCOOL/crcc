package com.crcc.common.mapper;

import com.crcc.common.model.InspectionAccount;

public interface InspectionAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InspectionAccount record);

    int insertSelective(InspectionAccount record);

    InspectionAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InspectionAccount record);

    int updateByPrimaryKey(InspectionAccount record);
}