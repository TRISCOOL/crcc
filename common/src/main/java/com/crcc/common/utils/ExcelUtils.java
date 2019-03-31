package com.crcc.common.utils;

import com.crcc.common.model.*;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
            contentRow.createCell(5).setCellValue(meteringAccount.getValuationAmountTax().doubleValue());
            //计价金额税率
            contentRow.createCell(6).setCellValue(meteringAccount.getTax().doubleValue());
            //计价金额不含税
            contentRow.createCell(7).setCellValue(meteringAccount.getValuationAmountNotTax().doubleValue());
            //实际支付金额含税
            contentRow.createCell(8).setCellValue(meteringAccount.getRealAmountTax().doubleValue());
            //实际支付金额不含税
            contentRow.createCell(9).setCellValue(meteringAccount.getRealAmount().doubleValue());
            //已经支付金额
            contentRow.createCell(10).setCellValue(meteringAccount.getAlreadyPaidAmount().doubleValue());
            //未支付金额
            contentRow.createCell(11).setCellValue(meteringAccount.getUnpaidAmount().doubleValue());
            //拨付率
            contentRow.createCell(12).setCellValue(meteringAccount.getPayProportion().doubleValue()*100+"%");
            //超计价
            contentRow.createCell(13).setCellValue(meteringAccount.getExtraAmount().doubleValue());
            //已完未计
            contentRow.createCell(14).setCellValue(meteringAccount.getNotCalculatedAmount().doubleValue());
            //产值计价率
            contentRow.createCell(15).setCellValue(meteringAccount.getProductionValue().doubleValue()*100+"%");
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
                computerDivide(new BigDecimal(sumRealTaxAmount),new BigDecimal(sumAlreadyAmount),4)*100+"%"
        );
        contentRow.createCell(13).setCellValue(sumExtraAmount);
        contentRow.createCell(14).setCellValue(sumEndAmount);
        contentRow.createCell(15).setCellValue(
                computerDivide(new BigDecimal(sumTaxAmount),new BigDecimal(sumTaxAmount+sumEndAmount+sumExtraAmount),4)*100+"%");
        return wb;
    }

    public static Double computerDivide(BigDecimal bcs,BigDecimal cs,int decimalPoint){
        if (bcs.doubleValue() == 0d || cs.doubleValue() == 0d)
            return 0d;

        return bcs.divide(cs,decimalPoint,BigDecimal.ROUND_HALF_UP).doubleValue();
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
        Double sumValuationPrice = 0d;
        Double sumValuationPriceReduce = 0d;
        Double sumWarranty = 0d;
        Double sumPerformanceBond = 0d;
        Double sumShouldAmount = 0d;
        Double sumCompensation = 0d;
        for(InspectionAccount inspectionAccount : inspectionAccounts){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(inspectionAccount.getProjectName());
            contentRow.createCell(1).setCellValue(inspectionAccount.getSubcontractorName());
            contentRow.createCell(2).setCellValue(inspectionAccount.getTeamName());
            contentRow.createCell(3).setCellValue(isNull(new BigDecimal(inspectionAccount.getSumContractAmount())));
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
            contentRow.createCell(14).setCellValue(inspectionAccount.getUnderRate()==null?"":new BigDecimal(inspectionAccount.getUnderRate().setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()).doubleValue()*100+"%");
            contentRow.createCell(15).setCellValue(inspectionAccount.getValuationPerson());
            contentRow.createCell(16).setCellValue(inspectionAccount.getRemark());
            sumPrice = addAmount(sumPrice,inspectionAccount.getValuationPrice());
            sumEndPrice = addAmount(sumEndPrice,inspectionAccount.getEndedPrice());
            sumValuationPrice = addAmount(sumValuationPrice,inspectionAccount.getValuationPrice());
            sumValuationPriceReduce = addAmount(sumValuationPriceReduce,inspectionAccount.getValuationPriceReduce());
            sumWarranty = addAmount(sumWarranty,inspectionAccount.getWarranty());
            sumPerformanceBond = addAmount(sumPerformanceBond,inspectionAccount.getPerformanceBond());
            sumShouldAmount = addAmount(sumShouldAmount,inspectionAccount.getShouldAmount());
            sumCompensation = addAmount(sumCompensation,inspectionAccount.getCompensation());
            num++;

        }
        HSSFRow contentRow = sheet.createRow(num);
        contentRow.createCell(0).setCellValue("合计");
        contentRow.createCell(7).setCellValue(sumValuationPrice);
        contentRow.createCell(8).setCellValue(sumValuationPriceReduce);
        contentRow.createCell(9).setCellValue(sumWarranty);
        contentRow.createCell(10).setCellValue(sumPerformanceBond);
        contentRow.createCell(11).setCellValue(sumCompensation);
        contentRow.createCell(12).setCellValue(sumShouldAmount);
        contentRow.createCell(13).setCellValue(sumEndPrice);
        contentRow.createCell(14).setCellValue(
                computerDivide(new BigDecimal(sumPrice),new BigDecimal(sumPrice+sumEndPrice),4)*100+"%"
        );
        return wb;
    }

    private static Double isNull(BigDecimal value){
        if (value == null){
            return 0d;
        }

        return value.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
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


    public static HSSFWorkbook getEngineerChangeExcel(String titleValue,String sheetName,String[] title,
                                                                      List<EngineeringChangeMonthly> engineeringChangeMonthlies){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1,true);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

//        font.setStrikeout(true); //是否使用划线

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(getTitleStyle(wb));
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

        HSSFRow realRow = sheet.createRow(num);
        for (int j = 0;j<title.length;j++){
            HSSFCell cell = realRow.createCell(j);
            cell.setCellValue(title[j]);
            cell.setCellStyle(style);
        }

        Double sumTemporarilyPrice = 0d;
        Double sumConstructionOutputValue = 0d;
        Double sumChangeClaimAmount = 0d;
        //创建内容
        num = num+1;
        for(EngineeringChangeMonthly engineeringChangeMonthly : engineeringChangeMonthlies){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(engineeringChangeMonthly.getId());
            contentRow.createCell(1).setCellValue(engineeringChangeMonthly.getProjectName());
            contentRow.createCell(2).setCellValue(engineeringChangeMonthly.getProjectType());
            contentRow.createCell(3).setCellValue(DateTimeUtil.getYYYYMMDD(engineeringChangeMonthly.getReportTime()));

            Double temporarilyPrice = isNull(engineeringChangeMonthly.getTemporarilyPrice());
            contentRow.createCell(4).setCellValue(temporarilyPrice);
            sumTemporarilyPrice = sumTemporarilyPrice + temporarilyPrice;

            Double constructionOutputValue =  isNull(engineeringChangeMonthly.getConstructionOutputValue());
            contentRow.createCell(5).setCellValue(constructionOutputValue);
            sumConstructionOutputValue = sumConstructionOutputValue + constructionOutputValue;

            Double changeClaimAmount = isNull(engineeringChangeMonthly.getChangeClaimAmount());
            contentRow.createCell(6).setCellValue(changeClaimAmount);
            sumChangeClaimAmount = sumChangeClaimAmount + changeClaimAmount;

            contentRow.createCell(7).setCellValue(isNull(engineeringChangeMonthly.getPercentage()) *100 + "%");
            contentRow.createCell(8).setCellValue(engineeringChangeMonthly.getRemark());
            num++;

        }
        num = num + 1;
        HSSFRow sumRow = sheet.createRow(num);
        sumRow.createCell(0).setCellValue("合计");
        sumRow.createCell(4).setCellValue(sumTemporarilyPrice);
        sumRow.createCell(5).setCellValue(sumConstructionOutputValue);
        sumRow.createCell(6).setCellValue(sumChangeClaimAmount);
        sumRow.createCell(7).setCellValue(Utils.computerDivide(
                new BigDecimal(sumChangeClaimAmount),
                new BigDecimal(sumTemporarilyPrice),4).doubleValue() * 100 + "%");

        return wb;
    }

    public static HSSFWorkbook getEngineerChangeStatisticsExcel(String titleValue,String sheetName,String[] title,
                                                      List<EngineeringChangeMonthly> engineeringChangeMonthlies){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1,true);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

