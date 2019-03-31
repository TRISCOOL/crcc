package com.crcc.api.controller.report;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.LiabilityCost;
import com.crcc.common.model.LiabilityCostForList;
import com.crcc.common.model.User;
import com.crcc.common.service.LiabilityCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/liability_cost")
public class LiabilityCostController extends BaseController{

    @Autowired
    private LiabilityCostService liabilityCostService;

    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody LiabilityCost liabilityCost, HttpServletRequest request){
        User user = curUser(request);
        liabilityCost.setCreateUser(user.getId());
        Long id = liabilityCostService.add(liabilityCost);
        Map<String,Long> result = new HashMap<String, Long>();
        result.put("id",id);
        return ResponseVo.ok(result);
    }

    @SuppressWarnings("Duplicates")
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody LiabilityCost liabilityCost,HttpServletRequest request){
        User user = curUser(request);
        liabilityCost.setUpdateUser(user.getId());
        boolean result = liabilityCostService.update(liabilityCost);
        if (result){
            return ResponseVo.ok();
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo list(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "year",required = false)Integer year,
                           @RequestParam(value = "month",required = false)Integer month,
                           @RequestParam(value = "page",required = false)Integer page,
                           @RequestParam(value = "pageSize",required = false)Integer pageSize,
                           HttpServletRequest request){
        Long projectId = permissionProject(request);

        Integer offset = page - 1 < 0 ? 0:page-1;
        List<LiabilityCostForList> liabilityCostForLists = liabilityCostService.listForPage(projectId,projectName,
                year,month,offset*pageSize,pageSize);

        Integer total = liabilityCostService.listForPageSize(projectId,projectName,year,month);
        return ResponseVo.ok(total,page,pageSize,liabilityCostForLists);

    }

    @PostMapping("/delete/v1.1")
    @AuthRequire
    public ResponseVo delete(@RequestBody LiabilityCost liabilityCost){
        boolean result = liabilityCostService.deleteOnById(liabilityCost.getId());
        if (result)
            return ResponseVo.ok();
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }
}
