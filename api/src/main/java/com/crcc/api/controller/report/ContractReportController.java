package com.crcc.api.controller.report;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.OutOfContractCompensationStatistics;
import com.crcc.common.model.OutOfContractCompensationStatisticsTotal;
import com.crcc.common.model.User;
import com.crcc.common.service.CompensationStatisticsService;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
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

@RequestMapping("/contract_report")
@RestController
public class ContractReportController extends BaseController{

    @Autowired
    private CompensationStatisticsService compensationStatisticsService;

    /**
     * 新增合同外计日工及赔偿情况统计表
     * @param compensationStatistics
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody OutOfContractCompensationStatistics compensationStatistics,
                          HttpServletRequest request){
        User user = curUser(request);
        compensationStatistics.setCreateUser(user.getId());
        Long id = compensationStatisticsService.addCompensationStatistics(compensationStatistics);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("compensationStatisticsId",id);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 获取列表
     * @param projectName
     * @param subcontractorName
     * @param teamName
     * @param year
     * @param quarter 季度
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo list(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                           @RequestParam(value = "teamName",required = false)String teamName,
                           @RequestParam(value = "year",required = false)String year,
                           @RequestParam(value = "quarter",required = false)Integer quarter,
                           @RequestParam(value = "page",required = false)Integer page,
                           @RequestParam(value = "pageSize",required = false)Integer pageSize,
                           HttpServletRequest request){

        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);
        subcontractorName = Utils.getBlurryKeyString(subcontractorName);
        teamName = Utils.getBlurryKeyString(teamName);

        Integer offset = page - 1 < 0?0:page-1;

        List<OutOfContractCompensationStatistics> compensationStatistics =
                compensationStatisticsService.listForPage(projectName,subcontractorName,teamName,
                        year,quarter,offset*pageSize,pageSize,projectId);

        Integer size = compensationStatisticsService.listForPageSize(projectName,subcontractorName,teamName,year,quarter,
                projectId);

        return ResponseVo.ok(size,page,pageSize,compensationStatistics);
    }

    @GetMapping("/total_statistics/v1.1")
    @AuthRequire
    public ResponseVo getTotal(@RequestParam(value = "projectName",required = false)String projectName,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);

        OutOfContractCompensationStatisticsTotal total = compensationStatisticsService.getTotalStatistics(projectName,projectId);
        return ResponseVo.ok(total);
    }

    @GetMapping("/total/v1.1")
    @AuthRequire
    public ResponseVo getTotal(@RequestParam(value = "projectName",required = false)String projectName,
                               @RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                               @RequestParam(value = "teamName",required = false)String teamName,
                               @RequestParam(value = "year",required = false)String year,
                               @RequestParam(value = "quarter",required = false)Integer quarter,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);
        subcontractorName = Utils.getBlurryKeyString(subcontractorName);
        teamName = Utils.getBlurryKeyString(teamName);

        OutOfContractCompensationStatisticsTotal total = compensationStatisticsService.getTotal(projectName,subcontractorName,
                teamName,year,quarter,projectId);

        return ResponseVo.ok(total);
    }

    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody OutOfContractCompensationStatistics compensationStatistics,HttpServletRequest request){
        if (compensationStatistics.getId() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        User user = curUser(request);
        compensationStatistics.setUpdateUser(user.getId());
        boolean result = compensationStatisticsService.updateCompensationStatistics(compensationStatistics);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 得到详情
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        return ResponseVo.ok(compensationStatisticsService.getDetailsById(id));
    }

    /**
     * 合同外计日工及补偿费用台账列表
     * @param projectName
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/statistics_list/v1.1")
    @AuthRequire
    public ResponseVo listStatistics(@RequestParam(value = "projectName",required = false)String projectName,
                                     @RequestParam(value = "page",required = false)Integer page,
                                     @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                     HttpServletRequest request){
        Integer offset = page-1<0?0:page-1;

        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);

        List<OutOfContractCompensationStatistics> result =
                compensationStatisticsService.listStatisticsForPage(projectName,projectId,offset*pageSize,pageSize);

        Integer total = compensationStatisticsService.listStatisticsForPageSize(projectName,projectId);

        return ResponseVo.ok(total,page,pageSize,result);

    }

    @GetMapping("/export/v1.1")
    public void exportList(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                           @RequestParam(value = "teamName",required = false)String teamName,
                           @RequestParam(value = "year",required = false)String year,
                           @RequestParam(value = "quarter",required = false)Integer quarter,
                           @RequestParam(value = "token")String token,
                           HttpServletResponse response){
        Long projectId = permissionProjectOnlyToken(token);

        projectName = Utils.getBlurryKeyString(projectName);
        subcontractorName = Utils.getBlurryKeyString(subcontractorName);
        teamName = Utils.getBlurryKeyString(teamName);

        List<OutOfContractCompensationStatistics> compensationStatistics =
                compensationStatisticsService.listForPage(projectName,subcontractorName,teamName,year,quarter,
                        null,null,projectId);

        String[] tittles = {"序号","项目名称","工程类别","分包商名称","队伍名称","合同编号","合同签订人","填报日期","合同内计量",
        "机械台班","零星用工","小计","进出场","灾损","窝工/停工","其他","小计","合计","计日工占已计价金额比例（%）","合同外补偿/赔偿占已计价金额比例（%）",
        "计日工及补偿已拨付金额（元）","计日工及补偿已拨付金额拨付率（%）","是否报审","是否结算","机械台班","零星用工","小计","进出场",
                "灾损","窝工/停工","其他","小计","合计"};

        try {
            OutputStream out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getContractCompensationExcel("在建项目合同外计日工及赔偿情况统计表",
                    "在建项目合同外计日工及赔偿情况统计表",tittles,compensationStatistics);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("在建项目合同外计日工及赔偿情况统计表".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
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

        projectName = Utils.getBlurryKeyString(projectName);

        List<OutOfContractCompensationStatistics> compensationStatistics =
                compensationStatisticsService.listStatisticsForPage(projectName,projectId,null,null);

        String[] tittles = {"项目名称","工程类别","合同内计量","计日工","合同外补偿/赔偿","小计","计日工","合同外补偿/赔偿","小计"};

        try {
            OutputStream out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getContractCompensationStatisticsExcel("项目合同外计日工及补偿费用台账",
                    "项目合同外计日工及补偿费用台账",tittles,compensationStatistics);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("项目合同外计日工及补偿费用台账".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/delete/v1.1")
    @AuthRequire
    public ResponseVo delete(@RequestBody OutOfContractCompensationStatistics out){
        boolean result = compensationStatisticsService.deleteOneById(out.getId());
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }
}
