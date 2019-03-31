package com.crcc.api.controller.project;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.config.Constance;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.*;
import com.crcc.common.service.DictService;
import com.crcc.common.service.ExportConfigService;
import com.crcc.common.service.ProjectService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
import com.google.gson.reflect.TypeToken;
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
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController{

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DictService dictService;

    @Autowired
    private ExportConfigService exportConfigService;

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
     * 导出项目管理
     * @param projectName
     * @param code
     * @param projectType
     * @param status
     * @return
     */
    @GetMapping("/export_project/v1.1")
    public void exportProject(@RequestParam(value = "projectName",required = false)String projectName,
                                    @RequestParam(value = "code",required = false)String code,
                                    @RequestParam(value = "projectType",required = false)Long projectType,
                                    @RequestParam(value = "status",required = false)Integer status,
                                    HttpServletResponse response){

        List<Project> projectList = projectService.listProjectForPage(code,projectName,projectType,status,
                null,null);

        OutputStream out = null;
        try {

            String[] titles = {"项目编码","项目名称","工程类别","状态","创建人","创建时间"};

            out = response.getOutputStream();
            HSSFWorkbook wb = ExcelUtils.getHSSFWorkbookForProject("项目管理",titles,projectList);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+new String("项目管理".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    @GetMapping("/permission_list/v1.1")
    @AuthRequire
    public ResponseVo getPermissionProjectsList(HttpServletRequest request){
        User user = curUser(request);
        List<Project> projectList = projectService.listProjectForProjectUser(user.getId());

        if (projectList != null && projectList.size() <= 0)
            return ResponseVo.ok();

        projectList.forEach(project -> {
            if (project != null){
                Integer distance = DateTimeUtil.getDistanceMonthByDate(project.getContractStartTime(),project.getContractEndTime());
                project.setDistanceTime(distance);
            }
        });
        return ResponseVo.ok(projectList);
    }

    /**
     * 添加信息卡
     * @param pojo
     * @return
     */
    @PostMapping("/add_info/v1.1")
    @AuthRequire
    public ResponseVo addProjectInfo(@RequestBody ProjectInfoPojo pojo, HttpServletRequest request){
        User user = curUser(request);

        pojo.setCreateUser(user.getId());
        ProjectInfo projectInfo = ProjectInfo.getInfoByPojo(pojo);
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
     * @param pojo
     * @param request
     * @return
     */
    @PostMapping("/update_info/v1.1")
    @AuthRequire
    public ResponseVo updateProjectInfo(@RequestBody ProjectInfoPojo pojo,HttpServletRequest request){
        User user = curUser(request);
        pojo.setUpdateUser(user.getId());

        ProjectInfo projectInfo = ProjectInfo.getInfoByPojo(pojo);
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
        ProjectInfoPojo pojo = ProjectInfoPojo.getPojoByInfo(projectInfo);
        return ResponseVo.ok(pojo);
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
                                      @RequestParam(value = "contractStartTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date contractStartTime,
                                      @RequestParam(value = "contractEndTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date contractEndTime,
                                      @RequestParam(value = "realContractStartTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date realContractStartTime,
                                      @RequestParam(value = "realContractEndTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date realContractEndTime,
                                      @RequestParam(value = "page",required = false)Integer page,
                                      @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                      HttpServletRequest request){
        //TODO 项目部以上人员权限
        Long projectId = permissionProject(request);

        projectName = replaceNull(projectName);
        projectManager = replaceNull(projectManager);
        projectSecretary = replaceNull(projectSecretary);
        chiefEngineer = replaceNull(chiefEngineer);

        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<ProjectInfo> projectInfoList = projectService.listProjectInfoForUser(projectId,projectName,status,projectManager,
                projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime,offset*pageSize,pageSize);

        Integer total = projectService.listProjectInfoForUserSize(projectId,projectName,status,projectManager,
                projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime);

        List<ProjectInfoPojo> pojos = projectInfoList.stream().map(projectInfo -> {
            ProjectInfoPojo pojo = ProjectInfoPojo.getPojoByInfo(projectInfo);
            return pojo;
        }).collect(Collectors.toList());

        return ResponseVo.ok(total,page,pageSize,pojos);

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
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileUrl));
        document.open();
        setContentForPDF(projectInfo,document,writer);
        document.close();

        PdfReader reader = new PdfReader(fileUrl);
        PdfStamper stamper = new PdfStamper(reader,response.getOutputStream());
        Image img = Image.getInstance(pdfMarketAddress);
        img.setAbsolutePosition(400, 720);
        PdfContentByte under = stamper.getUnderContent(1);
        under.addImage(img);
        stamper.close();
        reader.close();

    }

    private void setContentForPDF(ProjectInfo projectInfo,Document document,PdfWriter writer) throws Exception{
        if (document == null)
            return;

        BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 12,Font.NORMAL);

        Font titleFont = new Font(bfChinese,12,Font.BOLD);

        Paragraph title = new Paragraph(projectInfo.getProjectName()+"工程项目信息卡",titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        Paragraph kb1 = new Paragraph(" ");
        document.add(title);
        document.add(kb1);

        float[] widths = {70, 70, 70,70, 70,70,70};
        PdfPTable table = new PdfPTable(widths);
        table.setLockedWidth(true);
        table.setTotalWidth(490);

        PdfPCell cell;
        cell = Utils.getNewCell(getTitle("工程名称",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getProjectName(),font),5,
                null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("工程地点",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getAddress(),titleFont),2,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("工程状态",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getStatusStr(),titleFont),2,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("里程桩号",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getMileageNumber()+"",font),2,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("项目部地址",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getOrgAddress(),titleFont),2,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("合同总价",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getTotalPrice().doubleValue()+"",titleFont),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("合同编号",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getContractNumber(),titleFont),3,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("合同工期",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getContractDay()+"月",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("合同开工日期",titleFont),1,null,true,false);
        cell.setColspan(1);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(projectInfo.getContractStartTime()),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("合同竣工日期",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(projectInfo.getContractEndTime()),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("实际工期",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getRealContractDay()+"月",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("实际开工时间",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(projectInfo.getRealContractStartTime()),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("实际竣工时间",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(projectInfo.getRealContractEndTime()),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("业主单位",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getProprietorCompany(),font),3,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("联系方式",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getProprietorPhone(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("监理单位",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getSupervisionCompany(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("监理地址",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getSupervisionAddress(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("联系方式",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getSupervisionPhone(),font),1,null,true,false);
        table.addCell(cell);

        createParagrap(getProjectInfoPeoples(projectInfo.getManager()),table,cell,font,"项目经理",titleFont);
        createParagrap(getProjectInfoPeoples(projectInfo.getSecretary()),table,cell,font,"项目书记",titleFont);
        createParagrap(getProjectInfoPeoples(projectInfo.getEngineer()),table,cell,font,"项目总工",titleFont);

        cell = Utils.getNewCell(getTitle("投入人员",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(isNullAndUnit(projectInfo.getInputPerson(),"人"),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("正式职工",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(isNullAndUnit(projectInfo.getFormalEmployee(),"人"),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(getTitle("外聘",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(isNullAndUnit(projectInfo.getExternalEmployee(),"人"),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("工程概述",titleFont),1,3,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(projectInfo.getDescription(),font),6,null,true,false);
        cell.setColspan(6);
        table.addCell(cell);

        document.add(table);

    }

    private String isNullAndUnit(Integer content,String unit){
        if (content == null){
            return "";
        }

        return content+unit;
    }

    private void createParagrap(List<ProjectInfoPeople> projectInfoPeople,PdfPTable table,
                                PdfPCell cell,Font font,String title,Font tilteFont) throws Exception{
        if (projectInfoPeople == null)
            return;

        if (projectInfoPeople.size()<=0){
            return;
        }

        cell = Utils.getNewCell(getTitle(title,font),1,projectInfoPeople.size(),true,true);
        table.addCell(cell);
        for (ProjectInfoPeople people : projectInfoPeople){
            cell = Utils.getNewCell(new Paragraph("姓名",tilteFont),1,null,true,false);
            table.addCell(cell);
            cell = Utils.getNewCell(new Paragraph(people.getName(),font),1,null,true,false);
            table.addCell(cell);
            cell = Utils.getNewCell(new Paragraph("任职时间",tilteFont),1,null,true,false);
            table.addCell(cell);
            cell = Utils.getNewCell(new Paragraph(people.getTime(),font),1,null,true,false);
            table.addCell(cell);
            cell = Utils.getNewCell(new Paragraph("联系方式",tilteFont),1,null,true,false);
            table.addCell(cell);
            cell = Utils.getNewCell(new Paragraph(people.getPhone(),font),1,null,true,false);
            table.addCell(cell);
        }
    }

    private List<ProjectInfoPeople> getProjectInfoPeoples(String str){
        if (str == null)
            return null;
        if ("".equals(str))
            return null;

        List<ProjectInfoPeople> projectInfoPeople = Utils.fromJson(str,new TypeToken<List<ProjectInfoPeople>>(){});
        return projectInfoPeople;
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
     * @param token
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
                                      @RequestParam("token")String token,
                                      @RequestParam(value = "sort",required = false)List<Integer> sort,
                                      @RequestParam(value = "exportType",required = false)String exportType,
                                      HttpServletResponse response) {

        Long projectId = permissionProjectOnlyToken(token);
        List<ProjectInfo> projectInfoList = projectService.listProjectInfoForUser(projectId,projectName,status,
                projectManager,projectSecretary,chiefEngineer,contractStartTime,contractEndTime,realContractStartTime,
                realContractEndTime,null,null);

        HSSFWorkbook wb = null;

        if (exportType != null && sort != null){
            List<ExportConfig> exportConfigs = exportConfigService.findExportConfigs(exportType,sort);
            List<String> titles = Utils.getField(Utils.EXPORT_CONFIG_KEY_TITLE,exportConfigs);
            List<String> fields = Utils.getField(Utils.EXPORT_CONFIG_KEY_FIELD,exportConfigs);
            if (sort.contains(2)){
                projectInfoList.forEach(projectInfo -> {
                    projectInfo.setManager(ExcelUtils.getProjectNameFirst(projectInfo.getManager()));
                    projectInfo.setSecretary(ExcelUtils.getProjectNameFirst(projectInfo.getSecretary()));
                    projectInfo.setEngineer(ExcelUtils.getProjectNameFirst(projectInfo.getEngineer()));
                });
            }
            wb = ExcelUtils.getStandardExcel("工程信息卡",titles,fields,projectInfoList,"工程信息卡");
        }else {

            String[] titles = {"项目编码","项目名称","工程状态","合同开工日期","合同完工日期","实际开工日期","实际完工日期",
                    "暂估合同额","有效合同额","项目经理","项目书记","总工"};

            wb = ExcelUtils.getHSSFWorkbookForProjectInfo("工程信息卡",titles,projectInfoList);
        }

        OutputStream out = null;
        try {


            out = response.getOutputStream();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Content-disposition", "attachment; filename="+new String("工程信息卡".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String replaceNull(String str){
        if (str != null && "null".equals(str)){
            return null;
        }
        return str;
    }

    private Paragraph getTitle(String value,Font font){
        Paragraph paragraph = new Paragraph(value,font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }


}
