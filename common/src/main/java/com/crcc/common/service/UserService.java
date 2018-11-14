package com.crcc.common.service;

import com.crcc.common.model.User;

public interface UserService {

    User login(String account,String passWorld);

    Long addUser(User user);

    boolean updateUser(User user);
}