//        font.setStrikeout(true); //是否使用划线

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(getTitleStyle(wb));
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

        HSSFRow realRow = sheet.createRow(num);
        for (int j = 0;j<title.length;j++){
            HSSFCell cell = realRow.createCell(j);
            cell.setCellValue(title[j]);
            cell.setCellStyle(style);
        }

        Double sumTemporarilyPrice = 0d;
        Double sumConstructionOutputValue = 0d;
        Double sumChangeClaimAmount = 0d;
        //创建内容
        num = num+1;
        for(EngineeringChangeMonthly engineeringChangeMonthly : engineeringChangeMonthlies){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(engineeringChangeMonthly.getProjectName());

            Double temporarilyPrice = isNull(engineeringChangeMonthly.getTemporarilyPrice());
            contentRow.createCell(1).setCellValue(temporarilyPrice);
            sumTemporarilyPrice = sumTemporarilyPrice + temporarilyPrice;

            Double constructionOutputValueStatistics = isNull(engineeringChangeMonthly.getConstructionOutputValueStatistics());
            contentRow.createCell(2).setCellValue(constructionOutputValueStatistics);
            sumConstructionOutputValue = sumConstructionOutputValue + constructionOutputValueStatistics;

            Double changeClaimAmountStatistics = isNull(engineeringChangeMonthly.getChangeClaimAmountStatistics());
            contentRow.createCell(3).setCellValue(changeClaimAmountStatistics);
            sumChangeClaimAmount = sumChangeClaimAmount + changeClaimAmountStatistics;

            contentRow.createCell(4).setCellValue(isNull(engineeringChangeMonthly.getPercentageStatistics())*100+"%");
            num++;

        }
        num = num + 1;
        HSSFRow sumRow = sheet.createRow(num);
        sumRow.createCell(0).setCellValue("合计");
        sumRow.createCell(1).setCellValue(sumTemporarilyPrice);
        sumRow.createCell(2).setCellValue(sumConstructionOutputValue);
        sumRow.createCell(3).setCellValue(sumChangeClaimAmount);
        sumRow.createCell(4).setCellValue(Utils.computerDivide(
                new BigDecimal(sumChangeClaimAmount),
                new BigDecimal(sumTemporarilyPrice),4).doubleValue() * 100 + "%");
        return wb;
    }

    public static HSSFWorkbook getContractCompensationStatisticsExcel(String titleValue,String sheetName,String[] title,
                                                                      List<OutOfContractCompensationStatistics> compensationStatistics){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1,true);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

