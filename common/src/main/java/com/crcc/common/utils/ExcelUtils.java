package com.crcc.common.utils;

import com.crcc.common.model.ProjectInfo;
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
}
