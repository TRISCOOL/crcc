package com.crcc.common.service;

import com.crcc.common.model.Subcontractor;

import java.util.List;

public interface SubcontractorService {
    Long addSubcontractor(Subcontractor subcontractor);

    boolean updateSubcontractor(Subcontractor subcontractor);

    Subcontractor getDetails(Long subcontractorId);

    List<Subcontractor> listSubcontractor(String name,Long typeId,Long professionType,Integer minAmount,Integer maxAmount,
                                          String shareEvaluation,String groupEvaluation,String companyEvaluation,
                                          Integer offset,Integer length);

    Integer listSubcontractorSize(String name,Long typeId,Long professionType,Integer minAmount,Integer maxAmount,
                                  String shareEvaluation,String groupEvaluation,String companyEvaluation);



}
