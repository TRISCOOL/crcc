package com.crcc.common.utils;

import com.crcc.common.model.*;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static HSSFWorkbook getHSSFWorkbookForUser(String sheetName,String[] title,List<User> users){
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

        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        num = num+1;
        for (User user:users){
            HSSFRow contextRow = sheet.createRow(num);
            contextRow.createCell(0).setCellValue(user.getCode());
            contextRow.createCell(1).setCellValue(user.getType() == 0?"公司":"项目部");
            contextRow.createCell(2).setCellValue(user.getAccount());
            contextRow.createCell(3).setCellValue(user.getProjectName());
            contextRow.createCell(4).setCellValue(user.getPassword());
            contextRow.createCell(5).setCellValue(user.getRoleName());
            contextRow.createCell(6).setCellValue(user.getDisable()==0?"启用":"禁用");
            contextRow.createCell(7).setCellValue(user.getCreateUserStr());
            contextRow.createCell(8).setCellValue(user.getCreateTime() == null?"":DateTimeUtil.getYYYYMMDD(user.getCreateTime()));
            contextRow.createCell(9).setCellValue(user.getUpdateUserStr());
            contextRow.createCell(10).setCellValue(user.getUpdateTime() == null?"":DateTimeUtil.getYYYYMMDD(user.getUpdateTime()));
            num++;
        }
        return wb;
    }

    public static HSSFWorkbook getHSSFWorkbookForProject(String sheetName,String[] title,List<Project> projectList){
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

        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        num = num+1;
        for (Project project:projectList){
            HSSFRow contextRow = sheet.createRow(num);
            contextRow.createCell(0).setCellValue(project.getCode());
            contextRow.createCell(1).setCellValue(project.getName());
            contextRow.createCell(2).setCellValue(project.getProjectType());
            contextRow.createCell(3).setCellValue(project.getStatusStr());
            contextRow.createCell(4).setCellValue(project.getCreateUserStr());
            contextRow.createCell(5).setCellValue(project.getCreateTime() == null?"":DateTimeUtil.getYYYYMMDD(project.getCreateTime()));
            num++;
        }
        return wb;
    }

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
            contentRow.createCell(9).setCellValue(getProjectNameFirst(info.getManager()));
            contentRow.createCell(10).setCellValue(getProjectNameFirst(info.getSecretary()));
            contentRow.createCell(11).setCellValue(getProjectNameFirst(info.getEngineer()));
            num++;

        }
        return wb;
    }

    public static String getProjectNameFirst(String value){
        List<ProjectInfoPeople> projectInfoPeople = Utils.fromJson(value,new TypeToken<List<ProjectInfoPeople>>(){});
        if (projectInfoPeople == null)
            return "";

        return projectInfoPeople.get(projectInfoPeople.size()-1).getName();
    }

    public static HSSFWorkbook getProjectEvaluationExcel(String titleValue,String sheetName,String[] title,List<ProjectEvaluation> projectEvaluations){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

        //声明列对象
        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            if (i == 5){
                cell.setCellValue("合同额");
            }else if (i == 7) {
                cell.setCellValue("合同");
            }else if (i == 9){
                cell.setCellValue("合同工期(");
            }else if (i == 12){
                cell.setCellValue("经管部评估");
            }else if (i == 17){
                cell.setCellValue("会审情况");
            }else if (i == 21){
                cell.setCellValue("责任状签订");
            }else {
                cell.setCellValue("");
            }
            cell.setCellStyle(style);
        }
        CellRangeAddress merge1 = new CellRangeAddress(1,1,5,6);
        CellRangeAddress merge2 = new CellRangeAddress(1,1,7,8);
        CellRangeAddress merge3 = new CellRangeAddress(1,1,12,16);
        CellRangeAddress merge4 = new CellRangeAddress(1,1,17,20);
        CellRangeAddress merge5 = new CellRangeAddress(1,1,21,25);
        sheet.addMergedRegion(merge1);
        sheet.addMergedRegion(merge2);
        sheet.addMergedRegion(merge3);
        sheet.addMergedRegion(merge4);
        sheet.addMergedRegion(merge5);

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
        for(ProjectEvaluation evaluation : projectEvaluations){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(evaluation.getId());
            contentRow.createCell(1).setCellValue(evaluation.getProjectName());
            contentRow.createCell(2).setCellValue(evaluation.getEngineeringType());
            contentRow.createCell(3).setCellValue(evaluation.getEvaluationStatus());
            contentRow.createCell(4).setCellValue(evaluation.getProjectStatus());
            //中标
            contentRow.createCell(5).setCellValue(isNull(evaluation.getWinningBid()));
            //有效收入
            contentRow.createCell(6).setCellValue(isNull(evaluation.getEffectiveIncome()));
            //是否签订
            contentRow.createCell(7).setCellValue(evaluation.getIsSign());
            //合同开工日期
            contentRow.createCell(8).setCellValue(DateTimeUtil.getYYYYMMDD(evaluation.getContractStartTime()));
            //合同竣工日期
            contentRow.createCell(9).setCellValue(DateTimeUtil.getYYYYMMDD(evaluation.getContractEndTime()));
            //工期
            contentRow.createCell(10).setCellValue(evaluation.getDuration());
            //评估时间
            contentRow.createCell(11).setCellValue(DateTimeUtil.getYYYYMMDD(evaluation.getEvaluationTime()));
            //评估效益点
            contentRow.createCell(12).setCellValue(isNull(evaluation.getEvaluationBenefit())*100+"%");
            //含分包差及经营费
            contentRow.createCell(13).setCellValue(isNull(evaluation.getEvaluationCost()));
            //评估编号
            contentRow.createCell(14).setCellValue(evaluation.getEvaluationCode());
            //附件
            contentRow.createCell(15).setCellValue(evaluation.getEvaluationAnnex());
            //会审效益点
            contentRow.createCell(16).setCellValue(isNull(evaluation.getJointHearingBenefit())*100+"%");
            //会审含分包差及经营费
            contentRow.createCell(17).setCellValue(isNull(evaluation.getJointHearingCost()));
            contentRow.createCell(18).setCellValue(DateTimeUtil.getYYYYMMDD(evaluation.getJointHearingTime()));
            contentRow.createCell(19).setCellValue(evaluation.getJointHearingAnnex());
            contentRow.createCell(20).setCellValue(isNull(evaluation.getJointHearingCost()));

            contentRow.createCell(21).setCellValue(isNull(evaluation.getResponsibilityBenefiy())*100+"%");
            contentRow.createCell(22).setCellValue(DateTimeUtil.getYYYYMMDD(evaluation.getResponsibilityTime()));
            contentRow.createCell(23).setCellValue(evaluation.getResponsibilityPeople());
            contentRow.createCell(24).setCellValue(evaluation.getResponsibilitySecretary());
            contentRow.createCell(25).setCellValue(evaluation.getResponsibilityAnnex());
            num++;

        }
        return wb;

    }

    public static HSSFWorkbook getExcelForUpAccount(String titleValue,String sheetName,String[] title, List<MeteringAccount> meteringAccounts){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

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
        CellRangeAddress addressPlane = new CellRangeAddress(2,2,5,7);
        CellRangeAddress addressRealTime = new CellRangeAddress(2,2,8,9);
        CellRangeAddress addressContract = new CellRangeAddress(2,2,10,12);
        CellRangeAddress address = new CellRangeAddress(2,2,13,14);
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
        Double sumAlreadyAmount = 0d;
        Double sumRealTaxAmount = 0d;
        Double sumTaxAmount = 0d;
        Double sumEndAmount = 0d;
        Double sumExtraAmount = 0d;

        Double sumPrepaymentAmount = 0d;
        Double sumAmountNotTax = 0d;
        Double sumRealAmount = 0d;
        Double sumUnpaidAmount = 0d;
        //创建内容
        num = num+1;
        for(MeteringAccount meteringAccount : meteringAccounts){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(meteringAccount.getId());
            contentRow.createCell(1).setCellValue(meteringAccount.getProjectName());
            contentRow.createCell(2).setCellValue(meteringAccount.getMeteringNum());
            contentRow.createCell(3).setCellValue(DateTimeUtil.getYYYYMMDD(meteringAccount.getMeteringTime()));
            contentRow.createCell(4).setCellValue(meteringAccount.getPrepaymentAmount().setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
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

            sumAlreadyAmount = addAmount(sumAlreadyAmount,meteringAccount.getAlreadyPaidAmount());
            sumRealTaxAmount = addAmount(sumRealTaxAmount,meteringAccount.getRealAmountTax());
            sumTaxAmount = addAmount(sumTaxAmount,meteringAccount.getValuationAmountTax());
            sumEndAmount = addAmount(sumEndAmount,meteringAccount.getNotCalculatedAmount());
            sumExtraAmount = addAmount(sumExtraAmount,meteringAccount.getExtraAmount());
            sumPrepaymentAmount = addAmount(sumPrepaymentAmount,meteringAccount.getPrepaymentAmount());
            sumAmountNotTax = addAmount(sumAmountNotTax,meteringAccount.getValuationAmountNotTax());
            sumRealAmount = addAmount(sumRealAmount,meteringAccount.getRealAmount());
            sumUnpaidAmount = addAmount(sumUnpaidAmount,meteringAccount.getUnpaidAmount());

            num++;

        }
        num = num+1;
        HSSFRow contentRow = sheet.createRow(num);
        contentRow.createCell(0).setCellValue("合计");
        contentRow.createCell(4).setCellValue(sumPrepaymentAmount); //预付款
        contentRow.createCell(5).setCellValue(sumTaxAmount);
        contentRow.createCell(7).setCellValue(sumAmountNotTax);
        contentRow.createCell(8).setCellValue(sumRealTaxAmount);
        contentRow.createCell(9).setCellValue(sumRealAmount);
        contentRow.createCell(10).setCellValue(sumAlreadyAmount);
        contentRow.createCell(11).setCellValue(sumUnpaidAmount);
        contentRow.createCell(12).setCellValue(
                computerDivide(new BigDecimal(sumRealTaxAmount),new BigDecimal(sumAlreadyAmount))*100+"%"
        );
        contentRow.createCell(13).setCellValue(sumExtraAmount);
        contentRow.createCell(14).setCellValue(sumEndAmount);
        contentRow.createCell(15).setCellValue(
                computerDivide(new BigDecimal(sumTaxAmount),new BigDecimal(sumTaxAmount+sumEndAmount+sumExtraAmount))*100+"%");
        return wb;
    }

    private static Double computerDivide(BigDecimal bcs,BigDecimal cs){
        if (bcs.doubleValue() == 0d)
            return 0d;

        return bcs.divide(cs,2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
    }

    private static Double addAmount(Double sum,BigDecimal value){
        Double mid = 0d;
        if (value != null){
            mid = value.doubleValue();
        }
        sum = sum + mid;
        return sum;
    }

    public static HSSFWorkbook getSubcontractorExcel(String titleValue,String sheetName,String[] title, List<Subcontractor> subcontractors){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

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
            contentRow.createCell(12).setCellValue(subcontractor.getRemark());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getResumeExcel(String titleValue,String sheetName,String[] title, List<SubcontractorResume> subcontractorResumes){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

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
            contentRow.createCell(9).setCellValue(subcontractorResume.getContractAmount() != null?subcontractorResume.getContractAmount():0d);
            contentRow.createCell(10).setCellValue(subcontractorResume.getSettlementAmount() != null?subcontractorResume.getSettlementAmount():0d);
            contentRow.createCell(11).setCellValue(subcontractorResume.getProjectEvaluation());
            contentRow.createCell(12).setCellValue(subcontractorResume.getProjectDescription());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getLaborAccountExcel(String titleValue,String sheetName,String[] title, List<LaborAccount> laborAccounts){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

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
        CellRangeAddress address1 = new CellRangeAddress(1,1,0,13);
        CellRangeAddress address2 = new CellRangeAddress(1,1,14,18);
        sheet.addMergedRegion(address1);
        sheet.addMergedRegion(address2);

        num = num + 1;
        HSSFRow rowSecondTilte = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = rowSecondTilte.createCell(i);
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
        CellRangeAddress address3 = new CellRangeAddress(2,2,8,9);
        CellRangeAddress address4 = new CellRangeAddress(2,2,10,11);
        CellRangeAddress address5 = new CellRangeAddress(2,2,15,16);
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
            contentRow.createCell(12).setCellValue(laborAccount.getSettlementAmount() != null?laborAccount.getSettlementAmount()
                    .setScale(2,BigDecimal.ROUND_DOWN).doubleValue():0d);
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

    public static HSSFWorkbook getHSSFWorkbookForInspectionAccount(String titleValue,String sheetName, String[] title,
                                                                   List<InspectionAccount> inspectionAccounts){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

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
        CellRangeAddress addressPlane = new CellRangeAddress(1,1,7,13);
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
        Double sumPrice = 0d;
        Double sumEndPrice = 0d;
        for(InspectionAccount inspectionAccount : inspectionAccounts){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(inspectionAccount.getProjectName());
            contentRow.createCell(1).setCellValue(inspectionAccount.getSubcontractorName());
            contentRow.createCell(2).setCellValue(inspectionAccount.getTeamName());
            contentRow.createCell(3).setCellValue(isNull(inspectionAccount.getContractPrice()));
            contentRow.createCell(4).setCellValue(inspectionAccount.getValuationPeriod());
            contentRow.createCell(5).setCellValue(DateTimeUtil.getYYYYMMDD(inspectionAccount.getValuationTime()));
            contentRow.createCell(6).setCellValue(getValuationType(inspectionAccount.getValuationType()));
            contentRow.createCell(7).setCellValue(isNull(inspectionAccount.getValuationPrice()));
            contentRow.createCell(8).setCellValue(isNull(inspectionAccount.getValuationPriceReduce()));
            contentRow.createCell(9).setCellValue(isNull(inspectionAccount.getWarranty()));
            contentRow.createCell(10).setCellValue(isNull(inspectionAccount.getPerformanceBond()));
            contentRow.createCell(11).setCellValue(isNull(inspectionAccount.getCompensation()));
            contentRow.createCell(12).setCellValue(isNull(inspectionAccount.getShouldAmount()));
            contentRow.createCell(13).setCellValue(isNull(inspectionAccount.getEndedPrice()));
            contentRow.createCell(14).setCellValue(isNull(inspectionAccount.getUnderRate())+"%");
            contentRow.createCell(15).setCellValue(inspectionAccount.getValuationPerson());
            contentRow.createCell(16).setCellValue(inspectionAccount.getRemark());
            sumPrice = addAmount(sumPrice,inspectionAccount.getValuationPrice());
            sumEndPrice = addAmount(sumEndPrice,inspectionAccount.getEndedPrice());
            num++;

        }
        HSSFRow contentRow = sheet.createRow(num);
        contentRow.createCell(0).setCellValue("合计");
        contentRow.createCell(14).setCellValue(
                computerDivide(new BigDecimal(sumPrice),new BigDecimal(sumPrice+sumEndPrice))+"%"
        );
        return wb;
    }

    private static Double isNull(BigDecimal value){
        if (value == null){
            return -1d;
        }

        return value.setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static HSSFWorkbook getPersonnelExcel(String titleValue,String sheetName,String[] title, List<Personnel> personnels){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

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
            contentRow.createCell(0).setCellValue(personnel.getCode());
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
            contentRow.createCell(14).setCellValue(personnel.getCreateTime() == null?"":DateTimeUtil.getYYYYMMDD(personnel.getCreateTime()));
            contentRow.createCell(15).setCellValue(personnel.getRemark());
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
    public static String getTeamStatus(Integer status){
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

    public static HSSFWorkbook getStandardExcel(String title,List<String> titles, List<String> fields, List values, String sheetName){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<titles.size();i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(style);
            if (i == 0){
                titleCell.setCellValue(title);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,titles.size());
        sheet.addMergedRegion(merge);



/*        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();
        style2.setDataFormat(format.getFormat("@"));*/

        HSSFCell cell = null;
        for(int i=0;i< titles.size();i++){
            cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
            cell.setCellStyle(style);
        }

        num = num +1;
        for (Object value: values){
            HSSFRow contextRow = sheet.createRow(num);
            Class<? extends Object> targetObject = value.getClass();
            int cellNum = 0;
            for (String field : fields){
                try {
                    Field targetField = targetObject.getDeclaredField(field);
                    targetField.setAccessible(true);
                    if (targetField == null)
                        continue;
                    Class targetType = targetField.getType();
                    String result = null;
                    try {
                        if (targetType == BigDecimal.class){
                            BigDecimal bigDecimal = (BigDecimal)targetField.get(value);
                            if (bigDecimal != null){
                                bigDecimal.setScale(2,BigDecimal.ROUND_DOWN);
                                result = transformationFromDoubleToString(bigDecimal.doubleValue());//解除科学记数法在excel中
                            }
                        }else if (targetType == Double.class){
                            Double d = targetField.getDouble(value);
                            if (d != null){
                                result = transformationFromDoubleToString(new BigDecimal(d).
                                        setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
                            }
                        }else if (targetType == Date.class){
                            Date time = (Date) targetField.get(value);
                            result = DateTimeUtil.getYYYYMMDD(time);
                        }else {
                            result = targetField.get(value) == null?"":targetField.get(value).toString();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    HSSFCell resultCell = contextRow.createCell(cellNum);
                    /*resultCell.setCellStyle(style2);*/
                    resultCell.setCellValue(result);
                    targetField.setAccessible(false);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                cellNum ++ ;
            }
            num ++;
        }
        return wb;
    }

    //消除科学记数法
    private static String transformationFromDoubleToString(Double value){
        NumberFormat nf = NumberFormat.getInstance();
        //保留小数位4位
        nf.setMaximumFractionDigits(2);
        //是否保留千分位
        nf.setGroupingUsed(false);

        return nf.format(value);
    }
}
