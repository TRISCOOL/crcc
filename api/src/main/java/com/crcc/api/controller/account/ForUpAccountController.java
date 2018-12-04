package com.crcc.api.controller.account;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.MeteringAccount;
import com.crcc.common.model.User;
import com.crcc.common.service.ForUpAccountService;
import com.crcc.common.utils.ExcelUtils;
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
    public ResponseVo list(@RequestParam(value = "projectName",required = false)String projectName,
                           @RequestParam(value = "meteringTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date meteringTime,
                           @RequestParam(value = "minPayProportion",required = false)Double minPayProportion,
                           @RequestParam(value = "maxPayProportion",required = false)Double maxPayProportion,
                           @RequestParam(value = "minProductionValue",required = false)Double minProductionValue,
                           @RequestParam(value = "maxProductionValue",required = false)Double maxProductionValue,
                           @RequestParam("page") Integer page,@RequestParam("pageSize")Integer pageSize,
                           HttpServletRequest request){

        Long projectId = permissionProject(request);

        Integer offset = page - 1 < 0 ? 0 : page - 1;

        List<MeteringAccount> meteringAccounts = forUpAccountService.listForPage(projectId,projectName,meteringTime,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue,offset*pageSize,pageSize);

        Integer total = forUpAccountService.listForPageSize(projectId,projectName,meteringTime,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue);

        return ResponseVo.ok(total,page,pageSize,meteringAccounts);

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
     * @param request
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void exportExcel(@RequestParam(value = "projectName",required = false)String projectName,
                                  @RequestParam(value = "meteringTime",required = false)Date meteringTime,
                                  @RequestParam(value = "minPayProportion",required = false)Double minPayProportion,
                                  @RequestParam(value = "maxPayProportion",required = false)Double maxPayProportion,
                                  @RequestParam(value = "minProductionValue",required = false)Double minProductionValue,
                                  @RequestParam(value = "maxProductionValue",required = false)Double maxProductionValue,
                                  HttpServletRequest request, HttpServletResponse response){
        Long projectId = permissionProject(request);

        List<MeteringAccount> meteringAccounts = forUpAccountService.listForPage(projectId,projectName,meteringTime,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue,null,null);

        OutputStream out = null;
        try {

            String[] titles = {"序号","项目名称","计量期数","计量日期","预付款","含税","税率（%）",
                    "不含税","含税","不含税","已支付金额","未支付金额","拨付率(%)","超计价","已完未计","产值计价率","备注"};

            out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getExcelForUpAccount("对上计量台账",titles,meteringAccounts);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("对上计量台账".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
