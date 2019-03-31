package com.crcc.api.controller.report;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.EngineerChangeTotal;
import com.crcc.common.model.EngineeringChangeMonthly;
import com.crcc.common.model.User;
import com.crcc.common.service.EngineeringChangeService;
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
@RequestMapping("/engineer_change")
public class EngineerChangeController extends BaseController{

    @Autowired
    private EngineeringChangeService engineeringChangeService;

    /**
     * 新增
     * @param engineeringChangeMonthly
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody EngineeringChangeMonthly engineeringChangeMonthly, HttpServletRequest request){
        User user = curUser(request);
        engineeringChangeMonthly.setCreateUser(user.getId());
        Long id = engineeringChangeService.addEngineeringChangeMonthly(engineeringChangeMonthly);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody EngineeringChangeMonthly engineeringChangeMonthly,HttpServletRequest request){
        User user = curUser(request);
        engineeringChangeMonthly.setUpdateUser(user.getId());
        boolean result = engineeringChangeService.updateEngineeringChangeMonthly(engineeringChangeMonthly);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 列表
     * @param projectName
     * @param year
     * @param quarter
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo list(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "year",required = false)String year,
                           @RequestParam(value = "quarter",required = false)Integer quarter,
                           @RequestParam(value = "page",required = false)Integer page,
                           @RequestParam(value = "pageSize",required = false)Integer pageSize,
                           HttpServletRequest request){
        Long projectId = permissionProject(request);
        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<EngineeringChangeMonthly> engineeringChangeMonthlies = engineeringChangeService.listForPage(projectId,projectName,
                year,quarter,offset*pageSize,pageSize);
        Integer total = engineeringChangeService.listForPageSize(projectName,projectId,year,quarter);
        return ResponseVo.ok(total,page,pageSize,engineeringChangeMonthlies);
    }

    @GetMapping("/total/v1.1")
    @AuthRequire
    public ResponseVo getTotal(@RequestParam(value = "projectName",required = false)String projectName,
                               @RequestParam(value = "year",required = false)String year,
                               @RequestParam(value = "quarter",required = false)Integer quarter,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);
        EngineerChangeTotal total = engineeringChangeService.getTotal(projectName,projectId,year,quarter);
        return ResponseVo.ok(total);
    }

    /**
     * 台账列表
     * @return
     */
    @GetMapping("/list_statistics/v1.1")
    @AuthRequire
    public ResponseVo listStatistics(@RequestParam(value = "projectName",required = false)String projectName,
                                     @RequestParam(value = "page",required = false)Integer page,
                                     @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                     HttpServletRequest request){
        Integer offset = page -1 <0 ? 0: page-1;
        Long projectId = permissionProject(request);
        List<EngineeringChangeMonthly> engineeringChangeMonthlies =
                engineeringChangeService.listStatisticsForPage(projectId,projectName,offset*pageSize,pageSize);

        Integer total = engineeringChangeService.listStatisticsForPageSize(projectId,projectName);
        return ResponseVo.ok(total,page,pageSize,engineeringChangeMonthlies);
    }

    @GetMapping("/total_statistics/v1.1")
    @AuthRequire
    public ResponseVo getTotalStatistics(@RequestParam(value = "projectName",required = false)String projectName,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);
        EngineerChangeTotal total = engineeringChangeService.getStatisticsTotal(projectId,projectName);
        return ResponseVo.ok(total);
    }

    @GetMapping("/export/v1.1")
    public void exportList(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "year",required = false)String year,
                           @RequestParam(value = "quarter",required = false)Integer quarter,
                           @RequestParam(value = "token")String token,
                           HttpServletResponse response){
        Long projectId = permissionProjectOnlyToken(token);
        List<EngineeringChangeMonthly> engineeringChangeMonthlies =
                engineeringChangeService.listForPage(projectId,projectName,year,quarter,null,null);

        String[] tittles = {"序号","项目名称","工程类别","填报日期","原合同额","施工产值","变更索赔额","变更索赔率（%）","备注"};

        try {
            OutputStream out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getEngineerChangeExcel("工程变更索赔月快报表",
                    "工程变更索赔月快报表",tittles,engineeringChangeMonthlies);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("工程变更索赔月快报表".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/export_statistics/v1.1")
    public void exportListStatistics(@RequestParam(value = "projectName",required = false)String projectName,
                                     @RequestParam(value = "token")String token,
                           HttpServletResponse response){
        Long projectId = permissionProjectOnlyToken(token);
        List<EngineeringChangeMonthly> engineeringChangeMonthlies =
                engineeringChangeService.listStatisticsForPage(projectId,projectName,null,null);

        String[] tittles = {"项目名称","合同总额","施工产值","变更索赔额","变更索赔率（%）"};

        try {
            OutputStream out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getEngineerChangeStatisticsExcel("变更索赔情况统计表",
                    "变更索赔情况统计表",tittles,engineeringChangeMonthlies);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("变更索赔情况统计表".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/delete/v1.1")
    @AuthRequire
    public ResponseVo delete(@RequestParam("id")Long id){
        boolean result = engineeringChangeService.deleteOneById(id);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }
}
