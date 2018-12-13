package com.crcc.common.utils;

import com.crcc.common.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;
import java.util.List;

public class ExcelUtils {
    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbookForProjectInfo(String sheetName,String[] title, List<ProjectInfo> projectInfos){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 0;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            if (i == 3){
                cell.setCellValue("计划工期");
            }else if (i == 5) {
                cell.setCellValue("实际工期");
            }else if (i == 7){
                cell.setCellValue("合同总价");
            }else {
                cell.setCellValue("");
            }
            cell.setCellStyle(style);
        }
        CellRangeAddress addressPlane = new CellRangeAddress(0,0,3,4);
        CellRangeAddress addressRealTime = new CellRangeAddress(0,0,5,6);
        CellRangeAddress addressContract = new CellRangeAddress(0,0,7,8);
        sheet.addMergedRegion(addressPlane);
        sheet.addMergedRegion(addressRealTime);
        sheet.addMergedRegion(addressContract);




        //创建标题
        num = num+1;
        HSSFRow realRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = realRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        num = num+1;
        for(ProjectInfo info : projectInfos){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(info.getCode());
            contentRow.createCell(1).setCellValue(info.getProjectName());
            contentRow.createCell(2).setCellValue(info.getStatusStr());
            contentRow.createCell(3).setCellValue(DateTimeUtil.getYYYYMMDD(info.getContractStartTime()));
            contentRow.createCell(4).setCellValue(DateTimeUtil.getYYYYMMDD(info.getContractEndTime()));
            contentRow.createCell(5).setCellValue(DateTimeUtil.getYYYYMMDD(info.getRealContractStartTime()));
            contentRow.createCell(6).setCellValue(DateTimeUtil.getYYYYMMDD(info.getRealContractEndTime()));
            contentRow.createCell(7).setCellValue(info.getTotalPrice().setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(8).setCellValue(info.getTemporarilyPrice().setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(9).setCellValue(info.getProjectManager());
            contentRow.createCell(10).setCellValue(info.getProjectSecretary());
            contentRow.createCell(11).setCellValue(info.getChiefEngineer());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getExcelForUpAccount(String sheetName,String[] title, List<MeteringAccount> meteringAccounts){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 0;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            if (i == 5){
                cell.setCellValue("计价金额(元)");
            }else if (i == 8) {
                cell.setCellValue("实际支付金额(元)");
            }else if (i == 10){
                cell.setCellValue("资金拨付情况(元)");
            }else if (i == 13){
                cell.setCellValue("其他计价(元)");
            }else {
                cell.setCellValue("");
            }
            cell.setCellStyle(style);
        }
        CellRangeAddress addressPlane = new CellRangeAddress(0,0,3,7);
        CellRangeAddress addressRealTime = new CellRangeAddress(0,0,8,9);
        CellRangeAddress addressContract = new CellRangeAddress(0,0,10,12);
        CellRangeAddress address = new CellRangeAddress(0,0,13,14);
        sheet.addMergedRegion(addressPlane);
        sheet.addMergedRegion(addressRealTime);
        sheet.addMergedRegion(addressContract);
        sheet.addMergedRegion(address);

        //创建标题
        num = num+1;
        HSSFRow realRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = realRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        num = num+1;
        for(MeteringAccount meteringAccount : meteringAccounts){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(meteringAccount.getId());
            contentRow.createCell(1).setCellValue(meteringAccount.getProjectName());
            contentRow.createCell(2).setCellValue(meteringAccount.getMeteringNum());
            contentRow.createCell(3).setCellValue(DateTimeUtil.getYYYYMMDD(meteringAccount.getMeteringTime()));
            contentRow.createCell(4).setCellValue("预付金额");
            //计价金额含税
            contentRow.createCell(5).setCellValue(meteringAccount.getValuationAmountTax()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //计价金额税率
            contentRow.createCell(6).setCellValue(meteringAccount.getTax()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //计价金额不含税
            contentRow.createCell(7).setCellValue(meteringAccount.getValuationAmountNotTax()
                    .setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
            //实际支付金额含税
            contentRow.createCell(8).setCellValue(meteringAccount.getRealAmountTax()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //实际支付金额不含税
            contentRow.createCell(9).setCellValue(meteringAccount.getRealAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //已经支付金额
            contentRow.createCell(10).setCellValue(meteringAccount.getAlreadyPaidAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //未支付金额
            contentRow.createCell(11).setCellValue(meteringAccount.getUnpaidAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //拨付率
            contentRow.createCell(12).setCellValue(meteringAccount.getPayProportion()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue()*100+"%");
            //超计价
            contentRow.createCell(13).setCellValue(meteringAccount.getExtraAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //已完未计
            contentRow.createCell(14).setCellValue(meteringAccount.getNotCalculatedAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            //产值计价率
            contentRow.createCell(15).setCellValue(meteringAccount.getProductionValue()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue()*100+"%");
            contentRow.createCell(16).setCellValue(meteringAccount.getRemark());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getSubcontractorExcel(String sheetName,String[] title, List<Subcontractor> subcontractors){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 0;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;

        HSSFRow realRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = realRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        num = num+1;
        for(Subcontractor subcontractor : subcontractors){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(subcontractor.getCode());
            contentRow.createCell(1).setCellValue(subcontractor.getName());
            contentRow.createCell(2).setCellValue(subcontractor.getTypeStr());
            contentRow.createCell(3).setCellValue(subcontractor.getProfessionStr());
            contentRow.createCell(4).setCellValue(subcontractor.getTaxpayerTypeStr());
            contentRow.createCell(5).setCellValue(subcontractor.getLegalPersonName());
            contentRow.createCell(6).setCellValue(subcontractor.getRegisteredCapital().
                    setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(7).setCellValue(subcontractor.getQualification());
            contentRow.createCell(8).setCellValue(subcontractor.getShareEvaluation());
            contentRow.createCell(9).setCellValue(subcontractor.getGroupEvaluation());
            contentRow.createCell(10).setCellValue(subcontractor.getCompanyEvaluation());
            contentRow.createCell(11).setCellValue(DateTimeUtil
                    .getYYYYMMDD(subcontractor.getQualificationValidityPeriod()));
            contentRow.createCell(12).setCellValue(subcontractor.getShareRemark());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getResumeExcel(String sheetName,String[] title, List<SubcontractorResume> subcontractorResumes){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 0;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;

        HSSFRow realRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = realRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        num = num+1;
        for(SubcontractorResume subcontractorResume : subcontractorResumes){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(subcontractorResume.getSubcontractorCode());
            contentRow.createCell(1).setCellValue(subcontractorResume.getSubcontractorName());
            contentRow.createCell(2).setCellValue(subcontractorResume.getTeamName());
            contentRow.createCell(3).setCellValue(subcontractorResume.getConstructionScale());
            contentRow.createCell(4).setCellValue(DateTimeUtil.getYYYYMMDD(subcontractorResume.getStartTime()));
            contentRow.createCell(5).setCellValue(DateTimeUtil.getYYYYMMDD(subcontractorResume.getEndTime()));
            contentRow.createCell(6).setCellValue(subcontractorResume.getProjectName());
            contentRow.createCell(7).setCellValue(subcontractorResume.getContractPerson());
            contentRow.createCell(8).setCellValue(subcontractorResume.getPhone());
            contentRow.createCell(9).setCellValue(subcontractorResume.getContractAmount());
            contentRow.createCell(10).setCellValue(subcontractorResume.getSettlementAmount());
            contentRow.createCell(11).setCellValue(subcontractorResume.getProjectEvaluation());
            contentRow.createCell(12).setCellValue(subcontractorResume.getProjectDescription());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getLaborAccountExcel(String sheetName,String[] title, List<LaborAccount> laborAccounts){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 0;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            if (i == 0){
                cell.setCellValue("劳务队伍统计（项目部填写）");
            }else if (i == 14) {
                cell.setCellValue("备案情况（公司填写）");
            }else {
                cell.setCellValue("");
            }
            cell.setCellStyle(style);
        }
        CellRangeAddress address1 = new CellRangeAddress(0,0,0,13);
        CellRangeAddress address2 = new CellRangeAddress(0,0,14,18);
        sheet.addMergedRegion(address1);
        sheet.addMergedRegion(address2);

        num = num + 1;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            if (i == 8){
                cell.setCellValue("履约保证金");
            }else if (i == 10) {
                cell.setCellValue("负责人");
            }else if (i == 14){
                cell.setCellValue("队伍选定");
            }else if (i == 15){
                cell.setCellValue("合同审批");
            }else if (i == 17){
                cell.setCellValue("结算审批");
            }else {
                cell.setCellValue("");
            }
            cell.setCellStyle(style);
        }
        CellRangeAddress address3 = new CellRangeAddress(1,1,8,9);
        CellRangeAddress address4 = new CellRangeAddress(1,1,10,11);
        CellRangeAddress address5 = new CellRangeAddress(1,1,15,16);
        sheet.addMergedRegion(address3);
        sheet.addMergedRegion(address4);
        sheet.addMergedRegion(address5);


        HSSFRow realRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = realRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        num = num+1;
        for(LaborAccount laborAccount : laborAccounts){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(laborAccount.getProjectName());
            contentRow.createCell(1).setCellValue(laborAccount.getContractCode());
            contentRow.createCell(2).setCellValue(laborAccount.getSubcontractorName());
            contentRow.createCell(3).setCellValue(laborAccount.getTeamName());
            contentRow.createCell(4).setCellValue(getTeamStatus(laborAccount.getStatus()));
            contentRow.createCell(5).setCellValue(DateTimeUtil.getYYYYMMDD(laborAccount.getContractTime()));
            contentRow.createCell(6).setCellValue(laborAccount.getEstimatedContractAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(7).setCellValue(laborAccount.getConstructionScope());
            contentRow.createCell(8).setCellValue(laborAccount.getShouldAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(9).setCellValue(laborAccount.getRealAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(10).setCellValue(laborAccount.getContractPerson());
            contentRow.createCell(11).setCellValue(laborAccount.getPhone());
            contentRow.createCell(12).setCellValue(laborAccount.getSettlementAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(13).setCellValue(laborAccount.getRemark());
            contentRow.createCell(14).setCellValue(DateTimeUtil.getYYYYMMDD(laborAccount.getTeamTime()));
            contentRow.createCell(15).setCellValue(DateTimeUtil.getYYYYMMDD(laborAccount.getApprovalTime()));
            contentRow.createCell(16).setCellValue(getApproval(laborAccount.getApprovalFiling()));
            contentRow.createCell(17).setCellValue(DateTimeUtil.getYYYYMMDD(laborAccount.getSettlementTime()));
            contentRow.createCell(18).setCellValue(laborAccount.getRemark());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getHSSFWorkbookForInspectionAccount(String sheetName, String[] title,
                                                                   List<InspectionAccount> inspectionAccounts){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 0;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            if (i == 7){
                cell.setCellValue("计价金额（含税）");
            }else {
                cell.setCellValue("");
            }
            cell.setCellStyle(style);
        }
        CellRangeAddress addressPlane = new CellRangeAddress(0,0,7,13);
        sheet.addMergedRegion(addressPlane);

        //创建标题
        num = num+1;
        HSSFRow realRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = realRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        num = num+1;
        for(InspectionAccount inspectionAccount : inspectionAccounts){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(inspectionAccount.getProjectName());
            contentRow.createCell(1).setCellValue(inspectionAccount.getSubcontractorName());
            contentRow.createCell(2).setCellValue(inspectionAccount.getTeamName());
            contentRow.createCell(3).setCellValue(inspectionAccount.getContractPrice()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(4).setCellValue(inspectionAccount.getValuationPeriod());
            contentRow.createCell(5).setCellValue(DateTimeUtil.getYYYYMMDD(inspectionAccount.getValuationTime()));
            contentRow.createCell(6).setCellValue(getValuationType(inspectionAccount.getValuationType()));
            contentRow.createCell(7).setCellValue(inspectionAccount.getValuationPrice()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(8).setCellValue(inspectionAccount.getValuationPriceReduce()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(9).setCellValue(inspectionAccount.getWarranty()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(10).setCellValue(inspectionAccount.getPerformanceBond()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(11).setCellValue(inspectionAccount.getCompensation()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(12).setCellValue(inspectionAccount.getShouldAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
            contentRow.createCell(13).setCellValue("已完未计金额");
            contentRow.createCell(14).setCellValue("对下计价率");
            contentRow.createCell(15).setCellValue(inspectionAccount.getValuationPerson());
            contentRow.createCell(16).setCellValue(inspectionAccount.getRemark());


            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getPersonnelExcel(String sheetName,String[] title, List<Personnel> personnels){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 0;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;

        HSSFRow realRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = realRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        num = num+1;
        for(Personnel personnel : personnels){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue("编码待定");
            contentRow.createCell(1).setCellValue(personnel.getName());
            contentRow.createCell(2).setCellValue(personnel.getSex());
            contentRow.createCell(3).setCellValue(personnel.getStatus());
            contentRow.createCell(4).setCellValue(personnel.getProjectName());
            contentRow.createCell(5).setCellValue(personnel.getPosition());
            contentRow.createCell(6).setCellValue(personnel.getJobTitle());
            contentRow.createCell(7).setCellValue(personnel.getWorkTime());
            contentRow.createCell(8).setCellValue(personnel.getEducation());
            contentRow.createCell(9).setCellValue(personnel.getPhone());
            contentRow.createCell(10).setCellValue(personnel.getQqNumber());
            contentRow.createCell(11).setCellValue(personnel.getIdCard());
            contentRow.createCell(12).setCellValue(personnel.getCertificate());
            contentRow.createCell(13).setCellValue(personnel.getJiguan());
            contentRow.createCell(14).setCellValue(personnel.getCreateTime());
            num++;

        }
        return wb;
    }

    /**
     * 计价类型（0-过程计价，1-中期结算，2-末次计算）
     * @param type
     * @return
     */
    private static String getValuationType(Integer type){
        String result = "";
        switch (type){
            case 0:
                result = "过程计价";
                break;
            case 1:
                result = "中期结算";
                break;
            case 2:
                result = "末次计算";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 0-未备案，1-备案
     * @param approval
     * @return
     */
    private static String getApproval(Integer approval){
        if (approval == null)
            return "";

        if (approval == 0){
            return "未备案";
        }else if (approval == 1){
            return "备案";
        }else {
            return "";
        }
    }

    //队伍状态(0-正在施工，1-完工待结算，2-已结算)
    private static String getTeamStatus(Integer status){
        String result = "未定义的状态";
        switch (status){
            case 0:
                result = "正在施工";
                break;
            case 1:
                result = "完工待结算";
                break;
            case 2:
                result = "已结算";
                break;
            default:
                break;
        }
        return result;
    }
}
