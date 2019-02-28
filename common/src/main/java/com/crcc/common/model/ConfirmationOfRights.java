package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class ConfirmationOfRights {
    private Long id;

    private Long projectId;

    private String year;

    private String quarter;

    private BigDecimal currentProductionValue;

    private BigDecimal sumHalfOne;

    private BigDecimal halfCompletedValue;

    private BigDecimal oneCompletedValue;

    private BigDecimal changeValue;

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(BigDecimal changeValue) {
        this.changeValue = changeValue;
    }

    private BigDecimal completedValue;

    private BigDecimal inspection;

    private BigDecimal sumFinalPeriod;

    private BigDecimal finalPeriodShould;

    private BigDecimal finalPeriodChange;

    private BigDecimal balanceCompleteValue;

    private BigDecimal balanceInspectionValue;

    private BigDecimal balanceShould;

    private BigDecimal balanceChange;

    private BigDecimal sumBalance;

    private Date reportTime;

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    private Date createTime;
    private Long createUser;

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

    public BigDecimal getSumBalance() {
        return sumBalance;
    }

    public void setSumBalance(BigDecimal sumBalance) {
        this.sumBalance = sumBalance;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    public BigDecimal getCurrentProductionValue() {
        return currentProductionValue;
    }

    public void setCurrentProductionValue(BigDecimal currentProductionValue) {
        this.currentProductionValue = currentProductionValue;
    }

    public BigDecimal getSumHalfOne() {
        return sumHalfOne;
    }

    public void setSumHalfOne(BigDecimal sumHalfOne) {
        this.sumHalfOne = sumHalfOne;
    }

    public BigDecimal getHalfCompletedValue() {
        return halfCompletedValue;
    }

    public void setHalfCompletedValue(BigDecimal halfCompletedValue) {
        this.halfCompletedValue = halfCompletedValue;
    }

    public BigDecimal getOneCompletedValue() {
        return oneCompletedValue;
    }

    public void setOneCompletedValue(BigDecimal oneCompletedValue) {
        this.oneCompletedValue = oneCompletedValue;
    }


    public BigDecimal getCompletedValue() {
        return completedValue;
    }

    public void setCompletedValue(BigDecimal completedValue) {
        this.completedValue = completedValue;
    }

    public BigDecimal getInspection() {
        return inspection;
    }

    public void setInspection(BigDecimal inspection) {
        this.inspection = inspection;
    }

    public BigDecimal getSumFinalPeriod() {
        return sumFinalPeriod;
    }

    public void setSumFinalPeriod(BigDecimal sumFinalPeriod) {
        this.sumFinalPeriod = sumFinalPeriod;
    }

    public BigDecimal getFinalPeriodShould() {
        return finalPeriodShould;
    }

    public void setFinalPeriodShould(BigDecimal finalPeriodShould) {
        this.finalPeriodShould = finalPeriodShould;
    }

    public BigDecimal getFinalPeriodChange() {
        return finalPeriodChange;
    }

    public void setFinalPeriodChange(BigDecimal finalPeriodChange) {
        this.finalPeriodChange = finalPeriodChange;
    }

    public BigDecimal getBalanceCompleteValue() {
        return balanceCompleteValue;
    }

    public void setBalanceCompleteValue(BigDecimal balanceCompleteValue) {
        this.balanceCompleteValue = balanceCompleteValue;
    }

    public BigDecimal getBalanceInspectionValue() {
        return balanceInspectionValue;
    }

    public void setBalanceInspectionValue(BigDecimal balanceInspectionValue) {
        this.balanceInspectionValue = balanceInspectionValue;
    }

    public BigDecimal getBalanceShould() {
        return balanceShould;
    }

    public void setBalanceShould(BigDecimal balanceShould) {
        this.balanceShould = balanceShould;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
    }
}