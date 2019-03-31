package com.crcc.api.controller.subcontractor;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.*;
import com.crcc.common.service.ExportConfigService;
import com.crcc.common.service.SubcontractorResumeService;
import com.crcc.common.service.SubcontractorService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subcontractor")
public class SubcontractorController extends BaseController{

    @Autowired
    private SubcontractorService subcontractorService;

    @Autowired
    private ExportConfigService exportConfigService;

    @Autowired
    private SubcontractorResumeService subcontractorResumeService;

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

        if (!subcontractorService.isCanAdd(subcontractor)){
            return ResponseVo.error(ResponseCode.SUBCONTRACTOR_ALREADY_EXISTS);
        }

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
                           @RequestParam(value = "professionType",required = false) String professionType,
                           @RequestParam(value = "minAmount",required = false) Integer minAmount,
                           @RequestParam(value = "maxAmount",required = false) Integer maxAmount,
                           @RequestParam(value = "shareEvaluation",required = false) String shareEvaluation,
                           @RequestParam(value = "groupEvaluation",required = false) String groupEvaluation,
                           @RequestParam(value = "companyEvaluation",required = false) String companyEvaluation,
                           @RequestParam(value = "isValid",required = false)Integer isValid,
                           @RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){

        Integer offset = page - 1 < 0 ? 0 : page - 1;
        List<Subcontractor> subcontractors = subcontractorService.listSubcontractor(name,type,professionType,minAmount,
                maxAmount,shareEvaluation,groupEvaluation,companyEvaluation,offset*pageSize,pageSize,isValid);

        Integer total = subcontractorService.listSubcontractorSize(name,type,professionType,minAmount,
                maxAmount,shareEvaluation,groupEvaluation,companyEvaluation,isValid);

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
                null,null,null,null);

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
     * @param professionType
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
                       @RequestParam(value = "professionType",required = false) String professionType,
                       @RequestParam(value = "minAmount",required = false) Integer minAmount,
                       @RequestParam(value = "maxAmount",required = false) Integer maxAmount,
                       @RequestParam(value = "shareEvaluation",required = false) String shareEvaluation,
                       @RequestParam(value = "groupEvaluation",required = false) String groupEvaluation,
                       @RequestParam(value = "companyEvaluation",required = false) String companyEvaluation,
                       @RequestParam(value = "exportType",required = false)String exportType,
                       @RequestParam(value = "sort",required = false)List<Integer> sort,
                       @RequestParam(value = "isValid",required = false)Integer isValid,
                       HttpServletResponse response){

        List<Subcontractor> subcontractors = subcontractorService.listSubcontractor(name,type,professionType,minAmount,
                maxAmount,shareEvaluation,groupEvaluation,companyEvaluation,null,null,isValid);
        HSSFWorkbook hssfWorkbook = null;

        if (exportType != null && sort != null){
            List<ExportConfig> exportConfigs = exportConfigService.findExportConfigs(exportType,sort);
            List<String> titles = Utils.getField(Utils.EXPORT_CONFIG_KEY_TITLE,exportConfigs);
            List<String> fields = Utils.getField(Utils.EXPORT_CONFIG_KEY_FIELD,exportConfigs);
            hssfWorkbook = ExcelUtils.getStandardExcel("分包商资质信息",titles,fields,subcontractors,"分包商资质信息");

        }else {
            String[] titles = {"分包商备案编码","分包商全称","分包商类型","专业类别","纳税人类型","法人","注册资本金（万元）",
                    "资质是否齐全","股份公司综合信誉评价","集团公司综合信誉评价","公司本级综合信誉评价","证件期限","备注"};

            hssfWorkbook = ExcelUtils.getSubcontractorExcel("分包商资质信息","分包商资质信息",titles,subcontractors);

        }
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
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileUrl));
        document.open();
        setContentForPDF(subcontractor,document,writer);
        document.close();

        PdfReader reader = new PdfReader(fileUrl);
        PdfStamper stamper = new PdfStamper(reader,response.getOutputStream());
        Image img = Image.getInstance(pdfMarketAddress);
        img.setAbsolutePosition(360, 720);
        PdfContentByte under = stamper.getUnderContent(1);
        under.addImage(img);
        stamper.close();
        reader.close();

    }

    private void setContentForPDF(Subcontractor subcontractor,Document document,PdfWriter writer) throws Exception{
        if (document == null)
            return;

        BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 10,Font.NORMAL);

        Font titleFont = new Font(bfChinese,10,Font.BOLD);
        Font realTitleFont = new Font(bfChinese,24,Font.BOLD);

        Paragraph title = new Paragraph(subcontractor.getName()+"资质信息卡",realTitleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        Paragraph kb1 = new Paragraph(" ");
        document.add(title);
        document.add(kb1);

        //float[] widths = {0.15f, 0.1f, 0.2f,0.1f, 0.2f,0.1f,0.15f};
        float[] widths = {70, 50, 100,50, 90,50,80};
        PdfPTable table = new PdfPTable(widths);
        table.setLockedWidth(true);
        table.setTotalWidth(550);

        PdfPCell cell;
        cell = Utils.getNewCell(getTitle("分包商全称",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getName(),font),5,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("成立日期",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getSetUpTime()),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("纳税人类型",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getTaxpayerType(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("注册资本金",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getRegisteredCapital().doubleValue()+"",font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("类型",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getType(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("电话",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getPhone(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("电子邮箱",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getEmail(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("注册地址",titleFont),2,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getAddress(),font),3,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("邮编",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getZipCode(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("法定代表人",titleFont),1,2,true,true,60);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("姓 名",titleFont),1,null,true,true,30);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getName(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("职务",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getLegalPersonPosition(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("身份证号码",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getLegalPersonCard(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("联系方式",titleFont),1,null,true,true,30);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getLegalPersonPhone(),font),1,null,true,false);
        table.addCell(cell);
        Paragraph address = new Paragraph("家庭地址",titleFont);
        address.setAlignment(Element.ALIGN_CENTER);
        cell = Utils.getNewCell(address,1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getLegalPersonAddress(),font),3,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("营业执照",titleFont),1,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("统一社会信用代码",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getBusinessLicenseCode(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("有效期限",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getBusinessLicenseValidityPeriod()),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("发证机关",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getBusinessLicenseFrom(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("资质证书",titleFont),1,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("证书编号",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getQualificationCode(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("有效期限",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getQualificationValidityPeriod()),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("发证机关",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getQualificationFrom(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("安全生产许可证",titleFont),1,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("编 号",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getSafetyCode(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("有效期限",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(DateTimeUtil.getYYYYMMDD(subcontractor.getSafetyValidityPeriod()),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("发证机关",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getSafetyFrom(),font),1,null,true,false);
        table.addCell(cell);

        cell = Utils.getNewCell(getTitle("开户银行许可证",titleFont),1,null,true,true);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("开户银行",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getBank(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("银行账号",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getBankAccount(),font),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph("发证机关",titleFont),1,null,true,false);
        table.addCell(cell);
        cell = Utils.getNewCell(new Paragraph(subcontractor.getBankFrom(),font),1,null,true,false);
        table.addCell(cell);

        document.add(table);
    }

    @PostMapping("/deleted/v1.1")
    @AuthRequire
    public ResponseVo logicDeleted(@RequestBody Subcontractor subcontractor,HttpServletRequest request){
        Long id = subcontractor.getId();
        List<SubcontractorResume> subcontractorResumeList = subcontractorResumeService.listResumeBySubcontractorId(id);
        if (subcontractorResumeList != null && subcontractorResumeList.size() > 0)
            return ResponseVo.error(ResponseCode.SUBCONTRACTOR_HAVE_RESUME);

        User user = curUser(request);
        boolean result = subcontractorService.logicDeletedById(id,user.getId(),new Date());
        if (result)
            return ResponseVo.ok();
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    private Paragraph getTitle(String value,Font font){
        Paragraph paragraph = new Paragraph(value,font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

}
