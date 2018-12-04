package com.crcc.api.controller.project;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.config.Constance;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.Dict;
import com.crcc.common.model.Project;
import com.crcc.common.model.ProjectInfo;
import com.crcc.common.model.User;
import com.crcc.common.service.DictService;
import com.crcc.common.service.ProjectService;
import com.crcc.common.utils.ExcelUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController{

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DictService dictService;

    @Value("${pdf.cache.address}")
    private String pdfCacheAddress;

    @Value("${pdf.market.address}")
    private String pdfMarketAddress;

    /**
     * 新增一个项目
     * @param project
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo addProject(@RequestBody Project project, HttpServletRequest request){
        User user = curUser(request);
        project.setCreateUserId(user.getId());
        Long projectId = projectService.addProject(project);
        if (projectId != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("projectId",projectId);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 项目类型列表
     * @return
     */
    @GetMapping("/project_type/v1.1")
    public ResponseVo getProjectType(){
        List<Dict> dicts = dictService.findDictByType(Constance.DICT_PROJECT_TYPE);
        return ResponseVo.ok(dicts);
    }

    /**
     * 更新一个项目
     * @param project
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo updateProject(@RequestBody Project project,HttpServletRequest request){
        User user = curUser(request);
        if (project.getId() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        project.setUpdateUserId(user.getId());
        boolean result = projectService.updateProject(project);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 项目列表
     * @param projectName
     * @param code
     * @param projectType
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo listProject(@RequestParam(value = "projectName",required = false)String projectName,
                                  @RequestParam(value = "code",required = false)String code,
                                  @RequestParam(value = "projectType",required = false)Long projectType,
                                  @RequestParam(value = "status",required = false)Integer status,
                                  @RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize){

        Integer offset = page - 1 < 0 ? 0 : page-1;

        List<Project> projectList = projectService.listProjectForPage(code,projectName, projectType,status,
                offset*pageSize,pageSize);

        Integer total = projectService.listProjectForPageSize(code,projectName, projectType,status);

        return ResponseVo.ok(total,page,pageSize,projectList);
    }

    /**
     * 项目详情
     * @param projectId
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getProjectDetails(@RequestParam("projectId")Long projectId){
        return ResponseVo.ok(projectService.getDetails(projectId));
    }

    /**
     * 获取用户权限内项目列表
     * @param request
     * @return
     */
    @AuthRequire
    @GetMapping("/permission_list/v1.1")
    public ResponseVo getPermissionProjectsList(HttpServletRequest request){
        User user = curUser(request);
        List<Project> projectList = projectService.listProjectForProjectUser(user.getId());
        return ResponseVo.ok(projectList);
    }

    /**
     * 添加信息卡
     * @param projectInfo
     * @return
     */
    @PostMapping("/add_info/v1.1")
    @AuthRequire
    public ResponseVo addProjectInfo(@RequestBody ProjectInfo projectInfo,HttpServletRequest request){
        User user = curUser(request);
        projectInfo.setCreateUser(user.getId());
        Long infoId = projectService.addProjectInfo(projectInfo);
        if (infoId != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("projectInfoId",infoId);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新工程信息卡
     * @param projectInfo
     * @param request
     * @return
     */
    @PostMapping("/update_info/v1.1")
    @AuthRequire
    public ResponseVo updateProjectInfo(@RequestBody ProjectInfo projectInfo,HttpServletRequest request){
        User user = curUser(request);
        projectInfo.setUpdateUser(user.getId());
        boolean result = projectService.updateProjectInfo(projectInfo);
        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 获取工程信息卡详情
     * @param projectInfoId
     * @return
     */
    @GetMapping("/details_info/v1.1")
    public ResponseVo getInfo(@RequestParam(value = "projectInfoId")Long projectInfoId){
        ProjectInfo projectInfo = projectService.getInfo(projectInfoId);
        return ResponseVo.ok(projectInfo);
    }

    /**
     * 删除信息卡
     * @param projectInfo
     * @return
     */
    @PostMapping("/delete_info/v1.1")
    @AuthRequire
    public ResponseVo deleteInfo(@RequestBody ProjectInfo projectInfo){
        if (projectInfo.getId() == null)
            return ResponseVo.error(ResponseCode.PARAM_ILLEGAL);

        boolean result = projectService.deleteInfo(projectInfo.getId());
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 信息卡列表
     * @param projectName
     * @param status
     * @param projectManager
     * @param projectSecretary
     * @param chiefEngineer
     * @param contractStartTime
     * @param contractEndTime
     * @param realContractStartTime
     * @param realContractEndTime
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/info_list/v1.1")
    public ResponseVo listProjectInfo(@RequestParam(value = "projectName",required = false) String projectName,
                                      @RequestParam(value = "status",required = false) Integer status,
                                      @RequestParam(value = "projectManager",required = false) String projectManager,
                                      @RequestParam(value = "projectSecretary",required = false)String projectSecretary,
                                      @RequestParam(value = "chiefEngineer",required = false) String chiefEngineer,
                                      @RequestParam(value = "contractStartTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date contractStartTime,
                                      @RequestParam(value = "contractEndTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date contractEndTime,
                                      @RequestParam(value = "realContractStartTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date realContractStartTime,
                                      @RequestParam(value = "realContractEndTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date realContractEndTime,
                                      @RequestParam(value = "page",required = false)Integer page,
                                      @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                      HttpServletRequest request){
        Long projectId = permissionProject(request);

        //TODO 项目部以上人员权限

        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<ProjectInfo> projectInfoList = projectService.listProjectInfoForUser(projectId,projectName,status,projectManager,
                projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime,offset*pageSize,pageSize);

        Integer total = projectService.listProjectInfoForUserSize(projectId,projectName,status,projectManager,
                projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime);

        return ResponseVo.ok(total,page,pageSize,projectInfoList);

    }

    @GetMapping("/pdf/v1.1")
    public void exportPdfForInfo(@RequestParam("projectInfoId")Long projectInfoId,
                                 HttpServletResponse response,HttpServletRequest request) throws Exception{



        ProjectInfo projectInfo = projectService.getInfo(projectInfoId);
        if (projectInfo == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        String fileUrl = pdfCacheAddress+projectInfo.getProjectName()+System.currentTimeMillis()+".pdf";

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/pdf");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+new String((projectInfo.getProjectName()+"工程信息卡").getBytes("UTF-8"), "ISO8859-1")+".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileUrl));
        document.open();
        setContentForPDF(projectInfo,document);
        document.close();

        PdfReader reader = new PdfReader(fileUrl);
        PdfStamper stamper = new PdfStamper(reader,response.getOutputStream());
        Image img = Image.getInstance(pdfMarketAddress);
        img.setAbsolutePosition(200, 400);
        PdfContentByte under = stamper.getUnderContent(1);
        under.addImage(img);
        stamper.close();
        reader.close();

    }

    private void setContentForPDF(ProjectInfo projectInfo,Document document) throws Exception{
        if (document == null)
            return;

        BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 12,Font.NORMAL);

        Paragraph title = new Paragraph(projectInfo.getProjectName()+"工程项目信息卡",font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph name = new Paragraph("项目名称："+projectInfo.getProjectName()+
                "        工程类别："+projectInfo.getProjectType(),font);
        document.add(name);

        Paragraph address = new Paragraph("工程地点："+projectInfo.getAddress(),font);
        document.add(address);

    }

    /**
     * 导出工程信息卡
     * @param projectName
     * @param status
     * @param projectManager
     * @param projectSecretary
     * @param chiefEngineer
     * @param contractStartTime
     * @param contractEndTime
     * @param realContractStartTime
     * @param realContractEndTime
     * @param request
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void exportProjectInfoList(@RequestParam(value = "projectName",required = false) String projectName,
                                      @RequestParam(value = "status",required = false) Integer status,
                                      @RequestParam(value = "projectManager",required = false) String projectManager,
                                      @RequestParam(value = "projectSecretary",required = false)String projectSecretary,
                                      @RequestParam(value = "chiefEngineer",required = false) String chiefEngineer,
                                      @RequestParam(value = "contractStartTime",required = false) Date contractStartTime,
                                      @RequestParam(value = "contractEndTime",required = false) Date contractEndTime,
                                      @RequestParam(value = "realContractStartTime",required = false) Date realContractStartTime,
                                      @RequestParam(value = "realContractEndTime",required = false) Date realContractEndTime,
                                      HttpServletRequest request, HttpServletResponse response) {

        User user = curUser(request);
        List<ProjectInfo> projectInfoList = projectService.listProjectInfoForUser(null,projectName,status,
                projectManager,projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime,null,null);

        OutputStream out = null;
        try {

            String[] titles = {"项目编码","项目名称","工程状态","合同开工日期","合同完工日期","实际开工日期","实际完工日期",
                    "暂估合同额","有效合同额","项目经理","项目书记","总工"};

            out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getHSSFWorkbookForProjectInfo("工程信息卡",titles,projectInfoList);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+new String("工程信息卡".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
