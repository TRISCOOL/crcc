package com.crcc.common.mapper;

import com.crcc.common.model.ProjectEvaluation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectEvaluationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProjectEvaluation record);

    int insertSelective(ProjectEvaluation record);

    ProjectEvaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProjectEvaluation record);

    int updateByPrimaryKey(ProjectEvaluation record);

    ProjectEvaluation getDetailsById(@Param("id")Long id);

    List<ProjectEvaluation> listForPage(@Param("projectId")Long projectId,@Param("projectName") String projectName,
                                        @Param("evaluationStatus") String evaluationStatus,
                                        @Param("engineeringStatus") String engineeringStatus,
                                        @Param("isSign") String isSign,
                                        @Param("isResponsibility") Integer isResponsibility,
                                        @Param("offset")Integer offset,@Param("length")Integer length);

    Integer listForPageSize(@Param("projectId")Long projectId,@Param("projectName") String projectName,
                            @Param("evaluationStatus") String evaluationStatus,
                            @Param("engineeringStatus") String engineeringStatus,
                            @Param("isSign") String isSign,
                            @Param("isResponsibility") Integer isResponsibility);
}