package com.crcc.api.controller;

import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.User;
import com.crcc.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @PostMapping("/login/v1.1")
    public ResponseVo login(@RequestBody User user){
        String account = user.getAccount();
        String passworld = user.getPassword();

        if (account == null || passworld == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        User result = userService.login(account,passworld);
        return ResponseVo.ok(result);
    }

}
