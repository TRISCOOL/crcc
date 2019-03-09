package com.crcc.common.model;

public class LiabilityCostForList {
    private String projectName;
    private String projectType;
    private String projectStatus;
    private String contractStartTime;
    private String contractEndTime;

    private LiabilityCost lastYear;
    private LiabilityCost openTired;
    private LiabilityCost thisYear;

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