//        font.setStrikeout(true); //是否使用划线

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(getTitleStyle(wb));
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

        //声明列对象
        HSSFCell cell = null;
        HSSFRow oneRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = oneRow.createCell(i);
            if (i == 2){
                cell.setCellValue("已计价金额（元）");
            }else if (i>2 && i<=5){

            }else if (i==6){
                cell.setCellValue("预估金额（元）");
            }else if (i >6 && i<=8){

            }else {
                cell.setCellValue(title[i]);
            }
            cell.setCellStyle(style);
        }

        num = num +1;
        HSSFRow twoRow = sheet.createRow(num);
        for (int j = 2;j<=8;j++){
            HSSFCell cell3Row = twoRow.createCell(j);
            cell3Row.setCellValue(title[j]);
            cell3Row.setCellStyle(style);
        }


        CellRangeAddress address0 = new CellRangeAddress(1,2,0,0);
        CellRangeAddress address1 = new CellRangeAddress(1,2,1,1);
        sheet.addMergedRegion(address0);
        sheet.addMergedRegion(address1);

        CellRangeAddress address3 = new CellRangeAddress(1,1,2,5);
        sheet.addMergedRegion(address3);
        CellRangeAddress address4 = new CellRangeAddress(1,1,6,8);
        sheet.addMergedRegion(address4);

        Double sumStatisticsTotalAmountContract = 0d;
        Double sumStatisticsDailyWorkSubtotal = 0d;
        Double sumStatisticsCompensationSubtotal = 0d;
        Double sumStatisticsAlreadySubtotal = 0d;
        Double sumStatisticsEstimateDailyWorkSubtotal = 0d;
        Double sumStatisticsEstimateCompensationSubtotal = 0d;
        Double sumStatisticsEstimateSubtotal = 0d;
        //创建内容
        num = num+1;
        for(OutOfContractCompensationStatistics out : compensationStatistics){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(out.getProjectName());
            contentRow.createCell(1).setCellValue(out.getProjectType());

            Double statisticsTotalAmountContract = isNull(out.getStatisticsTotalAmountContract());
            contentRow.createCell(2).setCellValue(statisticsTotalAmountContract);
            sumStatisticsTotalAmountContract = sumStatisticsTotalAmountContract+statisticsTotalAmountContract;

            Double statisticsDailyWorkSubtotal = isNull(out.getStatisticsDailyWorkSubtotal());
            contentRow.createCell(3).setCellValue(statisticsDailyWorkSubtotal);
            sumStatisticsDailyWorkSubtotal = sumStatisticsDailyWorkSubtotal + statisticsDailyWorkSubtotal;

            Double statisticsCompensationSubtotal = isNull(out.getStatisticsCompensationSubtotal());
            contentRow.createCell(4).setCellValue(statisticsCompensationSubtotal);
            sumStatisticsCompensationSubtotal = sumStatisticsCompensationSubtotal + statisticsCompensationSubtotal;

            Double statisticsAlreadySubtotal = isNull(out.getStatisticsAlreadySubtotal());
            contentRow.createCell(5).setCellValue(statisticsAlreadySubtotal);
            sumStatisticsAlreadySubtotal = sumStatisticsAlreadySubtotal + statisticsAlreadySubtotal;

            Double statisticsEstimateDailyWorkSubtotal = isNull(out.getStatisticsEstimateDailyWorkSubtotal());
            contentRow.createCell(6).setCellValue(statisticsEstimateDailyWorkSubtotal);
            sumStatisticsEstimateDailyWorkSubtotal = sumStatisticsEstimateDailyWorkSubtotal + statisticsEstimateDailyWorkSubtotal;

            Double statisticsEstimateCompensationSubtotal = isNull(out.getStatisticsEstimateCompensationSubtotal());
            contentRow.createCell(7).setCellValue(statisticsEstimateCompensationSubtotal);
            sumStatisticsEstimateCompensationSubtotal = sumStatisticsEstimateCompensationSubtotal + statisticsEstimateCompensationSubtotal;

            Double statisticsEstimateSubtotal = isNull(out.getStatisticsEstimateSubtotal());
            contentRow.createCell(8).setCellValue(statisticsEstimateSubtotal);
            sumStatisticsEstimateSubtotal = sumStatisticsEstimateSubtotal + statisticsEstimateSubtotal;
            num++;

        }

        num = num + 1;
        HSSFRow sumRow = sheet.createRow(num);
        sumRow.createCell(0).setCellValue("合计");
        sumRow.createCell(2).setCellValue(transformationFromDoubleToString(sumStatisticsTotalAmountContract));
        sumRow.createCell(3).setCellValue(transformationFromDoubleToString(sumStatisticsDailyWorkSubtotal));
        sumRow.createCell(4).setCellValue(transformationFromDoubleToString(sumStatisticsCompensationSubtotal));
        sumRow.createCell(5).setCellValue(transformationFromDoubleToString(sumStatisticsAlreadySubtotal));
        sumRow.createCell(6).setCellValue(transformationFromDoubleToString(sumStatisticsEstimateDailyWorkSubtotal));
        sumRow.createCell(7).setCellValue(transformationFromDoubleToString(sumStatisticsEstimateCompensationSubtotal));
        sumRow.createCell(8).setCellValue(transformationFromDoubleToString(sumStatisticsEstimateSubtotal));

        return wb;
    }

    public static HSSFWorkbook getContractCompensationExcel(String titleValue,String sheetName,String[] title,
                                                            List<OutOfContractCompensationStatistics> outOfContractCompensationStatistics){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1,true);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(getTitleStyle(wb));
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

        //声明列对象
        HSSFCell cell = null;
        HSSFRow oneRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = oneRow.createCell(i);
            if (i == 8){
                cell.setCellValue("已计价金额（元）");
            }else if (i>8 && i<=17){

            }else if (i==24){
                cell.setCellValue("合同外计日工及赔偿预估金额（元）");
            }else if (i >24 && i<=32){

            }else {
                cell.setCellValue(title[i]);
            }
            cell.setCellStyle(style);
        }

        num = num+1;
        HSSFRow twoRow = sheet.createRow(num);
        HSSFCell cell28 = twoRow.createCell(8);
        cell28.setCellValue("合同内计量");
        cell28.setCellStyle(style);
        HSSFCell cell29 = twoRow.createCell(9);
        cell29.setCellValue("计日工");
        cell29.setCellStyle(style);
        HSSFCell cell212 = twoRow.createCell(12);
        cell212.setCellValue("合同外补偿/赔偿");
        cell212.setCellStyle(style);
        HSSFCell cell217 = twoRow.createCell(17);
        cell217.setCellValue("合计");
        cell217.setCellStyle(style);
        HSSFCell cell224 = twoRow.createCell(24);
        cell224.setCellValue("计日工");
        cell224.setCellStyle(style);
        HSSFCell cell227 = twoRow.createCell(27);
        cell227.setCellValue("合同外补偿/赔偿");
        cell227.setCellStyle(style);
        HSSFCell cell232 = twoRow.createCell(32);
        cell232.setCellValue("合计");
        cell232.setCellStyle(style);

        num = num +1;
        HSSFRow thridRow = sheet.createRow(num);
        for (int j = 9;j<=16;j++){
            HSSFCell cell3Row = thridRow.createCell(j);
            cell3Row.setCellValue(title[j]);
            cell3Row.setCellStyle(style);
        }

        for (int n=24;n<=31;n++){
            HSSFCell cell3Row = thridRow.createCell(n);
            cell3Row.setCellValue(title[n]);
            cell3Row.setCellStyle(style);
        }


        CellRangeAddress address0 = new CellRangeAddress(1,3,0,0);
        CellRangeAddress address1 = new CellRangeAddress(1,3,1,1);
        CellRangeAddress address2 = new CellRangeAddress(1,3,2,2);
        CellRangeAddress address3 = new CellRangeAddress(1,3,3,3);
        CellRangeAddress address4 = new CellRangeAddress(1,3,4,4);
        CellRangeAddress address5 = new CellRangeAddress(1,3,5,5);
        CellRangeAddress address6 = new CellRangeAddress(1,3,6,6);
        CellRangeAddress address7 = new CellRangeAddress(1,3,7,7);
        sheet.addMergedRegion(address0);
        sheet.addMergedRegion(address1);
        sheet.addMergedRegion(address2);
        sheet.addMergedRegion(address3);
        sheet.addMergedRegion(address4);
        sheet.addMergedRegion(address5);
        sheet.addMergedRegion(address6);
        sheet.addMergedRegion(address7);

        CellRangeAddress address8 = new CellRangeAddress(1,1,8,17);
        sheet.addMergedRegion(address8);
        CellRangeAddress address9 = new CellRangeAddress(1,1,24,32);
        sheet.addMergedRegion(address9);

        CellRangeAddress address10 = new CellRangeAddress(1,3,18,18);
        CellRangeAddress address11 = new CellRangeAddress(1,3,19,19);
        CellRangeAddress address12 = new CellRangeAddress(1,3,20,20);
        CellRangeAddress address13 = new CellRangeAddress(1,3,21,21);
        CellRangeAddress address14 = new CellRangeAddress(1,3,22,22);
        CellRangeAddress address15 = new CellRangeAddress(1,3,23,23);
        sheet.addMergedRegion(address10);
        sheet.addMergedRegion(address11);
        sheet.addMergedRegion(address12);
        sheet.addMergedRegion(address13);
        sheet.addMergedRegion(address14);
        sheet.addMergedRegion(address15);

        CellRangeAddress address16 = new CellRangeAddress(2,3,8,8);
        sheet.addMergedRegion(address16);
        CellRangeAddress address17 = new CellRangeAddress(2,2,9,11);
        sheet.addMergedRegion(address17);
        CellRangeAddress address18 = new CellRangeAddress(2,2,12,16);
        sheet.addMergedRegion(address18);
        CellRangeAddress address19 = new CellRangeAddress(2,3,17,17);
        sheet.addMergedRegion(address19);
        CellRangeAddress address20 = new CellRangeAddress(2,2,24,26);
        sheet.addMergedRegion(address20);
        CellRangeAddress address21 = new CellRangeAddress(2,2,27,31);
        sheet.addMergedRegion(address21);
        CellRangeAddress address22 = new CellRangeAddress(2,3,32,32);
        sheet.addMergedRegion(address22);

        Double sumTotalAmountContract = 0d;
        Double sumMechanicalClass = 0.00d;
        Double sumSporadicEmployment = 0.00d;
        Double sumDailyWorkSubtotal = 0.00d;
        Double sumOutIn = 0.00d;
        Double sumDisasterDamage = 0.00d;
        Double sumWorkStop = 0.00d;
        Double sumOther = 0.00d;
        Double sumCompensationSubtotal = 0.00d;
        Double sumTotal = 0.00d;
        Double sumAmountAlreadyDisbursed = 0.00d;
        Double sumEstimateMechanicalClass = 0.00d;
        Double sumEstimateSporadicEmployment = 0.00d;
        Double sumEstimateDailyWorkSubtotal = 0.00d;
        Double sumEstimateOutIn = 0.00d;
        Double sumEstimateDisasterDamage = 0.00d;
        Double sumEstimateWorkStop = 0.00d;
        Double sumEstimateOther = 0.00d;
        Double sumEstimateCompensationSubtotal = 0.00d;
        Double sumEstimateTotal = 0.00d;
        //创建内容
        num = num+1;
        for(OutOfContractCompensationStatistics out : outOfContractCompensationStatistics){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(out.getId());
            contentRow.createCell(1).setCellValue(out.getProjectName());
            contentRow.createCell(2).setCellValue(out.getProjectType());
            contentRow.createCell(3).setCellValue(out.getSubcontractorName());
            contentRow.createCell(4).setCellValue(out.getTeamName());
            contentRow.createCell(5).setCellValue(out.getContractNumber());
            contentRow.createCell(6).setCellValue(out.getContractPerson());
            contentRow.createCell(7).setCellValue(DateTimeUtil.getYYYYMMDD(out.getReportTime()));

            Double totalAmountContract =  isNull(out.getTotalAmountContract());
            contentRow.createCell(8).setCellValue(totalAmountContract);
            sumTotalAmountContract = sumTotalAmountContract + totalAmountContract;

            Double mechanicalClass = isNull(out.getMechanicalClass());
            contentRow.createCell(9).setCellValue(mechanicalClass);
            sumMechanicalClass = sumMechanicalClass + mechanicalClass;

            Double sporadicEmployment = isNull(out.getSporadicEmployment());
            contentRow.createCell(10).setCellValue(sporadicEmployment);
            sumSporadicEmployment = sumSporadicEmployment + sporadicEmployment;

            Double dailyWorkSubtotal = isNull(out.getDailyWorkSubtotal());
            contentRow.createCell(11).setCellValue(dailyWorkSubtotal);
            sumDailyWorkSubtotal = sumDailyWorkSubtotal + dailyWorkSubtotal;

            Double outIn = isNull(out.getOutIn());
            contentRow.createCell(12).setCellValue(outIn);
            sumOutIn = sumOutIn + outIn;

            Double disasterDamage = isNull(out.getDisasterDamage());
            contentRow.createCell(13).setCellValue(disasterDamage);
            sumDisasterDamage = disasterDamage + sumDisasterDamage;

            Double workStop = isNull(out.getWorkStop());
            contentRow.createCell(14).setCellValue(workStop);
            sumWorkStop = workStop + sumWorkStop;

            Double other = isNull(out.getOther());
            contentRow.createCell(15).setCellValue(other);
            sumOther = other + sumOther;

            Double compensationSubtotal = isNull(out.getCompensationSubtotal());
            contentRow.createCell(16).setCellValue(compensationSubtotal);
            sumCompensationSubtotal = sumCompensationSubtotal + compensationSubtotal;

            Double total = isNull(out.getTotal());
            contentRow.createCell(17).setCellValue(total);
            sumTotal = total+ sumTotal;

            contentRow.createCell(18).setCellValue(out.getDailyPercentage() == null?"":out.getDailyPercentage().doubleValue()*100+"%");
            contentRow.createCell(19).setCellValue(out.getCompensationPercentage() == null?"":out.getCompensationPercentage().doubleValue()*100+"%");

            Double amountAlreadyDisbursed = isNull(out.getAmountAlreadyDisbursed());
            contentRow.createCell(20).setCellValue(amountAlreadyDisbursed);
            sumAmountAlreadyDisbursed = amountAlreadyDisbursed+sumAmountAlreadyDisbursed;

            contentRow.createCell(21).setCellValue(out.getDisbursedPercentage() == null?"":out.getDisbursedPercentage().doubleValue()*100+"%");
            contentRow.createCell(22).setCellValue(out.getSettlement());
            contentRow.createCell(23).setCellValue(out.getExamination());

            Double estimateMechanicalClass = isNull(out.getEstimateMechanicalClass());
            contentRow.createCell(24).setCellValue(estimateMechanicalClass);
            sumEstimateMechanicalClass = sumEstimateMechanicalClass + estimateMechanicalClass;

            Double estimateSporadicEmployment = isNull(out.getEstimateSporadicEmployment());
            contentRow.createCell(25).setCellValue(estimateSporadicEmployment);
            sumEstimateSporadicEmployment = estimateSporadicEmployment + sumEstimateSporadicEmployment;

            Double estimateDailyWorkSubtotal = isNull(out.getEstimateDailyWorkSubtotal());
            contentRow.createCell(26).setCellValue(isNull(out.getEstimateDailyWorkSubtotal()));
            sumEstimateDailyWorkSubtotal = sumEstimateDailyWorkSubtotal + estimateDailyWorkSubtotal;

            Double estimateOutIn = isNull(out.getEstimateOutIn());
            contentRow.createCell(27).setCellValue(estimateOutIn);
            sumEstimateOutIn = sumEstimateOutIn + estimateOutIn;

            Double estimateDisasterDamage = isNull(out.getEstimateDisasterDamage());
            contentRow.createCell(28).setCellValue(estimateDisasterDamage);
            sumEstimateDisasterDamage = sumEstimateDisasterDamage + estimateDisasterDamage;

            Double estimateWorkStop = isNull(out.getEstimateWorkStop());
            contentRow.createCell(29).setCellValue(estimateWorkStop);
            sumEstimateWorkStop = sumEstimateWorkStop + estimateWorkStop;

            Double estimateOther = isNull(out.getEstimateOther());
            contentRow.createCell(30).setCellValue(estimateOther);
            sumEstimateOther = estimateOther+sumEstimateOther;

            Double estimateCompensationSubtotal = isNull(out.getEstimateCompensationSubtotal());
            contentRow.createCell(31).setCellValue(estimateCompensationSubtotal);
            sumEstimateCompensationSubtotal = sumEstimateCompensationSubtotal + estimateCompensationSubtotal;

            Double estimateTotal = isNull(out.getEstimateTotal());
            contentRow.createCell(32).setCellValue(estimateTotal);
            sumEstimateTotal = sumEstimateTotal + estimateTotal;
            num++;

        }
        num = num+1;
        HSSFRow contentRow = sheet.createRow(num);
        contentRow.createCell(0).setCellValue("合计");
        contentRow.createCell(8).setCellValue(sumTotalAmountContract);
        contentRow.createCell(9).setCellValue(sumMechanicalClass);
        contentRow.createCell(10).setCellValue(sumSporadicEmployment);
        contentRow.createCell(11).setCellValue(sumDailyWorkSubtotal);
        contentRow.createCell(12).setCellValue(sumOutIn);
        contentRow.createCell(13).setCellValue(sumDisasterDamage);
        contentRow.createCell(14).setCellValue(sumWorkStop);
        contentRow.createCell(15).setCellValue(sumOther);
        contentRow.createCell(16).setCellValue(sumCompensationSubtotal);
        contentRow.createCell(17).setCellValue(sumTotal);

        Double sumDailyPercentage = Utils.computerDivide(new BigDecimal(sumDailyWorkSubtotal),
                new BigDecimal(sumTotal),4).doubleValue();
        contentRow.createCell(18).setCellValue(sumDailyPercentage * 100 + "%");

        Double sumCompensationPercentage = Utils.computerDivide(new BigDecimal(sumCompensationSubtotal),
                new BigDecimal(sumTotal),4).doubleValue();
        contentRow.createCell(19).setCellValue(sumCompensationPercentage*100 + "%");
        contentRow.createCell(20).setCellValue(sumAmountAlreadyDisbursed);
        Double sumDisbursedPercentage = Utils.computerDivide(new BigDecimal(sumAmountAlreadyDisbursed),
                new BigDecimal(sumCompensationSubtotal),4).doubleValue();
        contentRow.createCell(21).setCellValue(sumDisbursedPercentage*100 + "%");
        contentRow.createCell(24).setCellValue(sumEstimateMechanicalClass);
        contentRow.createCell(25).setCellValue(sumEstimateSporadicEmployment);
        contentRow.createCell(26).setCellValue(sumEstimateDailyWorkSubtotal);
        contentRow.createCell(27).setCellValue(sumEstimateOutIn);
        contentRow.createCell(28).setCellValue(sumEstimateDisasterDamage);
        contentRow.createCell(29).setCellValue(sumEstimateWorkStop);
        contentRow.createCell(30).setCellValue(sumEstimateOther);
        contentRow.createCell(31).setCellValue(sumEstimateCompensationSubtotal);
        contentRow.createCell(32).setCellValue(sumEstimateTotal);
        return wb;
    }

    public static HSSFWorkbook getLossExcel(String titleValue,String sheetName,String[] title,
                                                                      List<FinancialLoss> financialLosses){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1,true);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

