package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class MeteringAccount {
    private Long id;

    private Integer meteringNum;

    private Date meteringTime;

    private BigDecimal valuationAmountTax;

    private String valuationAmountNotTax;

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

    public String getValuationAmountNotTax() {
        return valuationAmountNotTax;
    }

    public void setValuationAmountNotTax(String valuationAmountNotTax) {
        this.valuationAmountNotTax = valuationAmountNotTax == null ? null : valuationAmountNotTax.trim();
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