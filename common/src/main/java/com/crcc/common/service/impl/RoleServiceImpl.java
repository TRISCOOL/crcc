package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.ResouceMapper;
import com.crcc.common.mapper.RoleMapper;
import com.crcc.common.mapper.RoleResourceRelMapper;
import com.crcc.common.model.Resouce;
import com.crcc.common.model.Role;
import com.crcc.common.model.RoleResourceRelKey;
import com.crcc.common.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class RoleServiceImpl implements RoleService{

    private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceRelMapper resourceRelMapper;

    @Autowired
    private ResouceMapper resouceMapper;

    @Override
    public Long addRole(Role role) {
        role.setCreateTime(new Date());
        role.setDisable(0);

        if (role.getCreateUserId() == null){
            logger.error("add role createUser is null");
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);
        }

        int result = roleMapper.insertSelective(role);
        if (result != 0){
            return role.getId();
        }
        return null;
    }

    @Override
    public boolean updateRole(Role role) {
        if (role.getUpdateUserId() == null){
            logger.error("update role updateUser is null");
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);
        }

        role.setUpdateTime(new Date());

        int result = roleMapper.updateByPrimaryKeySelective(role);
        if (result != 0){
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteRole(Long id) {

        int result = roleMapper.deleteByPrimaryKey(id);
        if (result != 0){
            int deleteResult = resourceRelMapper.deleteRelByRole(id);
            if (deleteResult != 0)
                return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean settingPermissions(Role role) {

        //先删除原来的权限
        resourceRelMapper.deleteRelByRole(role.getId());

        if (role.getResouces() != null && role.getResouces().size() > 0){
            //添加权限
            for (Resouce resouce:role.getResouces()){
                Resouce resouce1 = resouceMapper.findResourceByPermissions(resouce.getPermission());
                if (resouce1 != null){
                    RoleResourceRelKey resourceRelKey = new RoleResourceRelKey();
                    resourceRelKey.setRoleId(role.getId());
                    resourceRelKey.setResourceId(resouce1.getId());
                    resourceRelMapper.insertSelective(resourceRelKey);
                }
            }
        }

        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);

        return true;
    }

    @Override
    public Role getDetailsForRole(Long roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if (role == null)
            return null;
        List<Resouce> resouceList = resouceMapper.listResourceForRole(roleId);
        role.setResouces(resouceList);
        return role;
    }

    @Override
    public Role onlyGetRole(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<Role> listAllRole(String name, Integer offset, Integer length) {
        return roleMapper.listRoleForPage(name,offset,length);
    }

    @Override
    public Integer listAllRoleSize(String name) {
        return roleMapper.listRoleForPageSize(name);
    }

    @Override
    public Role findRoleByUserId(Long userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
