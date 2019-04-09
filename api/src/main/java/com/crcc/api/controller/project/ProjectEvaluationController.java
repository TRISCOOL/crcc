package com.crcc.api.controller.project;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.ExportConfig;
import com.crcc.common.model.ProjectEvaluation;
import com.crcc.common.model.User;
import com.crcc.common.service.ExportConfigService;
import com.crcc.common.service.ProjectEvaluationService;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evaluation")
public class ProjectEvaluationController extends BaseController{

    @Autowired
    private ProjectEvaluationService projectEvaluationService;

    @Autowired
    private ExportConfigService exportConfigService;

    /**
     * 新增项目评价
     * @param projectEvaluation
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody ProjectEvaluation projectEvaluation, HttpServletRequest request){
        User user = curUser(request);
        projectEvaluation.setCreateUser(user.getId());
        Long evaluationId = projectEvaluationService.addEvaluation(projectEvaluation);
        if (evaluationId != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",evaluationId);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新
     * @param projectEvaluation
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody ProjectEvaluation projectEvaluation,HttpServletRequest request){
        User user = curUser(request);
        projectEvaluation.setUpdateUser(user.getId());
        boolean result = projectEvaluationService.update(projectEvaluation);
        if (!result){
            return ResponseVo.error(ResponseCode.SERVER_ERROR);
        }
        return ResponseVo.ok();
    }

    /**
     * 列表
     * @param projectName
     * @param evaluationStatus
     * @param engineeringStatus
     * @param isSign
     * @param isResponsibility
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo list(@RequestParam(value = "projectName",required = false) String projectName,
                           @RequestParam(value = "evaluationStatus",required = false) String evaluationStatus,
                           @RequestParam(value = "engineeringStatus",required = false) String engineeringStatus,
                           @RequestParam(value = "isSign",required = false) String isSign,
                           @RequestParam(value = "isResponsibility",required = false) Integer isResponsibility,
                           @Param("page") Integer page, @Param("pageSize") Integer pageSize,
                           HttpServletRequest request){

        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);

        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<ProjectEvaluation> projectEvaluations = projectEvaluationService.listForPage(projectId,projectName,evaluationStatus,
                engineeringStatus,isSign,isResponsibility,offset*pageSize,pageSize);

        Integer total = projectEvaluationService.listForPageSize(projectId,projectName,evaluationStatus,
                engineeringStatus,isSign,isResponsibility);

        return ResponseVo.ok(total,page,pageSize,projectEvaluations);
    }

    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "projectName",required = false) String projectName,
                       @RequestParam(value = "evaluationStatus",required = false) String evaluationStatus,
                       @RequestParam(value = "engineeringStatus",required = false) String engineeringStatus,
                       @RequestParam(value = "isSign",required = false) String isSign,
                       @RequestParam(value = "isResponsibility",required = false) Integer isResponsibility,
                       @RequestParam("token")String token,
                       @RequestParam(value = "exportType",required = false)String exportType,
                       @RequestParam(value = "sort",required = false)List<Integer> sort,
                       HttpServletResponse response){

        Long projectId = permissionProjectOnlyToken(token);

        projectName = Utils.getBlurryKeyString(projectName);

        List<ProjectEvaluation> projectEvaluations = projectEvaluationService.listForPage(projectId,projectName,evaluationStatus,
                engineeringStatus,isSign,isResponsibility,null,null);
        HSSFWorkbook hb = null;
        if (exportType != null && sort != null){
            List<ExportConfig> exportConfigs = exportConfigService.findExportConfigs(exportType,sort);
            List<String> titles = Utils.getField(Utils.EXPORT_CONFIG_KEY_TITLE,exportConfigs);
            List<String> fields  = Utils.getField(Utils.EXPORT_CONFIG_KEY_FIELD,exportConfigs);
            hb = ExcelUtils.getStandardExcel("项目评估",titles,fields,projectEvaluations,"项目评估");
        }else {
            String[] titles = {"序号","项目名称","工程类别","评估状态","工程状态","中标","有效收入","是否签订",
                    "签订日期","合同开工时间","合同竣工时间","工期（月）","评估时间","评估效益点（%)","含分包差及经营费（%）","评估编号",
                    "附件","效益点","是否含分包差及经营费","上会时间","附件","效益点","签订时间","项目经理","项目书记","附件"};
            hb = ExcelUtils.getProjectEvaluationExcel("项目评估","项目评估",titles,projectEvaluations);
        }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+new String("项目评估".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            hb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        return ResponseVo.ok(projectEvaluationService.getDetails(id));
    }

    @PostMapping("/deleted/v1.1")
    @AuthRequire
    public ResponseVo deleted(@RequestBody ProjectEvaluation evaluation){
        boolean result = projectEvaluationService.deletedById(evaluation.getId());
        if (result)
            return ResponseVo.ok();
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }
}
