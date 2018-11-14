package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.UserMapper;
import com.crcc.common.model.User;
import com.crcc.common.service.RedisService;
import com.crcc.common.service.UserService;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public User login(String account, String passWorld) {

        User existUser = userMapper.findUserByAccount(account);
        if (existUser == null)
            throw new CrccException(ResponseCode.NOT_FOUND_USER);

        if (!existUser.getPassword().equals(passWorld))
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
            redisService.setStr(token,Utils.toJson(user));
            return user;
        }

        return null;
    }

    @Override
    public Long addUser(User user) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }
}
