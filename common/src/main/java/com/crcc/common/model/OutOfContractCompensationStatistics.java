package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class OutOfContractCompensationStatistics {
    private Long id;

    private Long projectId;

    private Long subcontractorId;

    private Long laborAccountId;

    private Date reportTime;

    private Integer quarter;

    private BigDecimal totalAmountContract;

    private BigDecimal mechanicalClass;

    private BigDecimal sporadicEmployment;

    private BigDecimal dailyWorkSubtotal;

    private BigDecimal outIn;

    private BigDecimal disasterDamage;

    private BigDecimal workStop;

    private BigDecimal other;

    private BigDecimal compensationSubtotal;

    private BigDecimal total;

    private BigDecimal dailyPercentage;

    private BigDecimal compensationPercentage;

    private BigDecimal amountAlreadyDisbursed;

    private BigDecimal disbursedPercentage;

    private String examination;

    private String settlement;

    private BigDecimal estimateMechanicalClass;

    private BigDecimal estimateSporadicEmployment;

    private BigDecimal estimateDailyWorkSubtotal;

    private BigDecimal estimateOutIn;

    private BigDecimal estimateDisasterDamage;

    private BigDecimal estimateWorkStop;

    private BigDecimal estimateOther;

    private BigDecimal estimateCompensationSubtotal;

    private BigDecimal estimateTotal;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private String projectName;
    private String projectType;
    private String contractNumber;
    private String subcontractorName;
    private String teamName;
    private String contractPerson;


    private BigDecimal statisticsDailyWorkSubtotal;
    private BigDecimal statisticsCompensationSubtotal;
    private BigDecimal statisticsEstimateDailyWorkSubtotal;
    private BigDecimal statisticsEstimateCompensationSubtotal;

    private BigDecimal statisticsTotalAmountContract;
    private BigDecimal statisticsAlreadySubtotal;
    private BigDecimal statisticsEstimateSubtotal;

    public BigDecimal getStatisticsTotalAmountContract() {
        return statisticsTotalAmountContract;
    }

    public void setStatisticsTotalAmountContract(BigDecimal statisticsTotalAmountContract) {
        this.statisticsTotalAmountContract = statisticsTotalAmountContract;
    }

    public BigDecimal getStatisticsAlreadySubtotal() {
        return statisticsAlreadySubtotal;
    }

    public void setStatisticsAlreadySubtotal(BigDecimal statisticsAlreadySubtotal) {
        this.statisticsAlreadySubtotal = statisticsAlreadySubtotal;
    }

    public BigDecimal getStatisticsEstimateSubtotal() {
        return statisticsEstimateSubtotal;
    }

    public void setStatisticsEstimateSubtotal(BigDecimal statisticsEstimateSubtotal) {
        this.statisticsEstimateSubtotal = statisticsEstimateSubtotal;
    }

    public BigDecimal getStatisticsDailyWorkSubtotal() {
        return statisticsDailyWorkSubtotal;
    }

    public void setStatisticsDailyWorkSubtotal(BigDecimal statisticsDailyWorkSubtotal) {
        this.statisticsDailyWorkSubtotal = statisticsDailyWorkSubtotal;
    }

    public BigDecimal getStatisticsCompensationSubtotal() {
        return statisticsCompensationSubtotal;
    }

    public void setStatisticsCompensationSubtotal(BigDecimal statisticsCompensationSubtotal) {
        this.statisticsCompensationSubtotal = statisticsCompensationSubtotal;
    }

    public BigDecimal getStatisticsEstimateDailyWorkSubtotal() {
        return statisticsEstimateDailyWorkSubtotal;
    }

    public void setStatisticsEstimateDailyWorkSubtotal(BigDecimal statisticsEstimateDailyWorkSubtotal) {
        this.statisticsEstimateDailyWorkSubtotal = statisticsEstimateDailyWorkSubtotal;
    }

    public BigDecimal getStatisticsEstimateCompensationSubtotal() {
        return statisticsEstimateCompensationSubtotal;
    }

    public void setStatisticsEstimateCompensationSubtotal(BigDecimal statisticsEstimateCompensationSubtotal) {
        this.statisticsEstimateCompensationSubtotal = statisticsEstimateCompensationSubtotal;
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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
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

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    public BigDecimal getEstimateTotal() {
        return estimateTotal;
    }

    public void setEstimateTotal(BigDecimal estimateTotal) {
        this.estimateTotal = estimateTotal;
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

    public Long getLaborAccountId() {
        return laborAccountId;
    }

    public void setLaborAccountId(Long laborAccountId) {
        this.laborAccountId = laborAccountId;
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

    public BigDecimal getTotalAmountContract() {
        return totalAmountContract;
    }

    public void setTotalAmountContract(BigDecimal totalAmountContract) {
        this.totalAmountContract = totalAmountContract;
    }

    public BigDecimal getMechanicalClass() {
        return mechanicalClass;
    }

    public void setMechanicalClass(BigDecimal mechanicalClass) {
        this.mechanicalClass = mechanicalClass;
    }

    public BigDecimal getSporadicEmployment() {
        return sporadicEmployment;
    }

    public void setSporadicEmployment(BigDecimal sporadicEmployment) {
        this.sporadicEmployment = sporadicEmployment;
    }

    public BigDecimal getDailyWorkSubtotal() {
        return dailyWorkSubtotal;
    }

    public void setDailyWorkSubtotal(BigDecimal dailyWorkSubtotal) {
        this.dailyWorkSubtotal = dailyWorkSubtotal;
    }

    public BigDecimal getOutIn() {
        return outIn;
    }

    public void setOutIn(BigDecimal outIn) {
        this.outIn = outIn;
    }

    public BigDecimal getDisasterDamage() {
        return disasterDamage;
    }

    public void setDisasterDamage(BigDecimal disasterDamage) {
        this.disasterDamage = disasterDamage;
    }

    public BigDecimal getWorkStop() {
        return workStop;
    }

    public void setWorkStop(BigDecimal workStop) {
        this.workStop = workStop;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getCompensationSubtotal() {
        return compensationSubtotal;
    }

    public void setCompensationSubtotal(BigDecimal compensationSubtotal) {
        this.compensationSubtotal = compensationSubtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDailyPercentage() {
        return dailyPercentage;
    }

    public void setDailyPercentage(BigDecimal dailyPercentage) {
        this.dailyPercentage = dailyPercentage;
    }

    public BigDecimal getCompensationPercentage() {
        return compensationPercentage;
    }

    public void setCompensationPercentage(BigDecimal compensationPercentage) {
        this.compensationPercentage = compensationPercentage;
    }

    public BigDecimal getAmountAlreadyDisbursed() {
        return amountAlreadyDisbursed;
    }

    public void setAmountAlreadyDisbursed(BigDecimal amountAlreadyDisbursed) {
        this.amountAlreadyDisbursed = amountAlreadyDisbursed;
    }

    public BigDecimal getDisbursedPercentage() {
        return disbursedPercentage;
    }

    public void setDisbursedPercentage(BigDecimal disbursedPercentage) {
        this.disbursedPercentage = disbursedPercentage;
    }

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination == null ? null : examination.trim();
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement == null ? null : settlement.trim();
    }

    public BigDecimal getEstimateMechanicalClass() {
        return estimateMechanicalClass;
    }

    public void setEstimateMechanicalClass(BigDecimal estimateMechanicalClass) {
        this.estimateMechanicalClass = estimateMechanicalClass;
    }

    public BigDecimal getEstimateSporadicEmployment() {
        return estimateSporadicEmployment;
    }

    public void setEstimateSporadicEmployment(BigDecimal estimateSporadicEmployment) {
        this.estimateSporadicEmployment = estimateSporadicEmployment;
    }

    public BigDecimal getEstimateDailyWorkSubtotal() {
        return estimateDailyWorkSubtotal;
    }

    public void setEstimateDailyWorkSubtotal(BigDecimal estimateDailyWorkSubtotal) {
        this.estimateDailyWorkSubtotal = estimateDailyWorkSubtotal;
    }

    public BigDecimal getEstimateOutIn() {
        return estimateOutIn;
    }

    public void setEstimateOutIn(BigDecimal estimateOutIn) {
        this.estimateOutIn = estimateOutIn;
    }

    public BigDecimal getEstimateDisasterDamage() {
        return estimateDisasterDamage;
    }

    public void setEstimateDisasterDamage(BigDecimal estimateDisasterDamage) {
        this.estimateDisasterDamage = estimateDisasterDamage;
    }

    public BigDecimal getEstimateWorkStop() {
        return estimateWorkStop;
    }

    public void setEstimateWorkStop(BigDecimal estimateWorkStop) {
        this.estimateWorkStop = estimateWorkStop;
    }

    public BigDecimal getEstimateOther() {
        return estimateOther;
    }

    public void setEstimateOther(BigDecimal estimateOther) {
        this.estimateOther = estimateOther;
    }

    public BigDecimal getEstimateCompensationSubtotal() {
        return estimateCompensationSubtotal;
    }

    public void setEstimateCompensationSubtotal(BigDecimal estimateCompensationSubtotal) {
        this.estimateCompensationSubtotal = estimateCompensationSubtotal;
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