package com.crcc.common.model;

import java.math.BigDecimal;

public class InspectionAccountTotal {
    private BigDecimal sumPrice; //
    private BigDecimal sumEndPrice; //已完未计
    private BigDecimal sumValuationPrice; //计价总金额
    private BigDecimal sumValuationPriceReduce; //扣款
    private BigDecimal sumWarranty; //扣除质保金
    private BigDecimal sumPerformanceBond; //扣除履约保证金
    private BigDecimal sumShouldAmount; //应支付金额
    private BigDecimal sumCompensation; //计日工及补偿费用
    private BigDecimal percentage; //under_rate

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getSumEndPrice() {
        return sumEndPrice;
    }

    public void setSumEndPrice(BigDecimal sumEndPrice) {
        this.sumEndPrice = sumEndPrice;
    }

    public BigDecimal getSumValuationPrice() {
        return sumValuationPrice;
    }

    public void setSumValuationPrice(BigDecimal sumValuationPrice) {
        this.sumValuationPrice = sumValuationPrice;
    }

    public BigDecimal getSumValuationPriceReduce() {
        return sumValuationPriceReduce;
    }

    public void setSumValuationPriceReduce(BigDecimal sumValuationPriceReduce) {
        this.sumValuationPriceReduce = sumValuationPriceReduce;
    }

    public BigDecimal getSumWarranty() {
        return sumWarranty;
    }

    public void setSumWarranty(BigDecimal sumWarranty) {
        this.sumWarranty = sumWarranty;
    }

    public BigDecimal getSumPerformanceBond() {
        return sumPerformanceBond;
    }

    public void setSumPerformanceBond(BigDecimal sumPerformanceBond) {
        this.sumPerformanceBond = sumPerformanceBond;
    }

    public BigDecimal getSumShouldAmount() {
        return sumShouldAmount;
    }

    public void setSumShouldAmount(BigDecimal sumShouldAmount) {
        this.sumShouldAmount = sumShouldAmount;
    }

    public BigDecimal getSumCompensation() {
        return sumCompensation;
    }

    public void setSumCompensation(BigDecimal sumCompensation) {
        this.sumCompensation = sumCompensation;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
