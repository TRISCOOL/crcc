package com.crcc.common.utils;

import com.crcc.common.model.MeteringAccount;
import com.crcc.common.model.ProjectInfo;
import com.crcc.common.model.Subcontractor;
import com.crcc.common.model.SubcontractorResume;
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
            contentRow.createCell(8).setCellValue(info.getTotalPrice().setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
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
}
