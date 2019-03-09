package com.crcc.common.service.impl;

import com.crcc.common.mapper.FinancialLossMapper;
import com.crcc.common.model.FinancialLoss;
import com.crcc.common.model.FinancialLossTotal;
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

    @Override
    public FinancialLossTotal getTotal(Long projectId, String projectName, String year, Integer quarter) {
        List<FinancialLoss> financialLosses = listForPage(projectId,projectName,year,quarter,null,null);
        if (financialLosses != null && financialLosses.size() > 0){
            FinancialLossTotal financialLossTotal = new FinancialLossTotal();
            BigDecimal sumTemporarilyPrice = new BigDecimal("0");
            BigDecimal sumAlreadyPriced = new BigDecimal("0");
            BigDecimal sumUnPriced = new BigDecimal("0");
            BigDecimal sumSumPriced = new BigDecimal("0");
            BigDecimal sumConfirmPriced = new BigDecimal("0");
            BigDecimal sumInBookCost = new BigDecimal("0");
            BigDecimal sumOutBookCost = new BigDecimal("0");
            BigDecimal sumSumBookCost = new BigDecimal("0");
            BigDecimal sumLossAmount = new BigDecimal("0");
            BigDecimal sumConfirmedNetProfit = new BigDecimal("0");
            BigDecimal sumUnConfirmedNetProfit = new BigDecimal("0");
            BigDecimal sumLossRatio = new BigDecimal("0");
            BigDecimal sumContractReceivable = new BigDecimal("0");
            BigDecimal sumProfitLossSubtotal = new BigDecimal("0");
            BigDecimal sumPotentialLoss = new BigDecimal("0");
            BigDecimal sumTotalProfitLoss = new BigDecimal("0");
            for (FinancialLoss financialLoss : financialLosses){
                sumTemporarilyPrice = Utils.addBigDecimal(sumTemporarilyPrice,financialLoss.getTemporarilyPrice());
                sumAlreadyPriced = Utils.addBigDecimal(sumAlreadyPriced,financialLoss.getAlreadyPriced());
                sumUnPriced = Utils.addBigDecimal(sumUnPriced,financialLoss.getUnPriced());
                sumSumPriced = Utils.addBigDecimal(sumSumPriced,financialLoss.getSumPriced());
                sumConfirmPriced = Utils.addBigDecimal(sumConfirmPriced,financialLoss.getConfirmPriced());
                sumInBookCost = Utils.addBigDecimal(sumInBookCost,financialLoss.getInBookCost());
                sumOutBookCost = Utils.addBigDecimal(sumOutBookCost,financialLoss.getOutBookCost());
                sumSumBookCost = Utils.addBigDecimal(sumSumBookCost,financialLoss.getSumBookCost());
                sumLossAmount = Utils.addBigDecimal(sumLossAmount,financialLoss.getLossAmount());
                sumConfirmedNetProfit = Utils.addBigDecimal(sumConfirmedNetProfit,financialLoss.getConfirmedNetProfit());
                sumUnConfirmedNetProfit = Utils.addBigDecimal(sumUnConfirmedNetProfit,financialLoss.getUnConfirmedNetProfit());
                sumLossRatio = Utils.addBigDecimal(sumLossRatio,financialLoss.getLossRatio());
                sumContractReceivable = Utils.addBigDecimal(sumContractReceivable,financialLoss.getContractReceivable());
                sumProfitLossSubtotal = Utils.addBigDecimal(sumProfitLossSubtotal,financialLoss.getProfitLossSubtotal());
                sumPotentialLoss = Utils.addBigDecimal(sumPotentialLoss,financialLoss.getPotentialLoss());
                sumTotalProfitLoss = Utils.addBigDecimal(sumTotalProfitLoss,financialLoss.getTotalProfitLoss());
            }
            financialLossTotal.setSumTemporarilyPrice(sumTemporarilyPrice);
            financialLossTotal.setSumAlreadyPriced(sumAlreadyPriced);
            financialLossTotal.setSumConfirmedNetProfit(sumConfirmedNetProfit);
            financialLossTotal.setSumConfirmPriced(sumConfirmPriced);
            financialLossTotal.setSumInBookCost(sumInBookCost);
            financialLossTotal.setSumLossAmount(sumLossAmount);
            financialLossTotal.setSumLossRatio(sumLossRatio);
            financialLossTotal.setSumOutBookCost(sumOutBookCost);
            financialLossTotal.setSumSumBookCost(sumSumBookCost);
            financialLossTotal.setSumSumPriced(sumSumPriced);
            financialLossTotal.setSumUnConfirmedNetProfit(sumUnConfirmedNetProfit);
            financialLossTotal.setSumUnPriced(sumUnPriced);
            financialLossTotal.setSumProfitLossSubtotal(sumProfitLossSubtotal);
            financialLossTotal.setSumPotentialLoss(sumPotentialLoss);
            financialLossTotal.setSumTotalProfitLoss(sumTotalProfitLoss);
            return financialLossTotal;
        }
        return null;
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
