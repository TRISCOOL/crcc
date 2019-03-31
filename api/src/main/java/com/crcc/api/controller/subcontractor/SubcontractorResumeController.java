package com.crcc.api.controller.subcontractor;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.SubcontractorResume;
import com.crcc.common.model.User;
import com.crcc.common.service.SubcontractorResumeService;
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
@RequestMapping("/subcontractor_resume")
public class SubcontractorResumeController extends BaseController{

    @Autowired
    private SubcontractorResumeService subcontractorResumeService;

    /**
     * 新增一个履历
     * @param subcontractorResume
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody SubcontractorResume subcontractorResume, HttpServletRequest request){
        User user = curUser(request);
        subcontractorResume.setCreateUser(user.getId());
        Long id = subcontractorResumeService.addResume(subcontractorResume);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新一个分包商履历
     * @param subcontractorResume
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody SubcontractorResume subcontractorResume,HttpServletRequest request){
        if (subcontractorResume.getId() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        User user = curUser(request);

        subcontractorResume.setUpdateUser(user.getId());
        boolean result = subcontractorResumeService.updateResume(subcontractorResume);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 列表
     * @param subcontractorName
     * @param projectEvaluation
     * @param gm
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo listForPage(@RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                                  @RequestParam(value = "projectEvaluation",required = false)String projectEvaluation,
                                  @RequestParam(value = "gm",required = false)String gm,
                                  @RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){

        Integer offset = page - 1 < 0 ? 0 : page - 1;

        List<SubcontractorResume> resumeList = subcontractorResumeService.listSubcontractorResumeForPage(
                subcontractorName,projectEvaluation,gm,offset*pageSize,pageSize);

        Integer total = subcontractorResumeService.listSubcontractorResumeForPageSize(subcontractorName,projectEvaluation,gm);

        return ResponseVo.ok(total,page,pageSize,resumeList);
    }

    /**
     * 某一个分包商履历列表
     * @param subcontractorId
     * @return
     */
    @GetMapping("/list_for_subcontractor/v1.1")
    public ResponseVo listResumesForSubcontractor(@RequestParam("subcontractorId")Long subcontractorId){
        return ResponseVo.ok(subcontractorResumeService.listResumeBySubcontractorId(subcontractorId));
    }

    /**
     * 获得详情
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        return ResponseVo.ok(subcontractorResumeService.getDetails(id));
    }

    /**
     * 导出分包商履历
     * @param subcontractorName
     * @param projectEvaluation
     * @param gm
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "subcontractorName",required = false)String subcontractorName,
                             @RequestParam(value = "projectEvaluation",required = false)String projectEvaluation,
                             @RequestParam(value = "gm",required = false)String gm, HttpServletResponse response){

        List<SubcontractorResume> subcontractorResumeList = subcontractorResumeService.listSubcontractorResumeForPage(
                subcontractorName,projectEvaluation,gm,null,null);

        String[] titles = {"分包商备案编码","分包商全称","队伍名称","施工规模","开始日期","结束日期","该时间段所属项目部",
        "合同签订人","合同签订人联系方式","合同金额","结算金额","项目部评价","项目部评价"};

        HSSFWorkbook wb = ExcelUtils.getResumeExcel("分包商履历","分包商履历",titles,subcontractorResumeList);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+
                    new String("分包商履历".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/deleted/v1.1")
    @AuthRequire
    public ResponseVo deletedById(@RequestParam("id")Long id){
        boolean result =subcontractorResumeService.deletedById(id);
        if (result)
            return ResponseVo.ok();
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

}
