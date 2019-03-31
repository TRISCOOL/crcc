package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class LiabilityCostForList {
    private String projectName;
    private String projectType;
    private String projectStatus;
    private String contractStartTime;
    private String contractEndTime;
    private BigDecimal contractPrice;
    private Date reportTime;
    private Long id;
    private Long projectId;

    private LiabilityCost lastYear;
    private LiabilityCost openTired;
    private LiabilityCost thisYear;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
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

    public LiabilityCost getLastYear() {
        return lastYear;
    }

    public void setLastYear(LiabilityCost lastYear) {
        this.lastYear = lastYear;
    }

    public LiabilityCost getOpenTired() {
        return openTired;
    }

    public void setOpenTired(LiabilityCost openTired) {
        this.openTired = openTired;
    }

    public LiabilityCost getThisYear() {
        return thisYear;
    }

    public void setThisYear(LiabilityCost thisYear) {
        this.thisYear = thisYear;
    }
}
