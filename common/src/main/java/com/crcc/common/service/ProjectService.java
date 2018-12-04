package com.crcc.common.service;

import com.crcc.common.model.Project;
import com.crcc.common.model.ProjectInfo;

import java.util.Date;
import java.util.List;

public interface ProjectService {
    Long addProject(Project project);

    boolean updateProject(Project project);

    List<Project> listProjectForPage(String code,String name,Long dictId,Integer status,Integer offset,Integer length);

    Integer listProjectForPageSize(String code,String name,Long dictId,Integer status);

    List<Project> listProjectForProjectUser(Long userId);

    Project getDetails(Long projectId);

    Long addProjectInfo(ProjectInfo projectInfo);

    boolean updateProjectInfo(ProjectInfo projectInfo);

    boolean deleteInfo(Long projectInfoId);

    ProjectInfo getInfo(Long projectInfoId);

    List<ProjectInfo> listProjectInfoForUser(Long projectId,String projectName,Integer status,String projectManager,
                                             String projectSecretary,String chiefEngineer,Date contractStartTime,
                                             Date contractEndTime,Date realContractStartTime,Date realContractEndTime,
                                             Integer offset,Integer length);

    Integer listProjectInfoForUserSize(Long projectId,String projectName,Integer status,String projectManager,
                                       String projectSecretary,String chiefEngineer,Date contractStartTime,
                                       Date contractEndTime,Date realContractStartTime,Date realContractEndTime);
}
