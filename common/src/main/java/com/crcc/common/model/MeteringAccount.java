package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class MeteringAccount {
    private Long id;

    private Integer meteringNum;

    private Date meteringTime;

    private BigDecimal valuationAmountTax;

    private BigDecimal valuationAmountNotTax;

    private BigDecimal tax;

    private BigDecimal realAmountTax;

    private BigDecimal realAmount;

    private BigDecimal alreadyPaidAmount;

    private BigDecimal unpaidAmount;

    private BigDecimal payProportion;

    private BigDecimal extraAmount;

    private BigDecimal notCalculatedAmount;

    private String remark;

    private String annexUrl;

    private Long projectId;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private String projectName;

    private BigDecimal productionValue;

    public BigDecimal getProductionValue() {
        return productionValue;
    }

    public void setProductionValue(BigDecimal productionValue) {
        this.productionValue = productionValue;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Integer getMeteringNum() {
        return meteringNum;
    }

    public void setMeteringNum(Integer meteringNum) {
        this.meteringNum = meteringNum;
    }

    public Date getMeteringTime() {
        return meteringTime;
    }

    public void setMeteringTime(Date meteringTime) {
        this.meteringTime = meteringTime;
    }

    public BigDecimal getValuationAmountTax() {
        return valuationAmountTax;
    }

    public void setValuationAmountTax(BigDecimal valuationAmountTax) {
        this.valuationAmountTax = valuationAmountTax;
    }

    public BigDecimal getValuationAmountNotTax() {
        return valuationAmountNotTax;
    }

    public void setValuationAmountNotTax(BigDecimal valuationAmountNotTax) {
        this.valuationAmountNotTax = valuationAmountNotTax;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getRealAmountTax() {
        return realAmountTax;
    }

    public void setRealAmountTax(BigDecimal realAmountTax) {
        this.realAmountTax = realAmountTax;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public BigDecimal getAlreadyPaidAmount() {
        return alreadyPaidAmount;
    }

    public void setAlreadyPaidAmount(BigDecimal alreadyPaidAmount) {
        this.alreadyPaidAmount = alreadyPaidAmount;
    }

    public BigDecimal getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(BigDecimal unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public BigDecimal getPayProportion() {
        return payProportion;
    }

    public void setpayProportion(BigDecimal payProportion) {
        this.payProportion = payProportion;
    }

    public BigDecimal getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(BigDecimal extraAmount) {
        this.extraAmount = extraAmount;
    }

    public BigDecimal getNotCalculatedAmount() {
        return notCalculatedAmount;
    }

    public void setNotCalculatedAmount(BigDecimal notCalculatedAmount) {
        this.notCalculatedAmount = notCalculatedAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl == null ? null : annexUrl.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}