package com.crcc.common.service;

import com.crcc.common.model.ProjectEvaluation;

import java.util.List;

public interface ProjectEvaluationService {

    Long addEvaluation(ProjectEvaluation projectEvaluation);

    boolean update(ProjectEvaluation projectEvaluation);

    List<ProjectEvaluation> listForPage(Long projectId,String projectName,String evaluationStatus,String engineeringStatus,String isSign,
                                        Integer isResponsibility,Integer offset,Integer length);

    Integer listForPageSize(Long projectId,String projectName,String evaluationStatus,String engineeringStatus,String isSign,
                            Integer isResponsibility);

    ProjectEvaluation getDetails(Long id);
}
