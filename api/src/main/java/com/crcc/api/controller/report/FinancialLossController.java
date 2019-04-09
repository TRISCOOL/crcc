package com.crcc.api.controller.report;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.FinancialLoss;
import com.crcc.common.model.FinancialLossTotal;
import com.crcc.common.model.User;
import com.crcc.common.service.FinancialLossService;
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

@RequestMapping("/loss")
@RestController
public class FinancialLossController extends BaseController{

    @Autowired
    private FinancialLossService financialLossService;

    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody FinancialLoss financialLoss, HttpServletRequest request){
        User user = curUser(request);
        financialLoss.setCreateUser(user.getId());
        Long id = financialLossService.addFinancialLoss(financialLoss);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody FinancialLoss financialLoss,HttpServletRequest request){
        User user = curUser(request);
        financialLoss.setUpdateUser(user.getId());
        boolean result = financialLossService.updateFinancialLoss(financialLoss);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     *
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

        projectName = Utils.getBlurryKeyString(projectName);

        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<FinancialLoss> financialLosses = financialLossService.listForPage(projectId,projectName,year,quarter,
                offset*pageSize, pageSize);
        Integer total = financialLossService.listForPageSize(projectId,projectName,year,quarter);
        return ResponseVo.ok(total,page,pageSize,financialLosses);
    }

    @GetMapping("/total/v1.1")
    @AuthRequire
    public ResponseVo getTotal(@RequestParam(value = "projectName",required = false)String projectName,
                               @RequestParam(value = "year",required = false)String year,
                               @RequestParam(value = "quarter",required = false)Integer quarter,
                               HttpServletRequest request){
        Long projectId = permissionProject(request);

        projectName = Utils.getBlurryKeyString(projectName);

        FinancialLossTotal total = financialLossService.getTotal(projectId,projectName,year,quarter);
        return ResponseVo.ok(total);
    }

    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "projectName",required = false)String projectName,
                       @RequestParam(value = "year",required = false)String year,
                       @RequestParam(value = "quarter",required = false)Integer quarter,
                       @RequestParam("token")String token, HttpServletResponse response){
        Long projectId = permissionProjectOnlyToken(token);

        projectName = Utils.getBlurryKeyString(projectName);

        List<FinancialLoss> financialLosses = financialLossService.listForPage(projectId,projectName,year,quarter,
                null,null);
        String[] titles = {"序号","项目名称","填报时间","合同金额（万元）","已计价","未计价","小计","财务确认收入","账内成本",
        "账外成本","小计","亏损金额","财务已确认净利润","财务未确认的亏损","亏损比例","应收客户合同工程款","预付账款","其他","小计",
                "账外潜亏","合计","备注"};

        try {
            OutputStream out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getLossExcel("过程亏损项目明细表",
                    "过程亏损项目明细表",titles,financialLosses);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("过程亏损项目明细表".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/delete/v1.1")
    @AuthRequire
    public ResponseVo delete(@RequestBody FinancialLoss loss){
        boolean result = financialLossService.deleteOnById(loss.getId());
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }
}
