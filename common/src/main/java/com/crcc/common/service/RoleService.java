package com.crcc.common.service;

import com.crcc.common.model.Resouce;
import com.crcc.common.model.Role;

import java.util.List;

public interface RoleService {
    Long addRole(Role role);

    boolean updateRole(Role role);

    boolean deleteRole(Long id);

    boolean settingPermissions(Long roleId, List<Resouce> resouces);

    Role getDetailsForRole(Long roleId);

    Role onlyGetRole(Long roleId);

    List<Role> listAllRole(String name,Integer offset,Integer length);
}
