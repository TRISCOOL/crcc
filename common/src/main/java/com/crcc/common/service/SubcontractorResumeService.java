package com.crcc.common.service;

import com.crcc.common.model.SubcontractorResume;

import java.util.List;

public interface SubcontractorResumeService {

    Long addResume(SubcontractorResume subcontractorResume);

    boolean updateResume(SubcontractorResume subcontractorResume);

    List<SubcontractorResume> listSubcontractorResumeForPage(String subcontractorName,String projectEvaluation,
                                                             String gm,Integer offset,Integer length);

    Integer listSubcontractorResumeForPageSize(String subcontractorName,String projectEvaluation,
                                           String gm);

    SubcontractorResume getDetails(Long subcontractorResumeId);

    List<SubcontractorResume> listResumeBySubcontractorId(Long subcontractorId);

    boolean deletedById(Long id);
}
