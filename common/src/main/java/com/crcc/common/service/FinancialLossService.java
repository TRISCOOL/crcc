package com.crcc.common.service;

import com.crcc.common.model.FinancialLoss;
import com.crcc.common.model.FinancialLossTotal;

import java.util.List;

public interface FinancialLossService {
    Long addFinancialLoss(FinancialLoss financialLoss);

    boolean updateFinancialLoss(FinancialLoss financialLoss);

    List<FinancialLoss> listForPage(Long projectId,String projectName,String year,Integer quarter,Integer offset,Integer length);

    Integer listForPageSize(Long projectId,String projectName,String year,Integer quarter);

    FinancialLossTotal getTotal(Long projectId,String projectName,String year,Integer quarter);
}
