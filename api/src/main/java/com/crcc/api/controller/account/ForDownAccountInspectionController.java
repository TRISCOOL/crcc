package com.crcc.api.controller.account;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.*;
import com.crcc.common.service.ForDownAccountService;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inspection")
public class ForDownAccountInspectionController extends BaseController{

    @Autowired
    private ForDownAccountService forDownAccountService;

    /**
     * 新增对下验工台账
     * @param inspectionAccount
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo addInsepection(@RequestBody InspectionAccount inspectionAccount, HttpServletRequest request){
        User user = curUser(request);
        inspectionAccount.setCreateUser(user.getId());
        Long id = forDownAccountService.addInsepectionAccount(inspectionAccount);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新对下计量台账
     * @param inspectionAccount
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody InspectionAccount inspectionAccount,HttpServletRequest request){
        User user = curUser(request);
        inspectionAccount.setUpdateUser(user.getId());
        boolean result = forDownAccountService.update(inspectionAccount);
        if (result){
            return ResponseVo.ok();
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 列表
     * @param projectName
     * @param subcontractorName
     * @param valuationType
     * @param valuationTime
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo listForPage(@RequestParam(value = "projectName",required = false)String projectName,
                                  @RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                                  @RequestParam(value = "teamName",required = false)String teamName,
                                  @RequestParam(value = "valuationType",required = false)Integer valuationType,
                                  @RequestParam(value = "valuationTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd")Date valuationTime,
                                  @RequestParam(value = "page",required = false)Integer page,
                                  @RequestParam(value = "maxUnderRate",required = false) Double maxUnderRate,
                                  @RequestParam(value = "minUnderRate",required = false) Double minUnderRate,
                                  @RequestParam(value = "pageSize",required = false)Integer pageSize,HttpServletRequest request){

        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);
        subcontractorName = Utils.getBlurryKeyString(subcontractorName);
        teamName = Utils.getBlurryKeyString(teamName);

        Integer offset = page - 1<0?0:page-1;
        List<InspectionAccount> inspectionAccounts = forDownAccountService.listForPage(projectId,projectName,subcontractorName,
                valuationType,valuationTime,offset*pageSize,pageSize,maxUnderRate,minUnderRate,teamName);

        Integer total = forDownAccountService.listForPage(projectId,projectName,subcontractorName,
                valuationType,valuationTime,null,null,maxUnderRate,minUnderRate,teamName).size();

        return ResponseVo.ok(total,page,pageSize,inspectionAccounts);
    }

    /**
     *
     * @param projectName
     * @param subcontractorName
     * @param valuationType
     * @param valuationTime
     * @param maxUnderRate
     * @param minUnderRate
     * @param request
     * @return
     */
    @GetMapping("/total/v1.1")
    @AuthRequire
    public ResponseVo getTotal(@RequestParam(value = "projectName",required = false)String projectName,
                               @RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                               @RequestParam(value = "teamName",required = false)String teamName,
                               @RequestParam(value = "valuationType",required = false)Integer valuationType,
                               @RequestParam(value = "valuationTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd")Date valuationTime,
                               @RequestParam(value = "maxUnderRate",required = false) Double maxUnderRate,
                               @RequestParam(value = "minUnderRate",required = false) Double minUnderRate,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);
        subcontractorName = Utils.getBlurryKeyString(subcontractorName);
        teamName = Utils.getBlurryKeyString(teamName);

        InspectionAccountTotal total = forDownAccountService.getTotal(projectId,projectName,subcontractorName,valuationType,
                valuationTime,maxUnderRate,minUnderRate,teamName);

