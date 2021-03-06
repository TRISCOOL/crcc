package com.crcc.common.service;

import com.crcc.common.model.Resouce;
import com.crcc.common.model.User;

import java.util.List;

public interface UserService {

    User login(String account,String password);

    Long addUser(User user);

    boolean updateUser(User user);

    boolean updateUserStatus(User user);

    boolean deleteUser(Long userId);

    User getUserDetails(Long userId);

    List<User> listUser(String code,String name,Integer disable,Integer offset,Integer length);

    Integer listUserSize(String code,String name,Integer disable);

    List<Resouce> listResourceForUser(Long userId);

    List<String> listPermissionForUser(List<Resouce> list);
}
