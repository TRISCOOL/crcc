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
                                                 String companyEvaluation, Integer offset, Integer length) {
        return subcontractorMapper.listForPage(name,type,professionType,minAmount,maxAmount,shareEvaluation,
                groupEvaluation,companyEvaluation,offset,length);
    }

    @Override
    public Integer listSubcontractorSize(String name, String type, String professionType, Integer minAmount, Integer maxAmount, String shareEvaluation, String groupEvaluation, String companyEvaluation) {
        return subcontractorMapper.listForPageSize(name,type,professionType,minAmount,maxAmount,shareEvaluation,
                groupEvaluation,companyEvaluation);
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
}
