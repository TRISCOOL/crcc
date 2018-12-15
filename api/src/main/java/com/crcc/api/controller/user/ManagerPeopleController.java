package com.crcc.api.controller.user;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.Personnel;
import com.crcc.common.model.Subcontractor;
import com.crcc.common.model.User;
import com.crcc.common.service.PersonnelService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.ExcelUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class ManagerPeopleController extends BaseController{

    @Autowired
    private PersonnelService personnelService;

    @Value("${pdf.cache.address}")
    private String pdfCacheAddress;

    @Value("${pdf.market.address}")
    private String pdfMarketAddress;

    /**
     * 新增
     * @param personnel
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody Personnel personnel, HttpServletRequest request){
        User user = curUser(request);
        personnel.setCreateUser(user.getId());
        Long id = personnelService.addPersonnel(personnel);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新
     * @param personnel
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody Personnel personnel,HttpServletRequest request){
        User user = curUser(request);
        boolean result = personnelService.updatePersonnel(personnel);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 列表
     * @param name
     * @param projectName
     * @param position
     * @param workTime
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo listForPage(@RequestParam(value = "name",required = false) String name,
                                  @RequestParam(value = "projectName",required = false) String projectName,
                                  @RequestParam(value = "position",required = false) String position,
                                  @RequestParam(value = "workTime",required = false) Integer workTime,
                                  @RequestParam(value = "page") Integer page,@RequestParam("pageSize") Integer pageSize){

        Integer offset = page - 1<0 ? 0:page-1;

        List<Personnel> personnels = personnelService.listForPage(name,projectName,position,workTime,offset*pageSize,pageSize);

        Integer total = personnelService.listForPageSize(name,projectName,position,workTime);

        return ResponseVo.ok(total,page,pageSize,personnels);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo details(@RequestParam(value = "id")Long id){
        return ResponseVo.ok(personnelService.getDetails(id));
    }

    /**
     * 导出
     * @param name
     * @param projectName
     * @param position
     * @param workTime
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "projectName",required = false) String projectName,
                       @RequestParam(value = "position",required = false) String position,
                       @RequestParam(value = "workTime",required = false) Integer workTime,
                       HttpServletResponse response){

        String[] titles = {"人员编码","姓名","性别","当前状态","项目名称","职务","职称","参加工作年限","学历","手机号码","QQ号码",
        "身份证号码","已取得证书","籍贯（省市区/县）","创建时间","备注"};

        List<Personnel> personnelList = personnelService.listForPage(name,projectName,position,workTime,null,null);
        HSSFWorkbook wb = ExcelUtils.getPersonnelExcel("经管人员",titles,personnelList);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+new String("经管人员".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导出人员履历pdf
     * @param peopleId
     * @param response
     * @param request
     * @throws Exception
     */
    @GetMapping("/pdf/v1.1")
    public void exportPdfForInfo(@RequestParam("peopleId")Long peopleId,
                                 HttpServletResponse response,HttpServletRequest request) throws Exception{



        Personnel personnel = personnelService.getDetails(peopleId);
        if (peopleId == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        String fileUrl = pdfCacheAddress+personnel.getName()+System.currentTimeMillis()+".pdf";

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/pdf");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+new String((personnel.getName()+"个人履历表").getBytes("UTF-8"), "ISO8859-1")+".pdf");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileUrl));
        document.open();
        setContentForPDF(personnel,document,writer);
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

    private void setContentForPDF(Personnel personnel,Document document,PdfWriter writer) throws Exception{
        if (document == null)
            return;

        BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 12,Font.NORMAL);

        Paragraph title = new Paragraph(personnel.getName()+"个人履历表",font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        float[] widths = {0.1f, 0.1f, 0.1f,0.1f, 0.1f,0.1f,0.3f};
        PdfPTable table = new PdfPTable(widths);
        PdfPCell cell;
        cell = new PdfPCell(getTitle("姓名",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getName(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("性别",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getSex(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("出生年月",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(personnel.getBrithday()),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("照片",font));
        cell.setColspan(1);
        cell.setRowspan(4);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("名族",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getFamousFamily(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("籍贯",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getJiguan(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("出生地",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getBirthplace(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("入党时间",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(personnel.getJoinAssociationTime()),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("参加工作时间",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getWorkTime()+"年",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("健康状况",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getHealth(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("专业职务",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getPosition(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("职称",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getJobTitle(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(getTitle("有何特长",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getSpecialty(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("第一学历",font));
        cell.setColspan(1);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("毕业院校",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getFirstDegreeSchool(),font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("专业",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getFirstDegreeProfession(),font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("毕业时间",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(personnel.getFirstDegreeTime()),font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("专科/本科",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getFirstDegreeLevel(),font));
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("第二学历",font));
        cell.setColspan(1);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("毕业院校",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getSecondDegreeSchool(),font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("专业",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getSecondDegreeProfession(),font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("毕业时间",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(personnel.getSecondDegreeTime()),font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("专科/本科",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getSecondDegreeLevel(),font));
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("家庭住址",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getHomeAddress(),font));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("身份证号",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getIdCard(),font));
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("联系电话",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getPhone(),font));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("QQ号",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getQqNumber(),font));
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("工作经历",font));
        cell.setColspan(1);
        cell.setRowspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getWorkExperience(),font));
        cell.setColspan(6);
        cell.setRowspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("职业资格证书取证情况",font));
        cell.setColspan(1);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getCertificate(),font));
        cell.setColspan(6);
        cell.setRowspan(2);
        table.addCell(cell);


        cell = new PdfPCell(new Paragraph("学习及培训经历",font));
        cell.setColspan(1);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getTraining(),font));
        cell.setColspan(6);
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("获奖励和受表彰情况",font));
        cell.setColspan(1);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(personnel.getAward(),font));
        cell.setColspan(6);
        cell.setRowspan(2);
        table.addCell(cell);

        document.add(table);
    }

    private Paragraph getTitle(String value,Font font){
        Paragraph paragraph = new Paragraph(value,font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }
}
