package com.crcc.common.service.impl;

import com.crcc.common.mapper.SubcontractorMapper;
import com.crcc.common.model.Subcontractor;
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

    @Override
    public Long addSubcontractor(Subcontractor subcontractor) {
        subcontractor.setCreateTime(new Date());
        subcontractorMapper.insertSelective(subcontractor);
        return subcontractor.getId();
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
    public List<Subcontractor> listSubcontractor(String name, String type, Long professionType, Integer minAmount,
                                                 Integer maxAmount, String shareEvaluation, String groupEvaluation,
                                                 String companyEvaluation, Integer offset, Integer length) {
        return subcontractorMapper.listForPage(name,type,professionType,minAmount,maxAmount,shareEvaluation,
                groupEvaluation,companyEvaluation,offset,length);
    }

    @Override
    public Integer listSubcontractorSize(String name, String type, Long professionType, Integer minAmount, Integer maxAmount, String shareEvaluation, String groupEvaluation, String companyEvaluation) {
        return subcontractorMapper.listForPageSize(name,type,professionType,minAmount,maxAmount,shareEvaluation,
                groupEvaluation,companyEvaluation);
    }
}
