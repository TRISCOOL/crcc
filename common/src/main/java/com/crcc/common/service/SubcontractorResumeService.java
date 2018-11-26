package com.crcc.common.service;

import com.crcc.common.model.SubcontractorResume;

import java.util.List;

public interface SubcontractorResumeService {

    Long addResume(SubcontractorResume subcontractorResume);

    boolean updateResume(SubcontractorResume subcontractorResume);

    List<SubcontractorResume> listSubcontractorResumeForPage(String subcontractorName,String projectEvaluation,
                                                             String gm,Integer offset,Integer length);

    SubcontractorResume getDetails(Long subcontractorResumeId);
}
