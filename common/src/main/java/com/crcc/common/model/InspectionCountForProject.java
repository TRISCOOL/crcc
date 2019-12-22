package com.crcc.common.model;

import java.math.BigDecimal;

public class InspectionCountForProject {
    private Long projectId;
    private String projectName;
    private BigDecimal sumValuationPrice;
    private BigDecimal sumValuationPriceReduce;
    private BigDecimal sumWarranty;
    private BigDecimal sumPerformanceBond;
    private BigDecimal sumCompensation;
    private BigDecimal sumShouldAmount;
    private BigDecimal sumEndedPrice;
    private BigDecimal contractPrice;
    private BigDecimal sumRate;

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

    public BigDecimal getSumCompensation() {
        return sumCompensation;
    }

    public void setSumCompensation(BigDecimal sumCompensation) {
        this.sumCompensation = sumCompensation;
    }

    public BigDecimal getSumShouldAmount() {
        return sumShouldAmount;
    }

    public void setSumShouldAmount(BigDecimal sumShouldAmount) {
        this.sumShouldAmount = sumShouldAmount;
    }

    public BigDecimal getSumEndedPrice() {
        return sumEndedPrice;
    }

    public void setSumEndedPrice(BigDecimal sumEndedPrice) {
        this.sumEndedPrice = sumEndedPrice;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getSumRate() {
        return sumRate;
    }

    public void setSumRate(BigDecimal sumRate) {
        this.sumRate = sumRate;
    }
}
