package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.ProjectMapper;
import com.crcc.common.mapper.UserMapper;
import com.crcc.common.mapper.UserProjectRelMapper;
import com.crcc.common.mapper.UserRoleRelMapper;
import com.crcc.common.model.*;
import com.crcc.common.service.RedisService;
import com.crcc.common.service.RoleService;
import com.crcc.common.service.UserService;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRoleRelMapper userRoleRelMapper;

    @Autowired
    private UserProjectRelMapper userProjectRelMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public User login(String account, String password) {

        User existUser = userMapper.findUserByAccount(account);
        if (existUser == null)
            throw new CrccException(ResponseCode.NOT_FOUND_USER);

        if (!existUser.getPassword().equals(password))
            throw new CrccException(ResponseCode.PASSWORD_ERROR);

        //删除原来的token
        if (existUser != null && existUser.getToken() != null){
            redisService.delStr(existUser.getToken());
        }

        String token = Utils.getUuid(true);
        existUser.setToken(token);
        int result = userMapper.updateByPrimaryKeySelective(existUser);
        if (result != 0){
            User user = new User();
            user.setToken(token);
            user.setId(existUser.getId());
            user.setName(existUser.getName());
            List<Resouce> resouceList = listResourceForUser(existUser.getId());
            user.setResouces(resouceList);
            user.setPermissions(listPermissionForUser(resouceList));
            redisService.setStr(token,Utils.toJson(user));
            return user;
        }

        return null;
    }

    @Override
    public Long addUser(User user) {
        user.setCreateTime(new Date());
        user.setUuid(Utils.getUuid(true));
        int result = userMapper.insertSelective(user);
        if (result != 0){
            relationUserAndProject(user.getId(),user.getProjects());
            relationUserAndRole(user.getId(),user.getRoleId());
            return user.getId();
        }
        return null;
    }

    public void relationUserAndRole(Long userId,Long roleId){
        //删除原来的角色
        userRoleRelMapper.deleteUserRel(userId);

        //添加角色关系
        UserRoleRel userRoleRel = new UserRoleRel();
        userRoleRel.setRoleId(roleId);
        userRoleRel.setUserId(userId);
        userRoleRelMapper.insertSelective(userRoleRel);

    }

    public void relationUserAndProject(Long userId,List<Project> projects){

        //先清除之前的项目关联
        userProjectRelMapper.deleteByUserId(userId);

        if (projects != null && projects.size()>0){
            for (Project project : projects){
                UserProjectRel userProjectRel = new UserProjectRel();
                userProjectRel.setUserId(userId);
                userProjectRel.setProjectId(project.getId());
                userProjectRelMapper.insertSelective(userProjectRel);
            }
        }
    }

    @Override
    public boolean updateUser(User user) {
        if (user.getId() == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        if (user.getUpdateUser() == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        user.setUpdateTime(new Date());
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result != 0){
            relationUserAndProject(user.getId(),user.getProjects());
            relationUserAndRole(user.getId(),user.getRoleId());
            return true;
        }

        return false;
    }

    @Override
    public boolean updateUserStatus(User user) {
        if (user.getId() == null || user.getUpdateUser() == null || user.getDisable() == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        user.setUpdateTime(new Date());
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long userId) {
        int result = userMapper.deleteByPrimaryKey(userId);
        if (result != 0){
            int relResult = userRoleRelMapper.deleteUserRel(userId);
            userProjectRelMapper.deleteByUserId(userId);
            if (relResult != 0)
                return true;
        }
        return false;
    }

    @Override
    public User getUserDetails(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null){
            //填充项目
            List<Project> projectList = projectMapper.findProjectsByUserId(userId);
            user.setProjects(projectList);

            //填充角色
            Role role = userRoleRelMapper.findRoleByUser(userId);
            user.setRoleId(role.getId());
            user.setRoleDescription(role.getDescription());
            user.setRoleName(role.getName());
        }
        return user;
    }

    @Override
    public List<User> listUser(String projectCode, String projectName, Integer disable, Integer offset, Integer length) {
        return userMapper.listUser(projectCode,projectName,disable,offset,length);
    }

    @Override
    public List<Resouce> listResourceForUser(Long userId) {

        Role role = roleService.findRoleByUserId(userId);
        if (role != null){
            Role result = roleService.getDetailsForRole(role.getId());
            return result.getResouces();
        }

        return null;
    }

    @Override
    public List<String> listPermissionForUser(List<Resouce> list) {
        if (list != null && list.size() > 0){
            List<String> permissions = new ArrayList<String>();
            for (Resouce resouce : list){
                permissions.add(resouce.getPermission());
            }
            return permissions;
        }
        return null;
    }
}