//        font.setStrikeout(true); //是否使用划线

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(getTitleStyle(wb));
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

        //声明列对象
        HSSFCell cell = null;
        HSSFRow oneRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = oneRow.createCell(i);
            if (i == 4){
                cell.setCellValue("收入（万元）");
            }else if (i>4 && i<=7){

            }else if (i==8){
                cell.setCellValue("成本(不含上交公司管理费)（万元）");
            }else if (i >8 && i<=10){

            }else if (i==11){
                cell.setCellValue("亏损情况（万元）");
            }else if (i>11 && i<=14){

            }else if (i==15){
                cell.setCellValue("潜盈（+）潜亏（-）情况（万元）");
            }else if (i>15 && i <=20){

            }else {
                cell.setCellValue(title[i]);
            }
            cell.setCellStyle(style);
        }

        num = num +1;
        HSSFRow twoRow = sheet.createRow(num);
        HSSFCell cell2Row = twoRow.createCell(15);
        cell2Row.setCellValue("账内潜亏");
        cell2Row.setCellStyle(style);

        num = num+1;
        HSSFRow thirdRow = sheet.createRow(num);
        for (int j=4;j<=18;j++){
            HSSFCell cell3Row = thirdRow.createCell(j);
            cell3Row.setCellValue(title[j]);
            cell3Row.setCellStyle(style);
        }


        CellRangeAddress address0 = new CellRangeAddress(1,3,0,0);
        CellRangeAddress address1 = new CellRangeAddress(1,3,1,1);
        CellRangeAddress address2 = new CellRangeAddress(1,3,2,2);
        CellRangeAddress address3 = new CellRangeAddress(1,3,3,3);
        CellRangeAddress address4 = new CellRangeAddress(1,3,21,21);
        sheet.addMergedRegion(address0);
        sheet.addMergedRegion(address1);
        sheet.addMergedRegion(address2);
        sheet.addMergedRegion(address3);
        sheet.addMergedRegion(address4);

        CellRangeAddress address5 = new CellRangeAddress(1,2,4,7);
        CellRangeAddress address6 = new CellRangeAddress(1,2,8,10);
        CellRangeAddress address7 = new CellRangeAddress(1,2,11,14);
        sheet.addMergedRegion(address5);
        sheet.addMergedRegion(address6);
        sheet.addMergedRegion(address7);

        CellRangeAddress address8 = new CellRangeAddress(1,1,15,20);
        sheet.addMergedRegion(address8);
        CellRangeAddress address9 = new CellRangeAddress(2,2,15,18);
        sheet.addMergedRegion(address9);

        CellRangeAddress address10 = new CellRangeAddress(2,3,19,19);
        sheet.addMergedRegion(address10);
        CellRangeAddress address11 = new CellRangeAddress(2,3,20,20);
        sheet.addMergedRegion(address11);

        Double sumTemporarilyPrice = 0d;
        Double sumAlreadyPriced = 0d;
        Double sumUnPriced = 0d;
        Double sumSumPriced = 0d;
        Double sumConfirmPriced = 0d;
        Double sumInBookCost = 0d;
        Double sumOutBookCost = 0d;
        Double sumSumBookCost = 0d;
        Double sumLossAmount = 0d;
        Double sumConfirmedNetProfit = 0d;
        Double sumUnConfirmedNetProfit = 0d;
        //创建内容
        num = num+1;
        for(FinancialLoss loss : financialLosses){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(loss.getId());
            contentRow.createCell(1).setCellValue(loss.getProjectName());
            contentRow.createCell(2).setCellValue(loss.getReportYear()+"第"+loss.getQuarter()+"季度");

            Double temporarilyPrice = isNull(loss.getTemporarilyPrice());
            contentRow.createCell(3).setCellValue(temporarilyPrice);
            sumTemporarilyPrice = sumTemporarilyPrice + temporarilyPrice;

            Double alreadyPriced = isNull(loss.getAlreadyPriced());
            contentRow.createCell(4).setCellValue(alreadyPriced);
            sumAlreadyPriced = sumAlreadyPriced + alreadyPriced;

            Double unPriced = isNull(loss.getUnPriced());
            contentRow.createCell(5).setCellValue(unPriced);
            sumUnPriced = sumUnPriced + unPriced;

            Double sumPriced = isNull(loss.getSumPriced());
            contentRow.createCell(6).setCellValue(sumPriced);
            sumSumPriced = sumSumPriced + sumPriced;

            Double confirmPriced = isNull(loss.getConfirmPriced());
            contentRow.createCell(7).setCellValue(confirmPriced);
            sumConfirmPriced = sumConfirmPriced + confirmPriced;

            Double inBookCost = isNull(loss.getInBookCost());
            contentRow.createCell(8).setCellValue(inBookCost);
            sumInBookCost = sumInBookCost + inBookCost;

            Double outBookCost = isNull(loss.getOutBookCost());
            contentRow.createCell(9).setCellValue(outBookCost);
            sumOutBookCost = sumOutBookCost + outBookCost;

            Double sumBookCost = isNull(loss.getSumBookCost());
            contentRow.createCell(10).setCellValue(sumBookCost);
            sumSumBookCost = sumSumBookCost + sumBookCost;

            Double lossAmount = isNull(loss.getLossAmount());
            contentRow.createCell(11).setCellValue(lossAmount);
            sumLossAmount = sumLossAmount + lossAmount;

            Double confirmedNetProfit = isNull(loss.getConfirmedNetProfit());
            contentRow.createCell(12).setCellValue(confirmedNetProfit);
            sumConfirmedNetProfit = sumConfirmedNetProfit + confirmedNetProfit;

            Double unConfirmedNetProfit =  isNull(loss.getUnConfirmedNetProfit());
            contentRow.createCell(13).setCellValue(unConfirmedNetProfit);
            sumUnConfirmedNetProfit = sumUnConfirmedNetProfit + unConfirmedNetProfit;

            contentRow.createCell(14).setCellValue(isNull(loss.getLossRatio())*100+"%");
            contentRow.createCell(15).setCellValue(isNull(loss.getContractReceivable()));
            contentRow.createCell(16).setCellValue(isNull(loss.getPrepayments()));
            contentRow.createCell(17).setCellValue(isNull(loss.getOther()));
            contentRow.createCell(18).setCellValue(isNull(loss.getProfitLossSubtotal()));
            contentRow.createCell(19).setCellValue(isNull(loss.getPotentialLoss()));
            contentRow.createCell(20).setCellValue(isNull(loss.getTotalProfitLoss()));
            contentRow.createCell(21).setCellValue(loss.getRemark());
            num++;

        }
        return wb;
    }

    public static HSSFWorkbook getConfirmationExcel(String titleValue,String sheetName,String[] title,
                                            List<ConfirmationOfRights> confirmationOfRights){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        int num = 1;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1,true);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(num);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

