package com.crcc.api.controller;

import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.Project;
import com.crcc.common.model.Role;
import com.crcc.common.model.User;
import com.crcc.common.service.ProjectService;
import com.crcc.common.service.RedisService;
import com.crcc.common.service.RoleService;
import com.crcc.common.utils.Utils;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/base")
@RestController
public class BaseController {

    protected static final String AUTHORIZATION = "Authorization";

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProjectService projectService;

    @ExceptionHandler
    @ResponseBody
    public ResponseVo defaultExceptionHandler(Exception ex) {
        ResponseVo vo = ResponseVo.error(ResponseCode.SERVER_ERROR);
        if (ex instanceof CrccException) {
            CrccException crccException = (CrccException) ex;
            vo = ResponseVo.error(crccException.getErrorCode());
        }
        if (ex instanceof BindException) {
            vo = ResponseVo.error(ResponseCode.PARAM_ILLEGAL);
        }
        logger.error("{}", ex);
        return vo;
    }

//    protected User curUser(HttpServletRequest request) throws LassException {
//        String authorization = request.getHeader(AUTHORIZATION);
//        User user = null;
//        if (!StringUtils.isEmpty(authorization)) {
//            String userStr = redisService.getStr(authorization);
//            if (!StringUtils.isEmpty(userStr)) {
//                user = gson.fromJson(userStr, User.class);
//            }
//        }
//        return user;
//    }

/*    protected User curUser(HttpServletRequest request){
        User user = new User();
        user.setId(1l);
        user.setExhibitionId(1l);
        return user;
    }*/

    protected User curUser(HttpServletRequest request) throws CrccException {
        String authorization = request.getHeader(AUTHORIZATION);
        User user = new User();
        if (!StringUtils.isEmpty(authorization)) {
            String userStr = redisService.getStr(authorization);
            if (!StringUtils.isEmpty(userStr)) {
                user = Utils.fromJson(userStr, new TypeToken<User>() {
                });
            }
        }
        return user;
    }

    protected User curUser(HttpServletRequest request, boolean checkToken) throws CrccException {
        User user = curUser(request);
        if (user == null && checkToken) {
            throw new CrccException(ResponseCode.AUTH_FAILED);
        }
        return user;
    }

    protected Long permissionProject(HttpServletRequest request){
        User user = curUser(request);
        if (user == null)
            return null;

        if (user.getType() == 0){
            return null;
        }
        List<Project> projectList = projectService.listProjectForProjectUser(user.getId());
        if (projectList != null && projectList.size()>0){
            return projectList.get(0).getId();
        }

        return 0L;

    }

//    protected User curUser(HttpServletRequest request, boolean checkToken) throws LassException {
//        User user = curUser(request);
//        if (user == null && checkToken) {
//            throw new LassException(ErrorCode.AUTH_FAIL);
//        }
//        return user;
//    }

//    public static ResponseVO error(ErrorCode code) {
//        ResponseVO tcy.admin.vo = new ResponseVO(false);
//        if(code != null) {
//            tcy.admin.vo.setMessage(code.getMsg());
//            tcy.admin.vo.setCode(code.getCode());
//        }
//
//        return tcy.admin.vo;
//    }

    @GetMapping("/test")
    public ResponseVo test(){

        Map<String,String> result = new HashMap<String, String>();
        result.put("token","1234");
        result.put("userName","哈哈哈");

        return ResponseVo.ok(result);
    }
}
