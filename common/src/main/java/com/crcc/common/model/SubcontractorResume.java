package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class SubcontractorResume {
    private Long id;

    private Long subcontractorId;

    private Date startTime;

    private Date endTime;

    private Date realStartTime;

    private Date realEndTime;

    private Long orgId;

    private BigDecimal contractPrice;

    private BigDecimal realPrice;

    private String projectEvaluation;

    private String projectDescription;

    private String comanyEvalution;

    private String groupEvalution;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

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

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
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

    public String getComanyEvalution() {
        return comanyEvalution;
    }

    public void setComanyEvalution(String comanyEvalution) {
        this.comanyEvalution = comanyEvalution == null ? null : comanyEvalution.trim();
    }

    public String getGroupEvalution() {
        return groupEvalution;
    }

    public void setGroupEvalution(String groupEvalution) {
        this.groupEvalution = groupEvalution == null ? null : groupEvalution.trim();
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