//        font.setStrikeout(true); //是否使用划线

        HSSFRow titleRow = sheet.createRow(0); //title
        HSSFCell titleCell = null;
        for (int i=0;i<title.length;i++){
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(getTitleStyle(wb));
            if (i == 0){
                titleCell.setCellValue(titleValue);
            }
        }
        CellRangeAddress merge = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(merge);

        //声明列对象
        HSSFCell cell = null;
        HSSFRow oneRow = sheet.createRow(num);
        for(int i=0;i<title.length;i++){
            cell = oneRow.createCell(i);
            if (i == 2){
                cell.setCellValue("年初余额");
            }else if (i>2 && i<=6){

            }else if (i==7){
                cell.setCellValue("本年截至本期");
            }else if (i >7 && i<=10){

            }else if (i == 11){
                cell.setCellValue("  ");
            }else if (i==12){
                cell.setCellValue("开累情况");
            }else if (i>12 && i<=16){

            }else {
                cell.setCellValue(title[i]);
            }
            cell.setCellStyle(style);
        }

        num = num +1;
        HSSFRow twoRow = sheet.createRow(num);
        for(int i=2;i<title.length;i++){
            cell = twoRow.createCell(i);
            if (i == 4){
                cell.setCellValue("上年末完工未计价");
            }else if (i>4 && i<=6){

            }else if (i==8){
                cell.setCellValue("截至本期验工计价");
            }else if (i >8 && i<=10){

            }else if(i == 11){
                cell.setCellValue("  ");
            }else if (i==14){
                cell.setCellValue("期末完工未计价");
            }else if (i>14 && i<=16){

            }else {
                cell.setCellValue(title[i]);
            }
            cell.setCellStyle(style);
        }

        num = num+1;
        HSSFRow thirdRow = sheet.createRow(num);
        for (int j=4;j<=16;j++){
            HSSFCell cell3Row = thirdRow.createCell(j);
            cell3Row.setCellValue(title[j]);
            cell3Row.setCellStyle(style);
        }


        CellRangeAddress address0 = new CellRangeAddress(1,1,2,6);
        CellRangeAddress address1 = new CellRangeAddress(1,1,7,10);
        CellRangeAddress address2 = new CellRangeAddress(1,1,12,16);
        CellRangeAddress address00 = new CellRangeAddress(1,3,0,0);
        CellRangeAddress address11 = new CellRangeAddress(1,3,1,1);
        CellRangeAddress address3 = new CellRangeAddress(2,3,2,2);
        CellRangeAddress address4 = new CellRangeAddress(2,3,3,3);
        sheet.addMergedRegion(address0);
        sheet.addMergedRegion(address1);
        sheet.addMergedRegion(address2);
        sheet.addMergedRegion(address3);
        sheet.addMergedRegion(address4);
        sheet.addMergedRegion(address00);
        sheet.addMergedRegion(address11);

        CellRangeAddress address5 = new CellRangeAddress(2,3,7,7);
        CellRangeAddress address6 = new CellRangeAddress(2,3,12,12);
        CellRangeAddress address7 = new CellRangeAddress(2,3,13,13);
        sheet.addMergedRegion(address5);
        sheet.addMergedRegion(address6);
        sheet.addMergedRegion(address7);

        CellRangeAddress address8 = new CellRangeAddress(2,2,4,6);
        sheet.addMergedRegion(address8);
        CellRangeAddress address9 = new CellRangeAddress(2,2,8,10);
        sheet.addMergedRegion(address9);

        CellRangeAddress address10 = new CellRangeAddress(2,2,14,16);
        sheet.addMergedRegion(address10);

        //创建内容
        num = num+1;
        for(ConfirmationOfRights confirmationOfRights1 : confirmationOfRights){
            HSSFRow contentRow = sheet.createRow(num);
            contentRow.createCell(0).setCellValue(confirmationOfRights1.getProjectName());
            contentRow.createCell(1).setCellValue(confirmationOfRights1.getYear()+"年"+confirmationOfRights1.getQuarter()+"季度");
            contentRow.createCell(2).setCellValue(isNull(confirmationOfRights1.getBalanceCompleteValue()));
            contentRow.createCell(3).setCellValue(isNull(confirmationOfRights1.getBalanceInspectionValue()));
            contentRow.createCell(4).setCellValue(isNull(confirmationOfRights1.getSumBalance()));
            contentRow.createCell(5).setCellValue(isNull(confirmationOfRights1.getBalanceShould()));
            contentRow.createCell(6).setCellValue(isNull(confirmationOfRights1.getBalanceChange()));
            contentRow.createCell(7).setCellValue(isNull(confirmationOfRights1.getCurrentProductionValue()));
            contentRow.createCell(8).setCellValue(isNull(confirmationOfRights1.getSumHalfOne()));
            contentRow.createCell(9).setCellValue(isNull(confirmationOfRights1.getHalfCompletedValue()));
            contentRow.createCell(10).setCellValue(isNull(confirmationOfRights1.getOneCompletedValue()));
            contentRow.createCell(11).setCellValue(isNull(confirmationOfRights1.getChangeValue()));
            contentRow.createCell(12).setCellValue(isNull(confirmationOfRights1.getCompletedValue()));
            contentRow.createCell(13).setCellValue(isNull(confirmationOfRights1.getInspection()));
            contentRow.createCell(14).setCellValue(isNull(confirmationOfRights1.getSumFinalPeriod()));
            contentRow.createCell(15).setCellValue(isNull(confirmationOfRights1.getFinalPeriodShould()));
            contentRow.createCell(16).setCellValue(isNull(confirmationOfRights1.getFinalPeriodChange()));
            num++;

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

    private static HSSFCellStyle getTitleStyle(HSSFWorkbook wb){
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 20); //字体高度);
        style.setFont(font);

        return style;
    }
}
