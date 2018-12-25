package com.crcc.common.service.impl;

import com.crcc.common.mapper.ProjectEvaluationMapper;
import com.crcc.common.model.ProjectEvaluation;
import com.crcc.common.service.ProjectEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ProjectEvaluationServiceImpl implements ProjectEvaluationService{

    @Autowired
    private ProjectEvaluationMapper projectEvaluationMapper;

    @Override
    public Long addEvaluation(ProjectEvaluation projectEvaluation) {
        projectEvaluation.setCreateTime(new Date());
        projectEvaluationMapper.insertSelective(projectEvaluation);
        return projectEvaluation.getId();
    }

    @Override
    public boolean update(ProjectEvaluation projectEvaluation) {
        projectEvaluation.setUpdateTime(new Date());
        int result = projectEvaluationMapper.updateByPrimaryKeySelective(projectEvaluation);
        if (result != 0){
            return true;
        }
        return false;
    }

    @Override
    public List<ProjectEvaluation> listForPage(Long projectId,String projectName, String evaluationStatus, String engineeringStatus,
                                               String isSign, Integer isResponsibility,Integer offset,Integer length) {
        return projectEvaluationMapper.listForPage(projectId,projectName,evaluationStatus,engineeringStatus,isSign,isResponsibility,
                offset,length);
    }

    @Override
    public Integer listForPageSize(Long projectId, String projectName, String evaluationStatus, String engineeringStatus, String isSign, Integer isResponsibility) {
        return projectEvaluationMapper.listForPageSize(projectId,projectName,evaluationStatus,engineeringStatus,isSign,isResponsibility);
    }

    @Override
    public ProjectEvaluation getDetails(Long id) {
        return projectEvaluationMapper.getDetailsById(id);
    }
}
