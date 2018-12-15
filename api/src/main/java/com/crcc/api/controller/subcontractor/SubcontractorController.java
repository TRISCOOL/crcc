package com.crcc.api.controller.subcontractor;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.ProjectInfo;
import com.crcc.common.model.ProjectInfoPeople;
import com.crcc.common.model.Subcontractor;
import com.crcc.common.model.User;
import com.crcc.common.service.SubcontractorService;
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
@RequestMapping("/subcontractor")
public class SubcontractorController extends BaseController{

    @Autowired
    private SubcontractorService subcontractorService;

    @Value("${pdf.cache.address}")
    private String pdfCacheAddress;

    @Value("${pdf.market.address}")
    private String pdfMarketAddress;

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
     * @param type
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
                           @RequestParam(value = "type",required = false) String type,
                           @RequestParam(value = "professionType",required = false) Long professionType,
                           @RequestParam(value = "minAmount",required = false) Integer minAmount,
                           @RequestParam(value = "maxAmount",required = false) Integer maxAmount,
                           @RequestParam(value = "shareEvaluation",required = false) String shareEvaluation,
                           @RequestParam(value = "groupEvaluation",required = false) String groupEvaluation,
                           @RequestParam(value = "companyEvaluation",required = false) String companyEvaluation,
                           @RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){

        Integer offset = page - 1 < 0 ? 0 : page - 1;
        List<Subcontractor> subcontractors = subcontractorService.listSubcontractor(name,type,professionType,minAmount,
                maxAmount,shareEvaluation,groupEvaluation,companyEvaluation,offset*pageSize,pageSize);

        Integer total = subcontractorService.listSubcontractorSize(name,type,professionType,minAmount,
                maxAmount,shareEvaluation,groupEvaluation,companyEvaluation);

        return ResponseVo.ok(total,page,pageSize,subcontractors);

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
     * @param type
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
                       @RequestParam(value = "type",required = false) String type,
                       @RequestParam(value = "professionId",required = false) Long professionId,
                       @RequestParam(value = "minAmount",required = false) Integer minAmount,
                       @RequestParam(value = "maxAmount",required = false) Integer maxAmount,
                       @RequestParam(value = "shareEvaluation",required = false) String shareEvaluation,
                       @RequestParam(value = "groupEvaluation",required = false) String groupEvaluation,
                       @RequestParam(value = "companyEvaluation",required = false) String companyEvaluation,
                       HttpServletResponse response){

        List<Subcontractor> subcontractors = subcontractorService.listSubcontractor(name,type,professionId,minAmount,
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

    /**
     * 导出分包商资质信息卡pdf
     * @param subcontractorId
     * @param response
     * @param request
     * @throws Exception
     */
    @GetMapping("/pdf/v1.1")
    public void exportPdfForInfo(@RequestParam("subcontractorId")Long subcontractorId,
                                 HttpServletResponse response,HttpServletRequest request) throws Exception{



        Subcontractor subcontractor = subcontractorService.getDetails(subcontractorId);
        if (subcontractor == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        String fileUrl = pdfCacheAddress+subcontractor.getName()+System.currentTimeMillis()+".pdf";

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/pdf");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+new String((subcontractor.getName()+"资质信息卡").getBytes("UTF-8"), "ISO8859-1")+".pdf");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileUrl));
        document.open();
        setContentForPDF(subcontractor,document,writer);
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

    private void setContentForPDF(Subcontractor subcontractor,Document document,PdfWriter writer) throws Exception{
        if (document == null)
            return;

        BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 12,Font.NORMAL);

        Paragraph title = new Paragraph(subcontractor.getName()+"资质信息卡",font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        float[] widths = {0.15f, 0.1f, 0.2f,0.1f, 0.2f,0.1f,0.15f};
        PdfPTable table = new PdfPTable(widths);
        PdfPCell cell;
        cell = new PdfPCell(getTitle("分包商全称",font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getName(),font));
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("成立日期",font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getSetUpTime()),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("纳税人类型",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getTaxpayerType(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("注册资本金",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getRegisteredCapital().doubleValue()+"",font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("类型",font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getType(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("电话",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getPhone(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("电子邮箱",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getEmail(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("注册地址",font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getAddress(),font));
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("邮编",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getZipCode(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("法定代表人",font));
        cell.setRowspan(2);
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("姓名",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getName(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("职务",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getLegalPersonPosition(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("身份证号码",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getLegalPersonCard(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("联系方式",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getLegalPersonPhone(),font));
        cell.setColspan(1);
        table.addCell(cell);
        Paragraph address = new Paragraph("家庭地址",font);
        address.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(address);
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getLegalPersonAddress(),font));
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("营业执照",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("统一社会信用代码",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getBusinessLicenseCode(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("有效期限",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getBusinessLicenseValidityPeriod()),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("发证机关",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getBusinessLicenseFrom(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("资质证书",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("证书编号",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getQualificationCode(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("有效期限",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getQualificationValidityPeriod()),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("发证机关",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getQualificationFrom(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("安全生产许可证",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("编号",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getSafetyCode(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("有效期限",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getSafetyValidityPeriod()),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("发证机关",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getSafetyFrom(),font));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(getTitle("开户银行许可证",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("开户银行",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getBank(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("银行账号",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getBankAccount(),font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("发证机关",font));
        cell.setColspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(subcontractor.getBankFrom(),font));
        cell.setColspan(1);
        table.addCell(cell);

        document.add(table);
    }

    private Paragraph getTitle(String value,Font font){
        Paragraph paragraph = new Paragraph(value,font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

}
