package com.crcc.common.mapper;

import com.crcc.common.model.FinancialLoss;

public interface FinancialLossMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FinancialLoss record);

    int insertSelective(FinancialLoss record);

    FinancialLoss selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinancialLoss record);

    int updateByPrimaryKeyWithBLOBs(FinancialLoss record);

    int updateByPrimaryKey(FinancialLoss record);
}