package com.crcc.common.service.impl;

import com.crcc.common.mapper.SubcontractorResumeMapper;
import com.crcc.common.model.SubcontractorResume;
import com.crcc.common.service.SubcontractorResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class SubcontractorResumeServiceImpl implements SubcontractorResumeService{

    @Autowired
    private SubcontractorResumeMapper subcontractorResumeMapper;

    @Override
    public Long addResume(SubcontractorResume subcontractorResume) {
        subcontractorResume.setCreateTime(new Date());
        subcontractorResumeMapper.insertSelective(subcontractorResume);
        return subcontractorResume.getId();
    }

    @Override
    public boolean updateResume(SubcontractorResume subcontractorResume) {
        subcontractorResume.setUpdateTime(new Date());
        int result = subcontractorResumeMapper.updateByPrimaryKeySelective(subcontractorResume);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public List<SubcontractorResume> listSubcontractorResumeForPage(String subcontractorName, String projectEvaluation,
                                                                    String gm,Integer offset,Integer length) {
        return subcontractorResumeMapper.listForPage(subcontractorName,projectEvaluation,gm,offset,length);
    }

    @Override
    public Integer listSubcontractorResumeForPageSize(String subcontractorName, String projectEvaluation, String gm) {
        return subcontractorResumeMapper.listForPageSize(subcontractorName,projectEvaluation,gm);
    }

    @Override
    public SubcontractorResume getDetails(Long subcontractorResumeId) {
        return subcontractorResumeMapper.getDetails(subcontractorResumeId);
    }

    @Override
    public List<SubcontractorResume> listResumeBySubcontractorId(Long subcontractorId) {
        return subcontractorResumeMapper.listResumeBySubcontractorId(subcontractorId);
    }

    @Override
    public boolean deletedById(Long id) {
        int result = subcontractorResumeMapper.deleteByPrimaryKey(id);
        if (result != 0)
            return true;
        return false;
    }
}
