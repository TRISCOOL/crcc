package com.crcc.common.model;

import java.math.BigDecimal;

public class FinancialLossTotal {
    private BigDecimal sumTemporarilyPrice;
    private BigDecimal sumAlreadyPriced;
    private BigDecimal sumUnPriced;
    private BigDecimal sumSumPriced;
    private BigDecimal sumConfirmPriced;
    private BigDecimal sumInBookCost;
    private BigDecimal sumOutBookCost;
    private BigDecimal sumSumBookCost;
    private BigDecimal sumLossAmount;
    private BigDecimal sumConfirmedNetProfit;
    private BigDecimal sumUnConfirmedNetProfit;
    private BigDecimal sumLossRatio;
    private BigDecimal sumProfitLossSubtotal;
    private BigDecimal sumPotentialLoss;
    private BigDecimal sumTotalProfitLoss;

    public BigDecimal getSumProfitLossSubtotal() {
        return sumProfitLossSubtotal;
    }

    public void setSumProfitLossSubtotal(BigDecimal sumProfitLossSubtotal) {
        this.sumProfitLossSubtotal = sumProfitLossSubtotal;
    }

    public BigDecimal getSumPotentialLoss() {
        return sumPotentialLoss;
    }

    public void setSumPotentialLoss(BigDecimal sumPotentialLoss) {
        this.sumPotentialLoss = sumPotentialLoss;
    }

    public BigDecimal getSumTotalProfitLoss() {
        return sumTotalProfitLoss;
    }

    public void setSumTotalProfitLoss(BigDecimal sumTotalProfitLoss) {
        this.sumTotalProfitLoss = sumTotalProfitLoss;
    }

    public BigDecimal getSumContractReceivable() {
        return sumContractReceivable;
    }

    public void setSumContractReceivable(BigDecimal sumContractReceivable) {
        this.sumContractReceivable = sumContractReceivable;
    }

    private BigDecimal sumContractReceivable;

    public BigDecimal getSumTemporarilyPrice() {
        return sumTemporarilyPrice;
    }

    public void setSumTemporarilyPrice(BigDecimal sumTemporarilyPrice) {
        this.sumTemporarilyPrice = sumTemporarilyPrice;
    }

    public BigDecimal getSumAlreadyPriced() {
        return sumAlreadyPriced;
    }

    public void setSumAlreadyPriced(BigDecimal sumAlreadyPriced) {
        this.sumAlreadyPriced = sumAlreadyPriced;
    }

    public BigDecimal getSumUnPriced() {
        return sumUnPriced;
    }

    public void setSumUnPriced(BigDecimal sumUnPriced) {
        this.sumUnPriced = sumUnPriced;
    }

    public BigDecimal getSumSumPriced() {
        return sumSumPriced;
    }

    public void setSumSumPriced(BigDecimal sumSumPriced) {
        this.sumSumPriced = sumSumPriced;
    }

    public BigDecimal getSumConfirmPriced() {
        return sumConfirmPriced;
    }

    public void setSumConfirmPriced(BigDecimal sumConfirmPriced) {
        this.sumConfirmPriced = sumConfirmPriced;
    }

    public BigDecimal getSumInBookCost() {
        return sumInBookCost;
    }

    public void setSumInBookCost(BigDecimal sumInBookCost) {
        this.sumInBookCost = sumInBookCost;
    }

    public BigDecimal getSumOutBookCost() {
        return sumOutBookCost;
    }

    public void setSumOutBookCost(BigDecimal sumOutBookCost) {
        this.sumOutBookCost = sumOutBookCost;
    }

    public BigDecimal getSumSumBookCost() {
        return sumSumBookCost;
    }

    public void setSumSumBookCost(BigDecimal sumSumBookCost) {
        this.sumSumBookCost = sumSumBookCost;
    }

    public BigDecimal getSumLossAmount() {
        return sumLossAmount;
    }

    public void setSumLossAmount(BigDecimal sumLossAmount) {
        this.sumLossAmount = sumLossAmount;
    }

    public BigDecimal getSumConfirmedNetProfit() {
        return sumConfirmedNetProfit;
    }

    public void setSumConfirmedNetProfit(BigDecimal sumConfirmedNetProfit) {
        this.sumConfirmedNetProfit = sumConfirmedNetProfit;
    }

    public BigDecimal getSumUnConfirmedNetProfit() {
        return sumUnConfirmedNetProfit;
    }

    public void setSumUnConfirmedNetProfit(BigDecimal sumUnConfirmedNetProfit) {
        this.sumUnConfirmedNetProfit = sumUnConfirmedNetProfit;
    }

    public BigDecimal getSumLossRatio() {
        return sumLossRatio;
    }

    public void setSumLossRatio(BigDecimal sumLossRatio) {
        this.sumLossRatio = sumLossRatio;
    }
}
