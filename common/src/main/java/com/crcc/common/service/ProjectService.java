package com.crcc.common.service;

import com.crcc.common.model.Project;

import java.util.List;

public interface ProjectService {
    Long addProject(Project project);

    boolean updateProject(Project project);

    List<Project> listProjectForPage(String code,String name,Long dictId,Integer status,Integer offset,Integer length);

    List<Project> listProjectForProjectUser(Long userId);

    Project getDetails(Long projectId);
}
