package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProjectEvaluation {
    private Long id;

    private Long projectId;

    private String engineeringType;

    private String evaluationStatus;

    private String projectStatus;

    private BigDecimal winningBid;

    private BigDecimal effectiveIncome;

    private String isSign;

    private Date signTime;

    private Date contractStartTime;

    private Date contractEndTime;

    private Integer duration;

    private Date evaluationTime;

    private BigDecimal evaluationBenefit;

    private BigDecimal evaluationCost;

    private String evaluationCode;

    private String evaluationAnnex;

    private BigDecimal jointHearingBenefit;

    private BigDecimal jointHearingCost;

    private Date jointHearingTime;

    private String jointHearingAnnex;

    private BigDecimal responsibilityBenefiy;

    private Date responsibilityTime;

    private String responsibilityPeople;

    private String responsibilitySecretary;

    private String responsibilityAnnex;

    private Date createTime;
    private Long createUser;
    private Date updateTime;
    private Long updateUser;

    private String createUserStr;
    private String updateUserStr;

    private String projectName;

    private String projectStatusStr;

    private String engineeringStatus;

    public String getEngineeringStatus() {
        return engineeringStatus;
    }

    public void setEngineeringStatus(String engineeringStatus) {
        this.engineeringStatus = engineeringStatus;
    }

    public String getProjectStatusStr() {
        return projectStatusStr;
    }

    public void setProjectStatusStr(String projectStatusStr) {
        this.projectStatusStr = projectStatusStr;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    private Double settlementAmount;

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

    public String getEngineeringType() {
        return engineeringType;
    }

    public void setEngineeringType(String engineeringType) {
        this.engineeringType = engineeringType == null ? null : engineeringType.trim();
    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus == null ? null : evaluationStatus.trim();
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus == null ? null : projectStatus.trim();
    }

    public BigDecimal getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(BigDecimal winningBid) {
        this.winningBid = winningBid;
    }

    public BigDecimal getEffectiveIncome() {
        return effectiveIncome;
    }

    public void setEffectiveIncome(BigDecimal effectiveIncome) {
        this.effectiveIncome = effectiveIncome;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign == null ? null : isSign.trim();
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public BigDecimal getEvaluationBenefit() {
        return evaluationBenefit;
    }

    public void setEvaluationBenefit(BigDecimal evaluationBenefit) {
        this.evaluationBenefit = evaluationBenefit;
    }

    public BigDecimal getEvaluationCost() {
        return evaluationCost;
    }

    public void setEvaluationCost(BigDecimal evaluationCost) {
        this.evaluationCost = evaluationCost;
    }

    public String getEvaluationCode() {
        return evaluationCode;
    }

    public void setEvaluationCode(String evaluationCode) {
        this.evaluationCode = evaluationCode == null ? null : evaluationCode.trim();
    }

    public String getEvaluationAnnex() {
        return evaluationAnnex;
    }

    public void setEvaluationAnnex(String evaluationAnnex) {
        this.evaluationAnnex = evaluationAnnex == null ? null : evaluationAnnex.trim();
    }

    public BigDecimal getJointHearingBenefit() {
        return jointHearingBenefit;
    }

    public void setJointHearingBenefit(BigDecimal jointHearingBenefit) {
        this.jointHearingBenefit = jointHearingBenefit;
    }

    public BigDecimal getJointHearingCost() {
        return jointHearingCost;
    }

    public void setJointHearingCost(BigDecimal jointHearingCost) {
        this.jointHearingCost = jointHearingCost;
    }

    public Date getJointHearingTime() {
        return jointHearingTime;
    }

    public void setJointHearingTime(Date jointHearingTime) {
        this.jointHearingTime = jointHearingTime;
    }

    public String getJointHearingAnnex() {
        return jointHearingAnnex;
    }

    public void setJointHearingAnnex(String jointHearingAnnex) {
        this.jointHearingAnnex = jointHearingAnnex == null ? null : jointHearingAnnex.trim();
    }

    public BigDecimal getResponsibilityBenefiy() {
        return responsibilityBenefiy;
    }

    public void setResponsibilityBenefiy(BigDecimal responsibilityBenefiy) {
        this.responsibilityBenefiy = responsibilityBenefiy;
    }

    public Date getResponsibilityTime() {
        return responsibilityTime;
    }

    public void setResponsibilityTime(Date responsibilityTime) {
        this.responsibilityTime = responsibilityTime;
    }

    public String getResponsibilityPeople() {
        return responsibilityPeople;
    }

    public void setResponsibilityPeople(String responsibilityPeople) {
        this.responsibilityPeople = responsibilityPeople == null ? null : responsibilityPeople.trim();
    }

    public String getResponsibilitySecretary() {
        return responsibilitySecretary;
    }

    public void setResponsibilitySecretary(String responsibilitySecretary) {
        this.responsibilitySecretary = responsibilitySecretary == null ? null : responsibilitySecretary.trim();
    }

    public String getResponsibilityAnnex() {
        return responsibilityAnnex;
    }

    public void setResponsibilityAnnex(String responsibilityAnnex) {
        this.responsibilityAnnex = responsibilityAnnex == null ? null : responsibilityAnnex.trim();
    }
}