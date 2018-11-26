package com.crcc.common.model;

import java.util.Date;

public class SubcontractorResume {
    private Long id;

    private Long subcontractorId;

    private Date startTime;

    private Date endTime;

    private Long projectId;

    private String projectEvaluation;

    private String projectDescription;

    private String companyEvaluation;

    private String groupEvaluation;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private Date createTime;

    private Long laborAccountId;

    private String constructionScale;

    private String teamName;

    private String projectName;

    private String subcontractorName;

    private String contractPerson;

    private String phone;

    private Double contractAmount;
    private Double settlementAmount;

    private String subcontractorCode;

    public String getSubcontractorCode() {
        return subcontractorCode;
    }

    public void setSubcontractorCode(String subcontractorCode) {
        this.subcontractorCode = subcontractorCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubcontractorId() {
        return subcontractorId;
    }

    public void setSubcontractorId(Long subcontractorId) {
        this.subcontractorId = subcontractorId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectEvaluation() {
        return projectEvaluation;
    }

    public void setProjectEvaluation(String projectEvaluation) {
        this.projectEvaluation = projectEvaluation == null ? null : projectEvaluation.trim();
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription == null ? null : projectDescription.trim();
    }

    public String getCompanyEvaluation() {
        return companyEvaluation;
    }

    public void setCompanyEvaluation(String companyEvaluation) {
        this.companyEvaluation = companyEvaluation == null ? null : companyEvaluation.trim();
    }

    public String getGroupEvaluation() {
        return groupEvaluation;
    }

    public void setGroupEvaluation(String groupEvaluation) {
        this.groupEvaluation = groupEvaluation == null ? null : groupEvaluation.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getLaborAccountId() {
        return laborAccountId;
    }

    public void setLaborAccountId(Long laborAccountId) {
        this.laborAccountId = laborAccountId;
    }

    public String getConstructionScale() {
        return constructionScale;
    }

    public void setConstructionScale(String constructionScale) {
        this.constructionScale = constructionScale == null ? null : constructionScale.trim();
    }
}