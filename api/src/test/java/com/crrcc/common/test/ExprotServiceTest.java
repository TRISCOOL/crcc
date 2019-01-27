package com.crrcc.common.test;

import com.crcc.common.model.Subcontractor;
import com.crcc.common.service.SubcontractorService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.Utils;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = ApplicationTest.class)
@RunWith(SpringRunner.class)
public class ExprotServiceTest {

    @Autowired
    private SubcontractorService subcontractorService;

    @Test
    @Ignore
    public void test() throws Exception{
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("/Users/taoran/sub2.xlsx"));


            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null){
                Integer maxRow = sheet.getLastRowNum();
                List<Subcontractor> subcontractors = new ArrayList<Subcontractor>();
                for (int rowNum = 1;rowNum<maxRow;rowNum++){
                    if (rowNum >= 50)
                        break;

                    Row row = sheet.getRow(rowNum);
                    short cellMax = row.getLastCellNum();
                    Subcontractor subcontractor = new Subcontractor();
                    for (int cellNum = 0;cellNum<cellMax;cellNum++){

                        Cell cell = row.getCell(cellNum);
                        System.out.println("row :"+rowNum+" cell :" + cellNum);
                        switch (cellNum){
                            case 0:
                                subcontractor.setName(cell.getStringCellValue().trim());
                                break;
                            case 1:
                                if (cell.getCellTypeEnum() == CellType.STRING){
                                    String s = cell.getStringCellValue().trim();
                                    subcontractor.setBusinessLicenseValidityPeriod(DateTimeUtil.parseDate(s));
                                }else if (cell.getCellTypeEnum() == CellType.NUMERIC){
                                    Date date1 = cell.getDateCellValue();
                                    subcontractor.setSetUpTime(date1);
                                }
                                break;
                            case 2:
                                subcontractor.setTaxpayerType(cell.getStringCellValue());
                                break;
                            case 3:
                                if (cell.getCellTypeEnum() == CellType.NUMERIC){
                                    subcontractor.setRegisteredCapital(new BigDecimal(cell.getNumericCellValue()));
                                }else {
                                    String value = cell.getStringCellValue();
                                    subcontractor.setRegisteredCapital(new BigDecimal(Double.parseDouble(value)));
                                }break;
                            case 4:
                                subcontractor.setAddress(cell.getStringCellValue());
                                break;
                            case 5:
                                subcontractor.setLegalPersonName(cell.getStringCellValue());
                                break;
                            case 6:
                                subcontractor.setBusinessLicenseCode(cell.getStringCellValue());
                                break;
                            case 7:
                                if (cell.getCellTypeEnum() == CellType.STRING){
                                    String s = cell.getStringCellValue().trim();
                                    if ("长期".equals(s)){
                                        subcontractor.setBusinessLicenseValidityPeriod(DateTimeUtil.parseDate("2200-01-01"));
                                    }
                                }else if (cell.getCellTypeEnum() == CellType.NUMERIC){
                                    Date date1 = cell.getDateCellValue();
                                    subcontractor.setBusinessLicenseValidityPeriod(date1);
                                }
                                break;
                            case 8:
                                subcontractor.setBusinessLicenseFrom(cell.getStringCellValue());
                                break;
                            case 9:
                                subcontractor.setRemark(cell.getStringCellValue());
                                break;


                        }
                    }
                    subcontractors.add(subcontractor);
                }
                for (Subcontractor s : subcontractors){
                    subcontractorService.addSubcontractor(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
