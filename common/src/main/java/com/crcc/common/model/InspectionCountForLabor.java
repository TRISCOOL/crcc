package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class InspectionCountForLabor {
    private Long projectId;
    private Long subcontractorId;
    private Long laborAccountId;
    private String projectName;
    private String subcontractorName;
    private String teamName;
    private Integer valuationPeriodCount;
    private BigDecimal sumValuationPrice;
    private BigDecimal sumValuationPriceReduce;
    private BigDecimal sumWarranty;
    private BigDecimal sumPerformanceBond;
    private BigDecimal sumCompensation;
    private BigDecimal sumShouldAmount;
    private BigDecimal sumEndedPrice;
    private BigDecimal contractPrice;
    private Date newPeriodTime;
    private Integer newPeriodType;
    private BigDecimal sumRate;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSubcontractorId() {
        return subcontractorId;
    }

    public void setSubcontractorId(Long subcontractorId) {
        this.subcontractorId = subcontractorId;
    }

    public Long getLaborAccountId() {
        return laborAccountId;
    }

    public void setLaborAccountId(Long laborAccountId) {
        this.laborAccountId = laborAccountId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSubcontractorName() {
        return subcontractorName;
    }

    public void setSubcontractorName(String subcontractorName) {
        this.subcontractorName = subcontractorName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getValuationPeriodCount() {
        return valuationPeriodCount;
    }

    public void setValuationPeriodCount(Integer valuationPeriodCount) {
        this.valuationPeriodCount = valuationPeriodCount;
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

    public Date getNewPeriodTime() {
        return newPeriodTime;
    }

    public void setNewPeriodTime(Date newPeriodTime) {
        this.newPeriodTime = newPeriodTime;
    }

    public Integer getNewPeriodType() {
        return newPeriodType;
    }

    public void setNewPeriodType(Integer newPeriodType) {
        this.newPeriodType = newPeriodType;
    }

    public BigDecimal getSumRate() {
        return sumRate;
    }

    public void setSumRate(BigDecimal sumRate) {
        this.sumRate = sumRate;
    }
}
