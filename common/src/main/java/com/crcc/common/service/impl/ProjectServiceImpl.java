package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.ProjectMapper;
import com.crcc.common.model.Project;
import com.crcc.common.service.ProjectService;
import com.crcc.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ProjectServiceImpl implements ProjectService{

    private static String CODE_KEY = "crcc23-3-";
    private static String CODE_NUM = "code_num";

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Long addProject(Project project) {
        project.setCreateTime(new Date());
        project.setStatus(0);
        project.setCode(createCodeForProject());
        int result = projectMapper.insertSelective(project);
        if (result != 0){
            return project.getId();
        }
        return null;
    }

    @Override
    public boolean updateProject(Project project) {
        if (project.getId() == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        project.setUpdateTime(new Date());
        int result = projectMapper.updateByPrimaryKeySelective(project);
        if (result != 0)
            return true;

        return false;
    }

    @Override
    public List<Project> listProjectForPage(String code, String name, Long dictId, Integer status, Integer offset, Integer length) {
        return projectMapper.listProjectForPage(offset,length,status,dictId,name,code);
    }

    @Override
    public List<Project> listProjectForProjectUser(Long userId) {
        return projectMapper.findProjectsByUserId(userId);
    }

    @Override
    public Project getDetails(Long projectId) {
        return projectMapper.getDetails(projectId);
    }

    private String createCodeForProject(){
        Long num =redisService.incrby(CODE_NUM,1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer year = calendar.get(Calendar.YEAR);
        String code = CODE_KEY+year+"-"+getEndNum(num);
        return code;
    }

    private String getEndNum(Long num){
        if (num<10)
            return "00"+num;

        if (num>=10 && num <100)
            return "0"+num;

        return num+"";
    }
}
