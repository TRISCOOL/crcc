package com.crcc.common.service.impl;

import com.crcc.common.mapper.SubcontractorMapper;
import com.crcc.common.model.Subcontractor;
import com.crcc.common.service.RedisService;
import com.crcc.common.service.SubcontractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class SubcontractorServiceImpl implements SubcontractorService{

    @Autowired
    private SubcontractorMapper subcontractorMapper;

    @Autowired
    private RedisService redisService;

    private static String SUBCONTRACTOR_CODE_KEY = "subcontractor_key";

    @Override
    public Long addSubcontractor(Subcontractor subcontractor) {



        subcontractor.setCreateTime(new Date());
        subcontractor.setCode(getCode());
        subcontractorMapper.insertSelective(subcontractor);
        return subcontractor.getId();
    }

    private String getCode(){
        Long num = redisService.incrby(SUBCONTRACTOR_CODE_KEY,1);

        //不知为何原因，redise的 key 老是被重置，初步判断是被人删除了，封锁了端口后再次发生，所以写了该段代码
        if (num <= 1){
            List<Subcontractor> subcontractors = subcontractorMapper.listForPage(null,null,null,null,null,null,
                    null,null,0,1,null);
            if (subcontractors != null && subcontractors.size() > 0){
                String midCode = subcontractors.get(0).getCode();
                Long midNum = Long.parseLong(midCode.substring(1,5));
                midNum = midNum + 1;
                redisService.setStr(SUBCONTRACTOR_CODE_KEY,midNum.toString());
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

    @Override
    public boolean updateSubcontractor(Subcontractor subcontractor) {
        subcontractor.setUpdateTime(new Date());
        int result = subcontractorMapper.updateByPrimaryKeySelective(subcontractor);
        if (result != 0){
            return true;
        }
        return false;
    }

    @Override
    public Subcontractor getDetails(Long subcontractorId) {
        return subcontractorMapper.findDetailsById(subcontractorId);
    }

    @Override
    public List<Subcontractor> listSubcontractor(String name, String type, String professionType, Integer minAmount,
                                                 Integer maxAmount, String shareEvaluation, String groupEvaluation,
                                                 String companyEvaluation, Integer offset, Integer length,Integer isValid) {
        return subcontractorMapper.listForPage(name,type,professionType,minAmount,maxAmount,shareEvaluation,
                groupEvaluation,companyEvaluation,offset,length,isValid);
    }

    @Override
    public Integer listSubcontractorSize(String name, String type, String professionType, Integer minAmount, Integer maxAmount, String shareEvaluation, String groupEvaluation, String companyEvaluation,Integer isValid) {
        return subcontractorMapper.listForPageSize(name,type,professionType,minAmount,maxAmount,shareEvaluation,
                groupEvaluation,companyEvaluation,isValid);
    }

    @Override
    public List<Subcontractor> selectSubcontractorByName(String subcontractorName) {
        return subcontractorMapper.selectSubcontractorByName(subcontractorName);
    }

    @Override
    public boolean isCanAdd(Subcontractor subcontractor) {
        List<Subcontractor> subcontractorList = selectSubcontractorByName(subcontractor.getName().trim());
        if (subcontractorList != null && subcontractorList.size() > 0){
            return false;
        }
        return true;
    }

    @Override
    public String getCodeTest() {
        return getCode();
    }
}
