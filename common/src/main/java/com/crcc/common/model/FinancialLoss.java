package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class FinancialLoss {
    private Long id;

    private Long projectId;

    private Date reportTime;

    private String reportYear;

    private Integer quarter;

    private BigDecimal alreadyPriced;

    private BigDecimal unPriced;

    private BigDecimal sumPriced;

    private BigDecimal confirmPriced;

    private BigDecimal inBookCost;

    private BigDecimal outBookCost;

    private BigDecimal sumBookCost;

    private BigDecimal lossAmount;

    private BigDecimal confirmedNetProfit;

    private BigDecimal unConfirmedNetProfit;

    private BigDecimal lossRatio;

    private BigDecimal contractReceivable;

    private BigDecimal prepayments;

    private BigDecimal other;

    private BigDecimal profitLossSubtotal;

    private BigDecimal potentialLoss;

    private BigDecimal totalProfitLoss;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear == null ? null : reportYear.trim();
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public BigDecimal getAlreadyPriced() {
        return alreadyPriced;
    }

    public void setAlreadyPriced(BigDecimal alreadyPriced) {
        this.alreadyPriced = alreadyPriced;
    }

    public BigDecimal getUnPriced() {
        return unPriced;
    }

    public void setUnPriced(BigDecimal unPriced) {
        this.unPriced = unPriced;
    }

    public BigDecimal getSumPriced() {
        return sumPriced;
    }

    public void setSumPriced(BigDecimal sumPriced) {
        this.sumPriced = sumPriced;
    }

    public BigDecimal getConfirmPriced() {
        return confirmPriced;
    }

    public void setConfirmPriced(BigDecimal confirmPriced) {
        this.confirmPriced = confirmPriced;
    }

    public BigDecimal getInBookCost() {
        return inBookCost;
    }

    public void setInBookCost(BigDecimal inBookCost) {
        this.inBookCost = inBookCost;
    }

    public BigDecimal getOutBookCost() {
        return outBookCost;
    }

    public void setOutBookCost(BigDecimal outBookCost) {
        this.outBookCost = outBookCost;
    }

    public BigDecimal getSumBookCost() {
        return sumBookCost;
    }

    public void setSumBookCost(BigDecimal sumBookCost) {
        this.sumBookCost = sumBookCost;
    }

    public BigDecimal getLossAmount() {
        return lossAmount;
    }

    public void setLossAmount(BigDecimal lossAmount) {
        this.lossAmount = lossAmount;
    }

    public BigDecimal getConfirmedNetProfit() {
        return confirmedNetProfit;
    }

    public void setConfirmedNetProfit(BigDecimal confirmedNetProfit) {
        this.confirmedNetProfit = confirmedNetProfit;
    }

    public BigDecimal getUnConfirmedNetProfit() {
        return unConfirmedNetProfit;
    }

    public void setUnConfirmedNetProfit(BigDecimal unConfirmedNetProfit) {
        this.unConfirmedNetProfit = unConfirmedNetProfit;
    }

    public BigDecimal getLossRatio() {
        return lossRatio;
    }

    public void setLossRatio(BigDecimal lossRatio) {
        this.lossRatio = lossRatio;
    }

    public BigDecimal getContractReceivable() {
        return contractReceivable;
    }

    public void setContractReceivable(BigDecimal contractReceivable) {
        this.contractReceivable = contractReceivable;
    }

    public BigDecimal getPrepayments() {
        return prepayments;
    }

    public void setPrepayments(BigDecimal prepayments) {
        this.prepayments = prepayments;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getProfitLossSubtotal() {
        return profitLossSubtotal;
    }

    public void setProfitLossSubtotal(BigDecimal profitLossSubtotal) {
        this.profitLossSubtotal = profitLossSubtotal;
    }

    public BigDecimal getPotentialLoss() {
        return potentialLoss;
    }

    public void setPotentialLoss(BigDecimal potentialLoss) {
        this.potentialLoss = potentialLoss;
    }

    public BigDecimal getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(BigDecimal totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}