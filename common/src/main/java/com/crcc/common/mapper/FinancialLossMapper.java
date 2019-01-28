package com.crcc.common.mapper;

import com.crcc.common.model.FinancialLoss;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinancialLossMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FinancialLoss record);

    int insertSelective(FinancialLoss record);

    FinancialLoss selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinancialLoss record);

    int updateByPrimaryKeyWithBLOBs(FinancialLoss record);

    int updateByPrimaryKey(FinancialLoss record);

    List<FinancialLoss> listForPage(@Param("projectId")Long projectId,@Param("projectName")String projectName,
                                    @Param("year")String year, @Param("quarter")Integer quarter,
                                    @Param("offset")Integer offset, @Param("length")Integer length);

    Integer listForPageSize(@Param("projectId")Long projectId,@Param("projectName")String projectName,
                            @Param("year")String year, @Param("quarter")Integer quarter);
}