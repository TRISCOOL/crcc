package com.crcc.common.model;

import java.math.BigDecimal;

public class MeteringAccountTotal {
    private BigDecimal sumAlreadyAmount; //已经支付金额
    private BigDecimal sumRealTaxAmount; //实际含税金额
    private BigDecimal sumTaxAmount; //计价含税金额
    private BigDecimal sumEndAmount; //已完未计金额
    private BigDecimal sumExtraAmount; //超计价金额

    private BigDecimal sumPrepaymentAmount; //预付金额
    private BigDecimal sumAmountNotTax; //计价金额不含税
    private BigDecimal sumRealAmount; //实际不含税金额
    private BigDecimal sumUnpaidAmount; //未支付金额
    private BigDecimal percentagePayProportion; //支付比例
    private BigDecimal percentageProductionValue; //产值计价率

    public BigDecimal getPercentagePayProportion() {
        return percentagePayProportion;
    }

    public void setPercentagePayProportion(BigDecimal percentagePayProportion) {
        this.percentagePayProportion = percentagePayProportion;
    }

    public BigDecimal getPercentageProductionValue() {
        return percentageProductionValue;
    }

    public void setPercentageProductionValue(BigDecimal percentageProductionValue) {
        this.percentageProductionValue = percentageProductionValue;
    }

    public BigDecimal getSumAlreadyAmount() {
        return sumAlreadyAmount;
    }

    public void setSumAlreadyAmount(BigDecimal sumAlreadyAmount) {
        this.sumAlreadyAmount = sumAlreadyAmount;
    }

    public BigDecimal getSumRealTaxAmount() {
        return sumRealTaxAmount;
    }

    public void setSumRealTaxAmount(BigDecimal sumRealTaxAmount) {
        this.sumRealTaxAmount = sumRealTaxAmount;
    }

    public BigDecimal getSumTaxAmount() {
        return sumTaxAmount;
    }

    public void setSumTaxAmount(BigDecimal sumTaxAmount) {
        this.sumTaxAmount = sumTaxAmount;
    }

    public BigDecimal getSumEndAmount() {
        return sumEndAmount;
    }

    public void setSumEndAmount(BigDecimal sumEndAmount) {
        this.sumEndAmount = sumEndAmount;
    }

    public BigDecimal getSumExtraAmount() {
        return sumExtraAmount;
    }

    public void setSumExtraAmount(BigDecimal sumExtraAmount) {
        this.sumExtraAmount = sumExtraAmount;
    }

    public BigDecimal getSumPrepaymentAmount() {
        return sumPrepaymentAmount;
    }

    public void setSumPrepaymentAmount(BigDecimal sumPrepaymentAmount) {
        this.sumPrepaymentAmount = sumPrepaymentAmount;
    }

    public BigDecimal getSumAmountNotTax() {
        return sumAmountNotTax;
    }

    public void setSumAmountNotTax(BigDecimal sumAmountNotTax) {
        this.sumAmountNotTax = sumAmountNotTax;
    }

    public BigDecimal getSumRealAmount() {
        return sumRealAmount;
    }

    public void setSumRealAmount(BigDecimal sumRealAmount) {
        this.sumRealAmount = sumRealAmount;
    }

    public BigDecimal getSumUnpaidAmount() {
        return sumUnpaidAmount;
    }

    public void setSumUnpaidAmount(BigDecimal sumUnpaidAmount) {
        this.sumUnpaidAmount = sumUnpaidAmount;
    }
}