        return ResponseVo.ok(total);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        return ResponseVo.ok(forDownAccountService.getDetails(id));
    }

    /**
     *
     * @param projectName
     * @param subcontractorName
     * @param valuationType
     * @param valuationTime
     * @param token
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "projectName",required = false)String projectName,
                       @RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                       @RequestParam(value = "teamName",required = false)String teamName,
                       @RequestParam(value = "valuationType",required = false)Integer valuationType,
                       @RequestParam(value = "valuationTime",required = false)Date valuationTime,
                       @RequestParam(value = "maxUnderRate",required = false) Double maxUnderRate,
                       @RequestParam(value = "minUnderRate",required = false) Double minUnderRate,
                       @RequestParam("token")String token,HttpServletResponse response){
        Long projectId = permissionProjectOnlyToken(token);

        projectName = Utils.getBlurryKeyString(projectName);
        subcontractorName = Utils.getBlurryKeyString(subcontractorName);
        teamName = Utils.getBlurryKeyString(teamName);

        List<InspectionAccount> inspectionAccounts = forDownAccountService.listForPage(projectId,projectName,
                subcontractorName,valuationType,valuationTime,null,null,maxUnderRate,minUnderRate,teamName);
        String[] title = {"项目名称","分包商名称","队伍名称","合同金额","计价期数","计价日期","计价类型","计价总金额","扣款",
                "扣除质保金","扣除履约保证金","计日工及补偿费用","应支付金额","已完未计","对下计价率","计价负责人","备注"};
        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbookForInspectionAccount("对下验工计价台账","对下验工计价台账",title,inspectionAccounts);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+new String("对下验工计价台账".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/deleted/v1.1")
    @AuthRequire
    public ResponseVo logicDeleted(@RequestBody InspectionAccount inspectionAccount, HttpServletRequest request){
        User user = curUser(request);
        boolean result = forDownAccountService.logicDeleteById(inspectionAccount.getId(),user.getId(),new Date());
        if (result)
            return ResponseVo.ok();
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    @GetMapping("/list_count_labor/v1.1")
    @AuthRequire
    public ResponseVo listCountForLabor(@RequestParam(value = "projectName",required = false)String projectName,
                                        @RequestParam(value = "subName",required = false)String subName,
                                        @RequestParam(value = "teamName",required = false)String teamName,
                                        @RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,
                                        HttpServletRequest request){

        projectName = Utils.getBlurryKeyString(projectName);
        subName = Utils.getBlurryKeyString(subName);
        teamName = Utils.getBlurryKeyString(teamName);

        Long projectId = permissionProject(request);

        Integer realPage = page - 1 < 0?0:page-1;

        List<InspectionCountForLabor> inspectionCountForLaborList = forDownAccountService.listCountForLabor(projectName,
                subName,teamName,realPage*pageSize,pageSize,projectId);

        Integer count = forDownAccountService.listCountForLaborCount(projectName,subName,teamName,projectId);

        return ResponseVo.ok(count,page,pageSize,inspectionCountForLaborList);
    }

    @GetMapping("/list_count_project/v1.1")
    @AuthRequire
    public ResponseVo listCountForProject(@RequestParam(value = "projectName",required = false)String projectName,
                                          @RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,
                                          HttpServletRequest request){
        projectName = Utils.getBlurryKeyString(projectName);

        Long projectId = permissionProject(request);

        Integer realPage = page - 1 < 0?0:page-1;

        List<InspectionCountForProject> forProjects = forDownAccountService.listInspectionCountForProject(projectId,projectName,
                realPage*pageSize,pageSize);

        Integer count = forDownAccountService.listInspectionCountForProjectCount(projectId,projectName);

        return ResponseVo.ok(count,page,pageSize,forProjects);
    }

    @GetMapping("/list_count_project_export/v1.1")
    public void listCountForProjectExport(@RequestParam(value = "projectName",required = false)String projectName,
                                                @RequestParam("token")String token,
                                                HttpServletResponse response){
        projectName = Utils.getBlurryKeyString(projectName);

        Long projectId = permissionProjectOnlyToken(token);

        List<InspectionCountForProject> forProjects = forDownAccountService.listInspectionCountForProject(projectId,
                projectName,null,null);

        String[] title = {"项目名称","合同金额","计价总金额","扣款", "扣除质保金","扣除履约保证金",
                "计日工及补偿费用","应支付金额","已完未计","对下计价率"};
        HSSFWorkbook wb = ExcelUtils.getInspectionProjectExcel("对下验工计价台账项目汇总表","对下验工计价台账项目汇总表",title,forProjects);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+new String("对下验工计价台账项目汇总表".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/list_count_labor_export/v1.1")
    public void listCountForProjectExport(@RequestParam(value = "projectName",required = false)String projectName,
                                                @RequestParam(value = "subName",required = false)String subName,
                                                @RequestParam(value = "teamName",required = false)String teamName,
                                                @RequestParam("token")String token,
                                                HttpServletResponse response){
        projectName = Utils.getBlurryKeyString(projectName);
        subName = Utils.getBlurryKeyString(subName);
        teamName = Utils.getBlurryKeyString(teamName);

        Long projectId = permissionProjectOnlyToken(token);

        List<InspectionCountForLabor> inspectionCountForLabors = forDownAccountService.listCountForLabor(projectName,subName,teamName,
                null,null,projectId);


        String[] title = {"项目名称","分包商名称","队伍名称","合同金额","已计价期数","截至日期","计价类型","计价总金额","扣款",
                "扣除质保金","扣除履约保证金","计日工及补偿费用","应支付金额","已完未计","对下计价率"};
        HSSFWorkbook wb = ExcelUtils.getInspectionLarborExcel("对下验工计价台账项目所属队伍汇总表","对下验工计价台账项目所属队伍汇总表",title,inspectionCountForLabors);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+new String("对下验工计价台账项目所属队伍汇总表".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
