package com.crrcc.common.test;

import com.crcc.common.mapper.SubcontractorMapper;
import com.crcc.common.model.Subcontractor;
import com.crcc.common.service.LaborAccountService;
import com.crcc.common.service.RedisService;
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

@SuppressWarnings("SpringJavaAutowiringInspection")
@SpringBootTest(classes = ApplicationTest.class)
@RunWith(SpringRunner.class)
public class ExprotServiceTest {

    @Autowired
    private SubcontractorService subcontractorService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LaborAccountService laborAccountService;

    @Autowired
    private SubcontractorMapper subcontractorMapper;


    @Test
    @Ignore
    public void test1(){

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(3);
        list.add(2);
        list.add(5);
        list.add(4);

        list.sort((a,b) -> {
            return a-b;
        });

        for (Integer i : list){
            System.out.println(i);
        }

/*        String code = getCode();
        System.out.println(">>>>>>>>>>>>>>>>>>>"+code);*/
/*        laborAccountService.listLaborAccount(null,null,null,null,
                null,null,null,null);*/
/*        String value = subcontractorService.getCodeTest();
        System.out.println("--------->"+value);*/
    }

    @Test
    @Ignore
    public void testUpdateCode(){
        List<Subcontractor> subcontractors = subcontractorService.listSubcontractor(null,null,null,null,null,null,
                null,null,null,null,null);
        for (Subcontractor subcontractor : subcontractors){
            subcontractor.setCode(getCode());
            subcontractorService.updateSubcontractor(subcontractor);
        }
    }

    @Test
    @Ignore
    public void test() throws Exception{
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("/Users/taoran/Desktop/sub2.xlsx"));


            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null){
                Integer maxRow = sheet.getLastRowNum();
                List<Subcontractor> subcontractors = new ArrayList<Subcontractor>();
                for (int rowNum = 50;rowNum<maxRow;rowNum++){
                    if (rowNum >= 456)
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

    private String getCode(){
        Long num = redisService.incrby("subcontractor_key",1);

        //不知为何原因，redise的 key 老是被重置，初步判断是被人删除了，封锁了端口后再次发生，所以写了该段代码
        if (num <= 1){
            List<Subcontractor> subcontractors = subcontractorMapper.listForPage(null,null,null,null,null,null,
                    null,null,0,1,null);
            if (subcontractors != null && subcontractors.size() > 0){
                String midCode = subcontractors.get(0).getCode();
                Long midNum = Long.parseLong(midCode.substring(1,5));
                redisService.setStr("subcontractor_key",midNum.toString());
                num = midNum;
            }

        }
        if (num < 10){
            return "0000"+num;
        }

        if (num >= 10 && num < 100){
            return "000"+num;
        }

        if (num>=100 && num < 1000){
            return "00"+num;
        }

        if (num >= 1000 && num <10000){
            return "0"+num;
        }

        if (num >= 10000)
            return num.toString();

        return "00000";

    }

}
