package com.crcc.api.controller.user;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.User;
import com.crcc.common.service.UserService;
import com.crcc.common.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/v1.1")
    public ResponseVo login(@RequestBody User user){
        String account = user.getAccount();
        String password = user.getPassword();

        if (account == null || password == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        User result = userService.login(account,password);
        return ResponseVo.ok(result);
    }

    /**
     * 创建一个用户
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo addUser(@RequestBody User user, HttpServletRequest request){

        User createUser = curUser(request);

        user.setCreateUser(createUser.getId());
        Long userId = userService.addUser(user);
        if (userId != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("userId",userId);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新一个用户信息
     * @param user
     * @param request
     * @return
     */
    @AuthRequire
    @PostMapping("/update/v1.1")
    public ResponseVo updateUser(@RequestBody User user,HttpServletRequest request){
        User operaitonUser = curUser(request);
        if (user.getId() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        user.setUpdateUser(operaitonUser.getId());
        boolean result = userService.updateUser(user);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 得到一个用户详细信息
     * @param userId
     * @return
     */
    @GetMapping("/details/v1.1")
    @AuthRequire
    public ResponseVo getDetails(@RequestParam("userId")Long userId){
        User user =userService.getUserDetails(userId);
        return ResponseVo.ok(user);
    }

    /**
     * 禁用或启用 用户状态
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/update_status/v1.1")
    @AuthRequire
    public ResponseVo updateStatusForUser(@RequestBody User user,HttpServletRequest request){
        User operationUser = curUser(request);

        if (user.getId() == null || user.getDisable() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        user.setUpdateUser(operationUser.getId());
        boolean result = userService.updateUserStatus(user);
        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 获取用户列表
     * @param projectName
     * @param code
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo listUser(@RequestParam(value = "projectName",required = false)String projectName,
                               @RequestParam(value = "code",required = false)String code,
                               @RequestParam(value = "status",required = false)Integer status,
                               @RequestParam(value = "page")Integer page,
                               @RequestParam(value = "pageSize")Integer pageSize){

        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<User> users = userService.listUser(code,projectName,status,offset*pageSize,pageSize);

        Integer total = userService.listUserSize(code,projectName,status);
        return ResponseVo.ok(total,page,pageSize,users);
    }

    /**
     * 导出用户管理列表
     * @param projectName
     * @param code
     * @param status
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void exportExcelForUsers(@RequestParam(value = "projectName",required = false)String projectName,
                                    @RequestParam(value = "code",required = false)String code,
                                    @RequestParam(value = "status",required = false)Integer status,
                                    HttpServletResponse response){

        List<User> users = userService.listUser(code,projectName,status,null,null);
        OutputStream out = null;
        try {

            String[] titles = {"序号编码","帐号类别","帐号名称","项目名称","密码","角色权限","状态","创建人","创建时间","更新人","更新时间"};

            out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getHSSFWorkbookForUser("用户管理",titles,users);
            response.setHeader("Content-disposition", "attachment; filename="+new String("用户管理".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
