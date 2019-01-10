package com.crcc.api.interceptors;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.User;
import com.crcc.common.service.RedisService;
import com.crcc.common.service.UserService;
import com.crcc.common.utils.Utils;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private UserService userService;


    @Resource
    private RedisService redisService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AuthRequire auth = handlerMethod.getMethodAnnotation(AuthRequire.class);
            if (auth == null) {
                return true;
            }
            String authorization = request.getHeader(AUTHORIZATION);
            try {
                if (StringUtils.isEmpty(authorization)) {
                    return authFail(response, ResponseCode.AUTH_FAILED);
                }
                if (redisService == null) {
                    redisService = (RedisService) WebApplicationContextUtils.getRequiredWebApplicationContext(request
                            .getServletContext()).getBean(RedisService.class);
                }
                if (!redisService.exists(authorization)) {
                    return authFail(response, ResponseCode.AUTH_FAILED);
                }
                if (!verificationPermission(authorization)){
                    //权限被更改后，删掉原来的token,保证能重新登录
                    redisService.delStr(authorization);
                    return authFail(response, ResponseCode.PERMISSION_CHANGED);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return authFail(response, ResponseCode.AUTH_FAILED);
            }
        } else {
            return true;
        }
    }

    private boolean authFail(HttpServletResponse response, ResponseCode errorCode) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try (OutputStream outputStream = response.getOutputStream()) {
            ResponseVo vo = ResponseVo.error(errorCode);
            outputStream.write(Utils.toJson(vo).getBytes());
        } catch (IOException e) {
            logger.error("auth fail", e);
            return false;
        }
        return false;
    }

    private boolean verificationPermission(String token){
        String value = redisService.getStr(token);
        if (value == null)
            return false;

        User exist = Utils.fromJson(value,new TypeToken<User>(){});
        List<String> permissions = exist.getPermissions();
        List<String> newPermissions = userService.listPermissionForUser(userService.listResourceForUser(exist.getId()));
        return comparisonList(permissions,newPermissions);

    }

    private boolean comparisonList(List<String> oldPermission,List<String> newPermission){
        Collections.sort(oldPermission);
        Collections.sort(newPermission);
        if (oldPermission.toString().equals(newPermission.toString())){
            return true;
        }

        return false;
    }
}
