package com.crcc.common.mapper;

import com.crcc.common.model.LiabilityCost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LiabilityCostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LiabilityCost record);

    int insertSelective(LiabilityCost record);

    LiabilityCost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiabilityCost record);

    int updateByPrimaryKeyWithBLOBs(LiabilityCost record);

    int updateByPrimaryKey(LiabilityCost record);

    List<LiabilityCost> listForPage(@Param("projectId")Long projectId,@Param("projectName")String projectName,
                                    @Param("year")Integer year,@Param("mon")Integer mon,
                                    @Param("offset")Integer offset, @Param("length")Integer length);

    Integer listForPageSize(@Param("projectId")Long projectId,@Param("projectName")String projectName,
                            @Param("year")Integer year, @Param("mon")Integer mon);
}