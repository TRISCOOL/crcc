package com.crcc.common.model;

import java.math.BigDecimal;

public class MeteringAccountForProjectCount {
    private Long projectId;
    private String projectName;
    private BigDecimal sumPrepaymentAmount;
    private BigDecimal sumValuationAmountTax;
    private BigDecimal valuationAmountNotTax;
    private BigDecimal tax;
    private BigDecimal sumRealAmountTax;
    private BigDecimal sumRealAmount;
    private BigDecimal sumAlreadyPaidAmount;
    private BigDecimal sumUnpaidAmount;
    private BigDecimal sumExtraAmount;
    private BigDecimal sumNotCalculatedAmount;
    private BigDecimal payProportion;
    private BigDecimal productionValue;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getSumPrepaymentAmount() {
        return sumPrepaymentAmount;
    }

    public void setSumPrepaymentAmount(BigDecimal sumPrepaymentAmount) {
        this.sumPrepaymentAmount = sumPrepaymentAmount;
    }

    public BigDecimal getSumValuationAmountTax() {
        return sumValuationAmountTax;
    }

    public void setSumValuationAmountTax(BigDecimal sumValuationAmountTax) {
        this.sumValuationAmountTax = sumValuationAmountTax;
    }

    public BigDecimal getValuationAmountNotTax() {
        return valuationAmountNotTax;
    }

    public void setValuationAmountNotTax(BigDecimal valuationAmountNotTax) {
        this.valuationAmountNotTax = valuationAmountNotTax;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getSumRealAmountTax() {
        return sumRealAmountTax;
    }

    public void setSumRealAmountTax(BigDecimal sumRealAmountTax) {
        this.sumRealAmountTax = sumRealAmountTax;
    }

    public BigDecimal getSumRealAmount() {
        return sumRealAmount;
    }

    public void setSumRealAmount(BigDecimal sumRealAmount) {
        this.sumRealAmount = sumRealAmount;
    }

    public BigDecimal getSumAlreadyPaidAmount() {
        return sumAlreadyPaidAmount;
    }

    public void setSumAlreadyPaidAmount(BigDecimal sumAlreadyPaidAmount) {
        this.sumAlreadyPaidAmount = sumAlreadyPaidAmount;
    }

    public BigDecimal getSumUnpaidAmount() {
        return sumUnpaidAmount;
    }

    public void setSumUnpaidAmount(BigDecimal sumUnpaidAmount) {
        this.sumUnpaidAmount = sumUnpaidAmount;
    }

    public BigDecimal getSumExtraAmount() {
        return sumExtraAmount;
    }

    public void setSumExtraAmount(BigDecimal sumExtraAmount) {
        this.sumExtraAmount = sumExtraAmount;
    }

    public BigDecimal getSumNotCalculatedAmount() {
        return sumNotCalculatedAmount;
    }

    public void setSumNotCalculatedAmount(BigDecimal sumNotCalculatedAmount) {
        this.sumNotCalculatedAmount = sumNotCalculatedAmount;
    }

    public BigDecimal getPayProportion() {
        return payProportion;
    }

    public void setPayProportion(BigDecimal payProportion) {
        this.payProportion = payProportion;
    }

    public BigDecimal getProductionValue() {
        return productionValue;
    }

    public void setProductionValue(BigDecimal productionValue) {
        this.productionValue = productionValue;
    }
}
