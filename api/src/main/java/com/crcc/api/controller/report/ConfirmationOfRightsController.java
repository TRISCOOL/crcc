package com.crcc.api.controller.report;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.ConfirmationOfRights;
import com.crcc.common.model.ConfirmationOfRightsTotal;
import com.crcc.common.model.User;
import com.crcc.common.service.ConfirmationOfRightsService;
import com.crcc.common.utils.ExcelUtils;
import org.apache.ibatis.annotations.Param;
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

@RequestMapping("/confirmation")
@RestController
public class ConfirmationOfRightsController extends BaseController{

    @Autowired
    private ConfirmationOfRightsService confirmationOfRightsService;

    /**
     * 获得上年余额
     * @param projectId
     * @return
     */
    @GetMapping("/last/v1.1")
    public ResponseVo found(@Param("projectId")Long projectId){
        ConfirmationOfRights confirmation = confirmationOfRightsService.foundConfirmForLastYear(projectId);
        return ResponseVo.ok(confirmation);
    }

    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody ConfirmationOfRights confirmationOfRights, HttpServletRequest request){
        User user = curUser(request);
        confirmationOfRights.setCreateUser(user.getId());
        confirmationOfRightsService.add(confirmationOfRights);
        if (confirmationOfRights.getId() != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",confirmationOfRights.getId());
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody ConfirmationOfRights confirmationOfRights,HttpServletRequest request){
        User user = curUser(request);
        boolean result = confirmationOfRightsService.updateConfirmationOfRights(confirmationOfRights);
        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo list(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "year",required = false)String year,
                           @RequestParam(value = "quarter",required = false)String quarter,
                           @RequestParam(value = "page")Integer page,
                           @RequestParam(value = "pageSize")Integer pageSize,HttpServletRequest request){

        Long projectId = permissionProject(request);
        Integer offset = page - 1 < 0 ? page -1 : 0;

        List<ConfirmationOfRights> confirmations =
                confirmationOfRightsService.listForPage(projectId,projectName,year,quarter,offset*pageSize,pageSize);

        Integer total = confirmationOfRightsService.listForPageSize(projectId,projectName,year,quarter);

        return ResponseVo.ok(total,page,pageSize,confirmations);

    }

    @GetMapping("/total/v1.1")
    @AuthRequire
    public ResponseVo getTotal(@RequestParam(value = "projectName",required = false)String projectName,
                               @RequestParam(value = "quarter",required = false)String quarter,
                               @RequestParam(value = "year",required = false)String  year,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);
        ConfirmationOfRightsTotal total = confirmationOfRightsService.getTotal(projectId,projectName,year,quarter);
        return ResponseVo.ok(total);

    }

    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "projectName",required = false)String projectName,
                       @RequestParam(value = "quarter",required = false)String quarter,
                       @RequestParam(value = "year",required = false)String  year,
                       @RequestParam("token")String token, HttpServletResponse response){
        Long projectId = permissionProjectOnlyToken(token);
        List<ConfirmationOfRights> confirmationOfRights = confirmationOfRightsService.listForPage(projectId,projectName,year,quarter,
                null,null);
        String[] titles = {"项目名称","填报时间","上年末开累完成产值","上年末开累验工计价","小计","合同内应计未计",
                "变更索赔预计额","本年截至本期完成产值", "小计","上年末已完工本年计价","当年完成产值当年验工计价",
                "变更索赔预计额","开累完成产值", "开累验工计价","小计","其中：合同内应计未计","其中：变更索赔预计额",};

        try {
            OutputStream out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getConfirmationExcel("工程承包板块季度确权清收统计表",
                    "工程承包板块季度确权清收统计表",titles,confirmationOfRights);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("工程承包板块季度确权清收统计表".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
