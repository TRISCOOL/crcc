package com.crcc.api.controller.subcontractor;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.Subcontractor;
import com.crcc.common.model.User;
import com.crcc.common.service.SubcontractorService;
import com.crcc.common.utils.ExcelUtils;
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
@RequestMapping("/subcontractor")
public class SubcontractorController extends BaseController{

    @Autowired
    private SubcontractorService subcontractorService;

    /**
     * 添加分包商信息
     * @param subcontractor
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo addSubcontractor(@RequestBody Subcontractor subcontractor, HttpServletRequest request){
        User user = curUser(request);
        subcontractor.setCreateUser(user.getId());
        Long id = subcontractorService.addSubcontractor(subcontractor);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("subcontractorId",id);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新分包商信息
     * @param subcontractor
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo updateSubcontractor(@RequestBody Subcontractor subcontractor,HttpServletRequest request){
        User user = curUser(request);
        subcontractor.setUpdateUser(user.getId());
        boolean result = subcontractorService.updateSubcontractor(subcontractor);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 获取列表
     * @param name
     * @param typeId
     * @param professionType
     * @param minAmount
     * @param maxAmount
     * @param shareEvaluation
     * @param groupEvaluation
     * @param companyEvaluation
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo list(@RequestParam(value = "name",required = false) String name,
                           @RequestParam(value = "typeId",required = false) Long typeId,
                           @RequestParam(value = "professionType",required = false) Long professionType,
                           @RequestParam(value = "minAmount",required = false) Integer minAmount,
                           @RequestParam(value = "maxAmount",required = false) Integer maxAmount,
                           @RequestParam(value = "shareEvaluation",required = false) String shareEvaluation,
                           @RequestParam(value = "groupEvaluation",required = false) String groupEvaluation,
                           @RequestParam(value = "companyEvaluation",required = false) String companyEvaluation,
                           @RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){

        Integer offset = page - 1 < 0 ? 0 : page - 1;
        List<Subcontractor> subcontractors = subcontractorService.listSubcontractor(name,typeId,professionType,minAmount,
                maxAmount,shareEvaluation,groupEvaluation,companyEvaluation,offset*pageSize,pageSize);

        return ResponseVo.ok(subcontractors);

    }

    /**
     * 仅仅获取分包商列表，供下拉使用
     * @return
     */
    @GetMapping("/only_list/v1.1")
    public ResponseVo listOnly(){
        List<Subcontractor> subcontractorList = subcontractorService.listSubcontractor(null,
                null,null,null,null,null,null,
                null,null,null);

        return ResponseVo.ok(subcontractorList);
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        return ResponseVo.ok(subcontractorService.getDetails(id));
    }

    /**
     * 导出分包商excel
     * @param name
     * @param typeId
     * @param professionId
     * @param minAmount
     * @param maxAmount
     * @param shareEvaluation
     * @param groupEvaluation
     * @param companyEvaluation
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "typeId",required = false) Long typeId,
                       @RequestParam(value = "professionId",required = false) Long professionId,
                       @RequestParam(value = "minAmount",required = false) Integer minAmount,
                       @RequestParam(value = "maxAmount",required = false) Integer maxAmount,
                       @RequestParam(value = "shareEvaluation",required = false) String shareEvaluation,
                       @RequestParam(value = "groupEvaluation",required = false) String groupEvaluation,
                       @RequestParam(value = "companyEvaluation",required = false) String companyEvaluation,
                       HttpServletResponse response){

        List<Subcontractor> subcontractors = subcontractorService.listSubcontractor(name,typeId,professionId,minAmount,
                maxAmount,shareEvaluation,groupEvaluation,companyEvaluation,null,null);

        String[] titles = {"分包商备案编码","分包商全称","分包商类型","专业类别","纳税人类型","法人","注册资本金（万元）",
                "资质是否齐全","股份公司综合信誉评价","集团公司综合信誉评价","公司本级综合信誉评价","证件期限","备注"};

        HSSFWorkbook hssfWorkbook = ExcelUtils.getSubcontractorExcel("分包商资质信息",titles,subcontractors);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("分包商资质信息".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            hssfWorkbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
