package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class EngineeringChangeMonthly {
    private Long id;

    private Long projectId;

    private Date reportTime;

    private Integer quarter;

    private BigDecimal constructionOutputValue;

    private BigDecimal changeClaimAmount;

    private BigDecimal percentage;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private String remark;

    private BigDecimal constructionOutputValueStatistics;

    private BigDecimal changeClaimAmountStatistics;

    private BigDecimal percentageStatistics;

    private BigDecimal temporarilyPrice;
    private String projectName;
    private String projectType;

    public BigDecimal getTemporarilyPrice() {
        return temporarilyPrice;
    }

    public void setTemporarilyPrice(BigDecimal temporarilyPrice) {
        this.temporarilyPrice = temporarilyPrice;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getPercentageStatistics() {
        return percentageStatistics;
    }

    public void setPercentageStatistics(BigDecimal percentageStatistics) {
        this.percentageStatistics = percentageStatistics;
    }

    public BigDecimal getConstructionOutputValueStatistics() {
        return constructionOutputValueStatistics;
    }

    public void setConstructionOutputValueStatistics(BigDecimal constructionOutputValueStatistics) {
        this.constructionOutputValueStatistics = constructionOutputValueStatistics;
    }

    public BigDecimal getChangeClaimAmountStatistics() {
        return changeClaimAmountStatistics;
    }

    public void setChangeClaimAmountStatistics(BigDecimal changeClaimAmountStatistics) {
        this.changeClaimAmountStatistics = changeClaimAmountStatistics;
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

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public BigDecimal getConstructionOutputValue() {
        return constructionOutputValue;
    }

    public void setConstructionOutputValue(BigDecimal constructionOutputValue) {
        this.constructionOutputValue = constructionOutputValue;
    }

    public BigDecimal getChangeClaimAmount() {
        return changeClaimAmount;
    }

    public void setChangeClaimAmount(BigDecimal changeClaimAmount) {
        this.changeClaimAmount = changeClaimAmount;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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