package com.crcc.common.mapper;

import com.crcc.common.model.SubcontractorResume;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubcontractorResumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SubcontractorResume record);

    int insertSelective(SubcontractorResume record);

    SubcontractorResume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SubcontractorResume record);

    int updateByPrimaryKey(SubcontractorResume record);

    SubcontractorResume getDetails(@Param("id")Long id);

    List<SubcontractorResume> listForPage(@Param("subcontractorName")String subcontractorName,
                                          @Param("projectEvaluation")String projectEvaluation,
                                          @Param("constructionScale")String constructionScale,
                                          @Param("offset")Integer offset,@Param("length")Integer length);

    Integer listForPageSize(@Param("subcontractorName")String subcontractorName,
                            @Param("projectEvaluation")String projectEvaluation,
                            @Param("constructionScale")String constructionScale);
}