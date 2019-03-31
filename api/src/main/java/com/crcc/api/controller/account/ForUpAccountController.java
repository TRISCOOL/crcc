package com.crcc.api.controller.account;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.ExportConfig;
import com.crcc.common.model.MeteringAccount;
import com.crcc.common.model.MeteringAccountTotal;
import com.crcc.common.model.User;
import com.crcc.common.service.ExportConfigService;
import com.crcc.common.service.ForUpAccountService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/for_up")
public class ForUpAccountController extends BaseController{

    @Autowired
    private ForUpAccountService forUpAccountService;

    @Autowired
    private ExportConfigService exportConfigService;

    /**
     * 添加一条对上计量台账
     * @param meteringAccount
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody MeteringAccount meteringAccount, HttpServletRequest request){
        User user = curUser(request);
        meteringAccount.setCreateUser(user.getId());
        Long id = forUpAccountService.addMeteringAccount(meteringAccount);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新
     * @param meteringAccount
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody MeteringAccount meteringAccount,HttpServletRequest request){
        User user = curUser(request);
        meteringAccount.setUpdateUser(user.getId());
        boolean result =  forUpAccountService.updateMeteringAccount(meteringAccount);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 获取对上计量列表
     * @param projectName
     * @param meteringTime
     * @param minPayProportion
     * @param maxPayProportion
     * @param minProductionValue
     * @param maxProductionValue
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo list(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "meteringTime",required = false) @DateTimeFormat(pattern = "yyyy-MM")Date meteringTime,
                           @RequestParam(value = "minPayProportion",required = false)Double minPayProportion,
                           @RequestParam(value = "maxPayProportion",required = false)Double maxPayProportion,
                           @RequestParam(value = "minProductionValue",required = false)Double minProductionValue,
                           @RequestParam(value = "maxProductionValue",required = false)Double maxProductionValue,
                           @RequestParam(value = "page",required = false) Integer page,
                           @RequestParam(value = "pageSize",required = false)Integer pageSize,
                           HttpServletRequest request){

        Long projectId = permissionProject(request);

        Integer offset = page - 1 < 0 ? 0 : page - 1;

        List<MeteringAccount> meteringAccounts = forUpAccountService.listForPage(projectId,projectName,meteringTime,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue,offset*pageSize,pageSize);

        Integer total = forUpAccountService.listForPageSize(projectId,projectName,meteringTime,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue);

        return ResponseVo.ok(total,page,pageSize,meteringAccounts);

    }

    @GetMapping("/total/v1.1")
    @AuthRequire
    public ResponseVo getTotal(@RequestParam(value = "projectName",required = false)String projectName,
                               @RequestParam(value = "meteringTime",required = false) @DateTimeFormat(pattern = "yyyy-MM")Date meteringTime,
                               @RequestParam(value = "minPayProportion",required = false)Double minPayProportion,
                               @RequestParam(value = "maxPayProportion",required = false)Double maxPayProportion,
                               @RequestParam(value = "minProductionValue",required = false)Double minProductionValue,
                               @RequestParam(value = "maxProductionValue",required = false)Double maxProductionValue,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);
        MeteringAccountTotal total = forUpAccountService.getTotal(projectId,projectName,meteringTime,minPayProportion,
                maxPayProportion,minProductionValue,maxProductionValue);
        return ResponseVo.ok(total);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        MeteringAccount meteringAccount = forUpAccountService.getMeteringAccountDetails(id);
        return ResponseVo.ok(meteringAccount);
    }

    /**
     * 导出excel
     * @param projectName
     * @param meteringTime
     * @param minPayProportion
     * @param maxPayProportion
     * @param minProductionValue
     * @param maxProductionValue
     * @param token
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void exportExcel(@RequestParam(value = "projectName",required = false)String projectName,
                                  @RequestParam(value = "meteringTime",required = false)Date meteringTime,
                                  @RequestParam(value = "minPayProportion",required = false)Double minPayProportion,
                                  @RequestParam(value = "maxPayProportion",required = false)Double maxPayProportion,
                                  @RequestParam(value = "minProductionValue",required = false)Double minProductionValue,
                                  @RequestParam(value = "maxProductionValue",required = false)Double maxProductionValue,
                                  @RequestParam("token")String token,HttpServletResponse response,
                                  @RequestParam(value = "exportType",required = false)String exportType,
                            @RequestParam(value = "sort",required = false)List<Integer> sort){
        Long projectId = permissionProjectOnlyToken(token);

        List<MeteringAccount> meteringAccounts = forUpAccountService.listForPage(projectId,projectName,meteringTime,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue,null,null);

        OutputStream out = null;
        HSSFWorkbook wb = null;
        if (exportType != null && sort != null){
            List<ExportConfig> exportConfigs = exportConfigService.findExportConfigs(exportType,sort);
            List<String> titles = Utils.getField(Utils.EXPORT_CONFIG_KEY_TITLE,exportConfigs);
            List<String> fields = Utils.getField(Utils.EXPORT_CONFIG_KEY_FIELD,exportConfigs);
            wb = ExcelUtils.getStandardExcel("对上计量台账",titles,fields,meteringAccounts,"对上计量台账");
        }else {

            String[] titles = {"序号","项目名称","计量期数","计量日期","预付款","含税","税率（%）",
                    "不含税","含税","不含税","已支付金额","未支付金额","拨付率(%)","超计价","已完未计","产值计价率","备注"};
            wb = ExcelUtils.getExcelForUpAccount("对上计量台账","对上计量台账",titles,meteringAccounts);

        }
        try {

            out = response.getOutputStream();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("对上计量台账".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/deleted/v1.1")
    @AuthRequire
    public ResponseVo logicDeleted(@RequestBody MeteringAccount meteringAccount,HttpServletRequest request){
        User user = curUser(request);
        boolean result = forUpAccountService.logicDeletedById(meteringAccount.getId(),user.getId(),new Date());
        if (result)
            return ResponseVo.ok();
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }
}
