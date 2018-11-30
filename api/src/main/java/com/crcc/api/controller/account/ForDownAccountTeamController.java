package com.crcc.api.controller.account;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.LaborAccount;
import com.crcc.common.model.User;
import com.crcc.common.service.LaborAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/team")
public class ForDownAccountTeamController extends BaseController{

    @Autowired
    private LaborAccountService laborAccountService;

    /**
     * 新增所属队伍台账
     * @param laborAccount
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody LaborAccount laborAccount, HttpServletRequest request){
        User user = curUser(request);
        laborAccount.setCreateUser(user.getId());
        Long laborAccountId = laborAccountService.addLaborAccount(laborAccount);
        if (laborAccountId != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("laborAccountId",laborAccountId);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新一个队伍台账
     * @param laborAccount
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody LaborAccount laborAccount,HttpServletRequest request){
        User user = curUser(request);
        laborAccount.setUpdateUser(user.getId());
        boolean result = laborAccountService.update(laborAccount);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

/*    @GetMapping("/list/v1.1")
    public ResponseVo listForPage(){

    }*/
}
