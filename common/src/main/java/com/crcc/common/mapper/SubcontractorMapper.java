package com.crcc.common.mapper;

import com.crcc.common.model.Subcontractor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubcontractorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Subcontractor record);

    int insertSelective(Subcontractor record);

    Subcontractor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Subcontractor record);

    int updateByPrimaryKey(Subcontractor record);

    Subcontractor findDetailsById(@Param("subcontractorId")Long SubcontractorId);

    List<Subcontractor> listForPage(@Param("name") String name,@Param("type") String type,
                                    @Param("professionType") Long professionType,@Param("minAmount") Integer minAmount,
                                    @Param("maxAmount") Integer maxAmount,
                                    @Param("shareEvaluation") String shareEvaluation,
                                    @Param("groupEvaluation") String groupEvaluation,
                                    @Param("companyEvaluation") String companyEvaluation,
                                    @Param("offset") Integer offset,@Param("length") Integer length);

    Integer listForPageSize(@Param("name") String name,@Param("type") String type,
                            @Param("professionType") Long professionType,@Param("minAmount") Integer minAmount,
                            @Param("maxAmount") Integer maxAmount,
                            @Param("shareEvaluation") String shareEvaluation,
                            @Param("groupEvaluation") String groupEvaluation,
                            @Param("companyEvaluation") String companyEvaluation);
}