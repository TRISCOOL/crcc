package com.crcc.api.controller.account;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.ExportConfig;
import com.crcc.common.model.LaborAccount;
import com.crcc.common.model.User;
import com.crcc.common.service.ExportConfigService;
import com.crcc.common.service.LaborAccountService;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
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
@RequestMapping("/team")
public class ForDownAccountTeamController extends BaseController{

    @Autowired
    private LaborAccountService laborAccountService;

    @Autowired
    private ExportConfigService exportConfigService;

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

    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam(value = "id")Long id){
        if (id == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        return ResponseVo.ok(laborAccountService.getDetails(id));
    }

    /**
     *
     * @param projectName
     * @param subcontractorName
     * @param status
     * @param approval
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo listForPage(@RequestParam(value = "projectName",required = false) String projectName,
                                  @RequestParam(value = "subcontractorName",required = false) String subcontractorName,
                                  @RequestParam(value = "status",required = false) Integer status,
                                  @RequestParam(value = "approval",required = false) Integer approval,
                                  @RequestParam(value = "page") Integer page,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  HttpServletRequest request){
        Integer offset = page - 1 < 0 ? 0 : page-1;
        Long projectId = permissionProject(request);
        List<LaborAccount> laborAccounts = laborAccountService.listLaborAccount(projectId,projectName,subcontractorName,
                status,approval,offset*pageSize,pageSize);

        Integer total = laborAccountService.listLaborAccountSize(projectId,projectName,subcontractorName,
                status,approval);

        return ResponseVo.ok(total,page,pageSize,laborAccounts);
    }

    /**
     * 导出台账
     * @param projectName
     * @param subcontractorName
     * @param status
     * @param approval
     * @param token
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "projectName",required = false) String projectName,
                             @RequestParam(value = "subcontractorName",required = false) String subcontractorName,
                             @RequestParam(value = "status",required = false) Integer status,
                             @RequestParam(value = "approval",required = false) Integer approval,
                             @RequestParam("token")String token,HttpServletResponse response,
                       @RequestParam(value = "exportType",required = false)String exportType,
                       @RequestParam(value = "sort",required = false)List<Integer> sort){

        Long projectId = permissionProjectOnlyToken(token);
        List<LaborAccount> laborAccountList = laborAccountService.listLaborAccount(projectId,projectName,subcontractorName,
                status,approval,null,null);
        HSSFWorkbook hb = null;
        if (exportType != null && sort != null){
            List<ExportConfig> exportConfigs = exportConfigService.findExportConfigs(exportType,sort);
            List<String> titles = Utils.getField(Utils.EXPORT_CONFIG_KEY_TITLE,exportConfigs);
            List<String> fields = Utils.getField(Utils.EXPORT_CONFIG_KEY_FIELD,exportConfigs);
            hb = ExcelUtils.getStandardExcel("所属队伍台账",titles,fields,laborAccountList,"所属队伍台账");
        }else {
            String[] titles = {"项目名称","合同编码","分包商名称","队伍名称","队伍状态","合同签订日期","预计合同金额","施工范围",
                    "应缴金额（万元）","实缴金额（万元）","合同签订人","联系方式","结算金额","备注","日期","日期","是否备案","日期","备注"};

            hb = ExcelUtils.getLaborAccountExcel("所属队伍台账","所属队伍台账",titles,laborAccountList);

        }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+new String("所属队伍台账".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            hb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/only_list/v1.1")
    public ResponseVo onlyList(){
        return ResponseVo.ok(laborAccountService.onlyLIst());
    }

    /**
     * 合同金额
     * @param projectId
     * @param subcontractorId
     * @param teamName
     * @return
     */
    @GetMapping("/contractor_amount/v1.1")
    public ResponseVo getContractAmount(@RequestParam("projectId")Long projectId,
                                        @RequestParam("subcontractorId")Long subcontractorId,
                                        @RequestParam("teamName")String teamName){

        Double sum = laborAccountService.getSumContractAmount(projectId,subcontractorId,teamName.trim());
        Map<String,Double> result = new HashMap<String, Double>();
        result.put("sumAmount",sum);
        return ResponseVo.ok(result);

    }

    /**
     * 根据项目id选择相应的分包商
     * @param projectId
     * @return
     */
    @GetMapping("/sub_list/v1.1")
    public ResponseVo getSubForTeam(@RequestParam("projectId")Long projectId){
        return ResponseVo.ok(laborAccountService.selectSubcontractorByProject(projectId));
    }

    /**
     * 根据项目和分包商选择队伍
     * @param projectId
     * @param subcontractorId
     * @return
     */
    @GetMapping("/team_list/v1.1")
    public ResponseVo getTeamBySubAndProject(@RequestParam("projectId")Long projectId,
                                             @RequestParam("subcontractorId")Long subcontractorId){
        return ResponseVo.ok(laborAccountService.selectTeamByProjectAndSub(projectId,subcontractorId));
    }


}
