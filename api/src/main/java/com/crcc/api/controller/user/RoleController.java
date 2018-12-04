package com.crcc.api.controller.user;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.Role;
import com.crcc.common.model.User;
import com.crcc.common.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;


    /**
     * 增加一个角色
     * @param role
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo addRole(@RequestBody Role role, HttpServletRequest request){
        User user = curUser(request);

        if (role.getName() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        if ("".equals(role.getName()))
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        role.setCreateUserId(user.getId());
        Long roleId = roleService.addRole(role);
        if (roleId != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("roleId",roleId);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 角色列表
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo listRole(@RequestParam(value = "name",required = false)String name,
                               @RequestParam(value = "page")Integer page,
                               @RequestParam(value = "pageSize")Integer pageSize){
        Integer offset = page-1 < 0?0:page-1;

        List<Role> roles = roleService.listAllRole(name,offset*pageSize,pageSize);

        Integer total = roleService.listAllRoleSize(name);

        return ResponseVo.ok(total,page,pageSize,roles);
    }

    /**
     * 指定删除一个角色
     * @param roleId
     * @return
     */
    @PostMapping("/delete/v1.1")
    public ResponseVo deleteRole(@RequestBody Long roleId){
        boolean result = roleService.deleteRole(roleId);
        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新指定role
     * @param role
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo updateRole(@RequestBody Role role,HttpServletRequest request){
        if (role.getId() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        User user = curUser(request);
        role.setUpdateUserId(user.getId());

        boolean result = roleService.updateRole(role);
        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 获得role详情包括权限
     * @param roleId
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam(value = "roleId")Long roleId){
        Role role = roleService.getDetailsForRole(roleId);
        return ResponseVo.ok(role);
    }

    /**
     * 为一个角色设置权限
     * @param role
     * @return
     */
    @PostMapping("/permissions/v1.1")
    @AuthRequire
    public ResponseVo settingPermissions(@RequestBody Role role){
        if (role.getId() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        boolean result = roleService.settingPermissions(role);
        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

}
