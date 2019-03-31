package com.crcc.common.service;

import com.crcc.common.model.OutOfContractCompensationStatistics;
import com.crcc.common.model.OutOfContractCompensationStatisticsTotal;

import java.util.List;

public interface CompensationStatisticsService {
    Long addCompensationStatistics(OutOfContractCompensationStatistics contractCompensationStatistics);

    OutOfContractCompensationStatistics getDetailsById(Long id);

    boolean updateCompensationStatistics(OutOfContractCompensationStatistics outOfContractCompensationStatistics);

    List<OutOfContractCompensationStatistics> listForPage(String projectName,String subcontractName,String teamName,
                                                          String year,Integer quarter,Integer offset,Integer limit,
                                                          Long projectId);

    Integer listForPageSize(String projectName,String subcontractName,String teamName,String year,Integer quarter,
                            Long projectId);

    List<OutOfContractCompensationStatistics> listStatisticsForPage(String projectName,Long projectId,Integer offset,
                                                                    Integer length);

    Integer listStatisticsForPageSize(String projectName,Long projectId);

    OutOfContractCompensationStatisticsTotal getTotal(String projectName,String subcontractName,String teamName,String year,Integer quarter,
                                                      Long projectId);

    OutOfContractCompensationStatisticsTotal getTotalStatistics(String projectName,Long projectId);

    boolean deleteOneById(Long id);


}
