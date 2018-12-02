package com.crcc.common.mapper;

import com.crcc.common.model.LaborAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LaborAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaborAccount record);

    int insertSelective(LaborAccount record);

    LaborAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LaborAccount record);

    int updateByPrimaryKey(LaborAccount record);

    LaborAccount getDetailsById(@Param("laborAccountId")Long laborAccountId);

    List<LaborAccount> listForPage(@Param("projectId")Long projectId, @Param("projectName") String projectName,
                                   @Param("subcontractorName") String subcontractorName,
                                   @Param("status") Integer status,
                                   @Param("approvalFiling") Integer approvalFiling,
                                   @Param("offset") Integer offset,@Param("length") Integer length);

    List<LaborAccount> onlyList();
}