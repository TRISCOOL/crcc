package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class LaborAccount {
    private Long id;

    private Long projectId;

    private Long subcontractorId;

    private String subcontractorName;

    private Date contractTime;

    private String constructionScope;

    private String contractCode;

    private String teamName;

    private BigDecimal estimatedContractAmount;

    private BigDecimal shouldAmount;

    private BigDecimal realAmount;

    private String contractPerson;

    private String phone;

    private String annexUrl;

    private String remark;

    private Date teamTime;

    private Integer teamQualification;

    private String teamAnnex;

    private Date approvalTime;

    private Integer approvalFiling;

    private String approvalAnnex;

    private Date settlementTime;

    private String settlementRemark;

    private BigDecimal settlementAmount;

    private Integer settlementFiling;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private Integer contractType;

    private Integer status;

    private String projectName;

    private String createUserStr;
    private String updateUserStr;

    private String contractNumber;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCreateUserStr() {
        return createUserStr;
    }

    public void setCreateUserStr(String createUserStr) {
        this.createUserStr = createUserStr;
    }

    public String getUpdateUserStr() {
        return updateUserStr;
    }

    public void setUpdateUserStr(String updateUserStr) {
        this.updateUserStr = updateUserStr;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getSubcontractorId() {
        return subcontractorId;
    }

    public void setSubcontractorId(Long subcontractorId) {
        this.subcontractorId = subcontractorId;
    }

    public String getSubcontractorName() {
        return subcontractorName;
    }

    public void setSubcontractorName(String subcontractorName) {
        this.subcontractorName = subcontractorName == null ? null : subcontractorName.trim();
    }

    public Date getContractTime() {
        return contractTime;
    }

    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    public String getConstructionScope() {
        return constructionScope;
    }

    public void setConstructionScope(String constructionScope) {
        this.constructionScope = constructionScope == null ? null : constructionScope.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public BigDecimal getEstimatedContractAmount() {
        return estimatedContractAmount;
    }

    public void setEstimatedContractAmount(BigDecimal estimatedContractAmount) {
        this.estimatedContractAmount = estimatedContractAmount;
    }

    public BigDecimal getShouldAmount() {
        return shouldAmount;
    }

    public void setShouldAmount(BigDecimal shouldAmount) {
        this.shouldAmount = shouldAmount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson == null ? null : contractPerson.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl == null ? null : annexUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getTeamTime() {
        return teamTime;
    }

    public void setTeamTime(Date teamTime) {
        this.teamTime = teamTime;
    }

    public Integer getTeamQualification() {
        return teamQualification;
    }

    public void setTeamQualification(Integer teamQualification) {
        this.teamQualification = teamQualification;
    }

    public String getTeamAnnex() {
        return teamAnnex;
    }

    public void setTeamAnnex(String teamAnnex) {
        this.teamAnnex = teamAnnex == null ? null : teamAnnex.trim();
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public Integer getApprovalFiling() {
        return approvalFiling;
    }

    public void setApprovalFiling(Integer approvalFiling) {
        this.approvalFiling = approvalFiling;
    }

    public String getApprovalAnnex() {
        return approvalAnnex;
    }

    public void setApprovalAnnex(String approvalAnnex) {
        this.approvalAnnex = approvalAnnex == null ? null : approvalAnnex.trim();
    }

    public Date getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getSettlementRemark() {
        return settlementRemark;
    }

    public void setSettlementRemark(String settlementRemark) {
        this.settlementRemark = settlementRemark == null ? null : settlementRemark.trim();
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public Integer getSettlementFiling() {
        return settlementFiling;
    }

    public void setSettlementFiling(Integer settlementFiling) {
        this.settlementFiling = settlementFiling;
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
}