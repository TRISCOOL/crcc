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

        PdfContentByte canvas = writer.getDirectContent();

        Paragraph title = new Paragraph(subcontractor.getName()+"资质信息卡",font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph name = new Paragraph("分包商全称："+subcontractor.getName(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,name,20, 750, 0);


        Paragraph setUpTime = new Paragraph("成立日期："+DateTimeUtil.getYYYYMMDD(subcontractor.getSetUpTime()),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,setUpTime,20, 730, 0);

        Paragraph nstype = new Paragraph("纳税人类型："+subcontractor.getTaxpayerType(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,nstype,220, 730, 0);

        Paragraph zc = new Paragraph("注册资本金："+subcontractor.getRegisteredCapital()+" 万元",font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,zc,420,730,0);

        Paragraph type = new Paragraph("分包商类型："+subcontractor.getType(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,type,20,710,0);

        Paragraph phone = new Paragraph("电  话："+subcontractor.getPhone(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,phone,220,710,0);

        Paragraph email = new Paragraph("电子邮箱："+subcontractor.getEmail(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,email,420,710,0);

        Paragraph address = new Paragraph("注册地址："+subcontractor.getAddress(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,address,20,690,0);

        Paragraph professionType = new Paragraph("专业类型："+subcontractor.getProfessionType(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,professionType,20,670,0);


        Paragraph title1 = new Paragraph("法定代表人",font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,title1,20,640,0);
        Paragraph fdName = new Paragraph("姓名："+subcontractor.getLegalPersonName(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,fdName,20,620,0);
        Paragraph fdPosition = new Paragraph("职务："+subcontractor.getLegalPersonPosition(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,fdPosition,220,620,0);
        Paragraph fdCard = new Paragraph("身份证号码："+subcontractor.getLegalPersonCard(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,fdCard,420,620,0);
        Paragraph fdPhone = new Paragraph("联系方式："+subcontractor.getLegalPersonPhone(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,fdPhone,20,600,0);
        Paragraph fdAddress = new Paragraph("家庭住址："+subcontractor.getLegalPersonAddress(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,fdAddress,220,600,0);

        Paragraph title2 = new Paragraph("营业执照",font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,title2,20,570,0);
        Paragraph tyxy = new Paragraph("统一社会信用代码："+ subcontractor.getBusinessLicenseCode(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,tyxy,20,550,0);
        Paragraph tyPeriod = new Paragraph("有效期限："+DateTimeUtil.getYYYYMMDD(subcontractor.getBusinessLicenseValidityPeriod()),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,tyPeriod,220,550,0);
        Paragraph tyfrom = new Paragraph("发证机关："+subcontractor.getBusinessLicenseFrom(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,tyfrom,420,550,0);

        Paragraph qualification = new Paragraph("资质证书",font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,qualification,20,520,0);
        Paragraph qualificationCode = new Paragraph("证书编号："+subcontractor.getQualificationCode(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,qualificationCode,20,500,0);
        Paragraph qualificationEffect = new Paragraph("有效期限："+DateTimeUtil.getYYYYMMDD(subcontractor.getQualificationValidityPeriod()),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,qualificationEffect,220,500,0);
        Paragraph qualificationFrom = new Paragraph("发证机关："+subcontractor.getQualificationFrom(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,qualificationFrom,420,500,0);

        Paragraph safety = new Paragraph("安全生产许可证",font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,safety,20,470,0);
        Paragraph safetyCode = new Paragraph("编码："+subcontractor.getSafetyCode(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,safetyCode,20,450,0);
        Paragraph safetyEffect = new Paragraph("有效期限："+DateTimeUtil.getYYYYMMDD(subcontractor.getSafetyValidityPeriod()),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,safetyEffect,220,450,0);
        Paragraph safetyFrom = new Paragraph("发证机关："+subcontractor.getSafetyFrom(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,safetyFrom,420,450,0);

        Paragraph bank =  new Paragraph("开户银行许可证",font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,bank,20,420,0);
        Paragraph bankName = new Paragraph("开户银行："+subcontractor.getBank(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,bankName,20,400,0);
        Paragraph bankAccount = new Paragraph("银行账号："+subcontractor.getBank(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,bankAccount,220,400,0);
        Paragraph bankFrom = new Paragraph("发证机关："+subcontractor.getBankFrom(),font);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,bankFrom,420,400,0);
    }

}
