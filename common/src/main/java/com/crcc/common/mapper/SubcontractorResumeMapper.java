package com.crcc.common.mapper;

import com.crcc.common.model.SubcontractorResume;

public interface SubcontractorResumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SubcontractorResume record);

    int insertSelective(SubcontractorResume record);

    SubcontractorResume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SubcontractorResume record);

    int updateByPrimaryKey(SubcontractorResume record);
}