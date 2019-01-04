package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.ProjectInfoMapper;
import com.crcc.common.mapper.ProjectMapper;
import com.crcc.common.mapper.UserMapper;
import com.crcc.common.model.Project;
import com.crcc.common.model.ProjectInfo;
import com.crcc.common.model.User;
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

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private UserMapper userMapper;

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
    public Integer listProjectForPageSize(String code, String name, Long dictId, Integer status) {
        return projectMapper.listProjectForPageSize(status,dictId,name,code);
    }

    @Override
    public List<Project> listProjectForProjectUser(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null && user.getType() == 0){
            return projectMapper.onlyList();
        }
        return projectMapper.findProjectsByUserId(userId);
    }

    @Override
    public Project getDetails(Long projectId) {
        return projectMapper.getDetails(projectId);
    }

    @Override
    public Long addProjectInfo(ProjectInfo projectInfo) {

        ProjectInfo exisit = projectInfoMapper.getInfoByProjectId(projectInfo.getProjectId());
        if (exisit != null){
            throw new CrccException(ResponseCode.PROJECT_INFO_EXIST);
        }

        projectInfo.setCreateTime(new Date());
        projectInfoMapper.insertSelective(projectInfo);
        return projectInfo.getId();
    }

    @Override
    public boolean updateProjectInfo(ProjectInfo projectInfo) {
        projectInfo.setUpdateTime(new Date());
        int result = projectInfoMapper.updateByPrimaryKey(projectInfo);
        if (result != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteInfo(Long projectInfoId) {
        int result = projectInfoMapper.deleteByPrimaryKey(projectInfoId);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public ProjectInfo getInfo(Long projectInfoId) {
        return projectInfoMapper.getDetails(projectInfoId);
    }

    @Override
    public List<ProjectInfo> listProjectInfoForUser(Long projectId, String projectName, Integer status, String projectManager, String projectSecretary, String chiefEngineer, Date contractStartTime, Date contractEndTime, Date realContractStartTime, Date realContractEndTime, Integer offset, Integer length) {
        return projectInfoMapper.projectInfoListByUser(projectId,projectName,status,projectManager,
                projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime,offset,length);
    }

    @Override
    public Integer listProjectInfoForUserSize(Long projectId, String projectName, Integer status, String projectManager, String projectSecretary, String chiefEngineer, Date contractStartTime, Date contractEndTime, Date realContractStartTime, Date realContractEndTime) {
        return projectInfoMapper.projectInfoListByUserSize(projectId,projectName,status,projectManager,
                projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime);
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
