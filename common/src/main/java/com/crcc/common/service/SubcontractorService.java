package com.crcc.common.service;

import com.crcc.common.model.Subcontractor;

import java.util.List;

public interface SubcontractorService {
    Long addSubcontractor(Subcontractor subcontractor);

    boolean updateSubcontractor(Subcontractor subcontractor);

    Subcontractor getDetails(Long subcontractorId);

    List<Subcontractor> listSubcontractor(String name,String type,String professionType,Integer minAmount,Integer maxAmount,
                                          String shareEvaluation,String groupEvaluation,String companyEvaluation,
                                          Integer offset,Integer length,Integer isValid);

    Integer listSubcontractorSize(String name,String type,String professionType,Integer minAmount,Integer maxAmount,
                                  String shareEvaluation,String groupEvaluation,String companyEvaluation,Integer isValid);

    List<Subcontractor> selectSubcontractorByName(String subcontractorName);

    boolean isCanAdd(Subcontractor subcontractor);



}
