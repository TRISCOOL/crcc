package com.crcc.common.service.impl;

import com.crcc.common.mapper.FinancialLossMapper;
import com.crcc.common.model.FinancialLoss;
import com.crcc.common.service.FinancialLossService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class FinancialLossServiceImpl implements FinancialLossService{

    @Autowired
    private FinancialLossMapper financialLossMapper;

    @Override
    public Long addFinancialLoss(FinancialLoss financialLoss) {
        financialLoss.setCreateTime(new Date());
        supplement(financialLoss);
        financialLossMapper.insertSelective(financialLoss);
        return financialLoss.getId();
    }

    @Override
    public boolean updateFinancialLoss(FinancialLoss financialLoss) {
        financialLoss.setUpdateTime(new Date());
        supplement(financialLoss);
        int result = financialLossMapper.updateByPrimaryKeySelective(financialLoss);
        if (result > 0)
            return true;
        return false;
    }

    @Override
    public List<FinancialLoss> listForPage(Long projectId,String projectName, String year,
                                           Integer quarter, Integer offset, Integer length) {
        return financialLossMapper.listForPage(projectId,projectName,year,quarter,offset,length);
    }

    @Override
    public Integer listForPageSize(Long projectId,String projectName, String year, Integer quarter) {
        return financialLossMapper.listForPageSize(projectId,projectName,year,quarter);
    }

    public void supplement(FinancialLoss financialLoss){
        if (financialLoss == null)
            return;

        if (financialLoss.getReportTime() != null){
            financialLoss.setQuarter(Utils.getQuarter(financialLoss.getReportTime()));
            financialLoss.setReportYear(DateTimeUtil.getYear(financialLoss.getReportTime())+"");
        }

        //收入小计
        BigDecimal income = BigDecimal.valueOf(0);
        if (financialLoss.getAlreadyPriced() != null && financialLoss.getUnPriced() != null){
            income = financialLoss.getAlreadyPriced().add(financialLoss.getUnPriced());
            financialLoss.setSumPriced(income);
        }

        //成本小计
        BigDecimal cost = BigDecimal.valueOf(0);
        if (financialLoss.getInBookCost() != null && financialLoss.getOutBookCost() != null){
            cost = financialLoss.getInBookCost().add(financialLoss.getOutBookCost());
            financialLoss.setSumBookCost(cost);
        }

        //亏损金额
        BigDecimal lossAmount = BigDecimal.valueOf(0);
        if (income.doubleValue() - cost.doubleValue() < 0){
            lossAmount = cost.subtract(income);
        }
        financialLoss.setLossAmount(lossAmount);

        //财务未确认的亏损
        BigDecimal unConfirmedProfit = null;
        if (financialLoss.getConfirmedNetProfit() != null){
            unConfirmedProfit = lossAmount.subtract(financialLoss.getConfirmedNetProfit());
            financialLoss.setUnConfirmedNetProfit(unConfirmedProfit);
        }

        //应收客户合同工程款
        BigDecimal contractReceivable = null;
        if (financialLoss.getAlreadyPriced() != null && financialLoss.getConfirmPriced() != null){
            contractReceivable = financialLoss.getAlreadyPriced().subtract(financialLoss.getConfirmPriced());
            financialLoss.setContractReceivable(contractReceivable);
        }

        //盈亏小计
        BigDecimal profitLossSubtotal = null;
        if (contractReceivable != null && financialLoss.getPrepayments() != null && financialLoss.getOther() != null){
            profitLossSubtotal = contractReceivable.add(financialLoss.getPrepayments()).add(financialLoss.getOther());
            financialLoss.setProfitLossSubtotal(profitLossSubtotal);
        }

        //账外潜亏
        BigDecimal potentialLoss = null;
        if (unConfirmedProfit != null && profitLossSubtotal != null){
            potentialLoss = unConfirmedProfit.subtract(profitLossSubtotal);
            financialLoss.setPotentialLoss(potentialLoss);
        }

        //盈亏合计
        if (potentialLoss != null && profitLossSubtotal != null){
            financialLoss.setTotalProfitLoss(potentialLoss.add(profitLossSubtotal));
        }

    }
}
