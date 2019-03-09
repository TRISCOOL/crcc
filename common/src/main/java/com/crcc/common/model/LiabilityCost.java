package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class LiabilityCost {
    private Long id;

    private Long projectId;

    private Date reportTime;

    private BigDecimal contractPrice;

    private BigDecimal constructionOutputValue;

    private BigDecimal advancePricing;

    private BigDecimal completedUncalculated;

    private BigDecimal shouldPrice;

    private BigDecimal ownerTotal;

    private BigDecimal sumOwnerTotal;

    private BigDecimal budget;

    private BigDecimal actualSum;

    private BigDecimal actualManage;

    private BigDecimal confirmPrice;

    private BigDecimal comprehensiveIncome;

    private BigDecimal comprehensiveIncomePercentage;

    private BigDecimal costSuperPercentage;

    private BigDecimal productionValuePercentage;

    private BigDecimal managementPercentage;

    private BigDecimal unconfirmPrice;

    private BigDecimal shouldAppropriation;

    private BigDecimal realAppropriation;

    private BigDecimal inPlacePercentage;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private String remark;

    private String projectName;
    private String projectType;
    private String projectStatus;
    private String contractStartTime;
    private String contractEndTime;

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

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(String contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public String getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(String contractEndTime) {
        this.contractEndTime = contractEndTime;
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

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getConstructionOutputValue() {
        return constructionOutputValue;
    }

    public void setConstructionOutputValue(BigDecimal constructionOutputValue) {
        this.constructionOutputValue = constructionOutputValue;
    }

    public BigDecimal getAdvancePricing() {
        return advancePricing;
    }

    public void setAdvancePricing(BigDecimal advancePricing) {
        this.advancePricing = advancePricing;
    }

    public BigDecimal getCompletedUncalculated() {
        return completedUncalculated;
    }

    public void setCompletedUncalculated(BigDecimal completedUncalculated) {
        this.completedUncalculated = completedUncalculated;
    }

    public BigDecimal getShouldPrice() {
        return shouldPrice;
    }

    public void setShouldPrice(BigDecimal shouldPrice) {
        this.shouldPrice = shouldPrice;
    }

    public BigDecimal getOwnerTotal() {
        return ownerTotal;
    }

    public void setOwnerTotal(BigDecimal ownerTotal) {
        this.ownerTotal = ownerTotal;
    }

    public BigDecimal getSumOwnerTotal() {
        return sumOwnerTotal;
    }

    public void setSumOwnerTotal(BigDecimal sumOwnerTotal) {
        this.sumOwnerTotal = sumOwnerTotal;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getActualSum() {
        return actualSum;
    }

    public void setActualSum(BigDecimal actualSum) {
        this.actualSum = actualSum;
    }

    public BigDecimal getActualManage() {
        return actualManage;
    }

    public void setActualManage(BigDecimal actualManage) {
        this.actualManage = actualManage;
    }

    public BigDecimal getConfirmPrice() {
        return confirmPrice;
    }

    public void setConfirmPrice(BigDecimal confirmPrice) {
        this.confirmPrice = confirmPrice;
    }

    public BigDecimal getComprehensiveIncome() {
        return comprehensiveIncome;
    }

    public void setComprehensiveIncome(BigDecimal comprehensiveIncome) {
        this.comprehensiveIncome = comprehensiveIncome;
    }

    public BigDecimal getComprehensiveIncomePercentage() {
        return comprehensiveIncomePercentage;
    }

    public void setComprehensiveIncomePercentage(BigDecimal comprehensiveIncomePercentage) {
        this.comprehensiveIncomePercentage = comprehensiveIncomePercentage;
    }

    public BigDecimal getCostSuperPercentage() {
        return costSuperPercentage;
    }

    public void setCostSuperPercentage(BigDecimal costSuperPercentage) {
        this.costSuperPercentage = costSuperPercentage;
    }

    public BigDecimal getProductionValuePercentage() {
        return productionValuePercentage;
    }

    public void setProductionValuePercentage(BigDecimal productionValuePercentage) {
        this.productionValuePercentage = productionValuePercentage;
    }

    public BigDecimal getManagementPercentage() {
        return managementPercentage;
    }

    public void setManagementPercentage(BigDecimal managementPercentage) {
        this.managementPercentage = managementPercentage;
    }

    public BigDecimal getUnconfirmPrice() {
        return unconfirmPrice;
    }

    public void setUnconfirmPrice(BigDecimal unconfirmPrice) {
        this.unconfirmPrice = unconfirmPrice;
    }

    public BigDecimal getShouldAppropriation() {
        return shouldAppropriation;
    }

    public void setShouldAppropriation(BigDecimal shouldAppropriation) {
        this.shouldAppropriation = shouldAppropriation;
    }

    public BigDecimal getRealAppropriation() {
        return realAppropriation;
    }

    public void setRealAppropriation(BigDecimal realAppropriation) {
        this.realAppropriation = realAppropriation;
    }

    public BigDecimal getInPlacePercentage() {
        return inPlacePercentage;
    }

    public void setInPlacePercentage(BigDecimal inPlacePercentage) {
        this.inPlacePercentage = inPlacePercentage;
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