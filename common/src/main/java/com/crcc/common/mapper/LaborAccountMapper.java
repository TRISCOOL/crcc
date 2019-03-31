package com.crcc.common.mapper;

import com.crcc.common.model.LaborAccount;
import com.crcc.common.model.Subcontractor;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
                                   @Param("contractPerson") String contractPerson,
                                   @Param("offset") Integer offset,@Param("length") Integer length);

    Integer listForPageSize(@Param("projectId")Long projectId, @Param("projectName") String projectName,
                            @Param("subcontractorName") String subcontractorName,
                            @Param("status") Integer status,
                            @Param("approvalFiling") Integer approvalFiling,
                            @Param("contractPerson") String contractPerson);

    List<LaborAccount> onlyList();

    Double getSumContractAmount(@Param("projectId")Long projectId,@Param("subcontractorId")Long subcontractorId,
                                @Param("teamName")String teamName);

    LaborAccount getTeamAccountByMain(@Param("projectId")Long projectId,@Param("subcontractorId")Long subcontractorId,
                                      @Param("teamName")String teamName,@Param("contractType")Integer contractType);


    List<Subcontractor> selectSubcontractorByProject(@Param("projectId")Long projectId);

    List<LaborAccount> selectTeamByProjectAndSub(@Param("projectId")Long projectId,
                                                 @Param("subcontractorId")Long subcontractorId);

    int logicDeleteById(@Param("id")Long id, @Param("updateUser")Long updateUser, @Param("updateTime")Date updateTime);
}