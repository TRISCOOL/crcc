package com.crcc.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Subcontractor {
    private Long id;

    private Date setUpTime;

    private Long taxpayerTypeId;

    private BigDecimal registeredCapital;

    private Long typeId;

    private String phone;

    private String email;

    private String address;

    private String code;

    private String zipCode;

    private String legalPersonName;

    private String legalPersonPosition;

    private String legalPersonCard;

    private String legalPersonPhone;

    private String legalPersonAddress;

    private String businessLicenseCode;

    private Date businessLicenseValidityPeriod;

    private String businessLicenseFrom;

    private String qualificationCode;

    private Date qualificationValidityPeriod;

    private String qualificationFrom;

    private String safetyCode;

    private Date safetyValidityPeriod;

    private String safetyFrom;

    private String bank;

    private String bankAccount;

    private String bankFrom;

    private String annex;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private String professionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSetUpTime() {
        return setUpTime;
    }

    public void setSetUpTime(Date setUpTime) {
        this.setUpTime = setUpTime;
    }

    public Long getTaxpayerTypeId() {
        return taxpayerTypeId;
    }

    public void setTaxpayerTypeId(Long taxpayerTypeId) {
        this.taxpayerTypeId = taxpayerTypeId;
    }

    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName == null ? null : legalPersonName.trim();
    }

    public String getLegalPersonPosition() {
        return legalPersonPosition;
    }

    public void setLegalPersonPosition(String legalPersonPosition) {
        this.legalPersonPosition = legalPersonPosition == null ? null : legalPersonPosition.trim();
    }

    public String getLegalPersonCard() {
        return legalPersonCard;
    }

    public void setLegalPersonCard(String legalPersonCard) {
        this.legalPersonCard = legalPersonCard == null ? null : legalPersonCard.trim();
    }

    public String getLegalPersonPhone() {
        return legalPersonPhone;
    }

    public void setLegalPersonPhone(String legalPersonPhone) {
        this.legalPersonPhone = legalPersonPhone == null ? null : legalPersonPhone.trim();
    }

    public String getLegalPersonAddress() {
        return legalPersonAddress;
    }

    public void setLegalPersonAddress(String legalPersonAddress) {
        this.legalPersonAddress = legalPersonAddress == null ? null : legalPersonAddress.trim();
    }

    public String getBusinessLicenseCode() {
        return businessLicenseCode;
    }

    public void setBusinessLicenseCode(String businessLicenseCode) {
        this.businessLicenseCode = businessLicenseCode == null ? null : businessLicenseCode.trim();
    }

    public Date getBusinessLicenseValidityPeriod() {
        return businessLicenseValidityPeriod;
    }

    public void setBusinessLicenseValidityPeriod(Date businessLicenseValidityPeriod) {
        this.businessLicenseValidityPeriod = businessLicenseValidityPeriod;
    }

    public String getBusinessLicenseFrom() {
        return businessLicenseFrom;
    }

    public void setBusinessLicenseFrom(String businessLicenseFrom) {
        this.businessLicenseFrom = businessLicenseFrom == null ? null : businessLicenseFrom.trim();
    }

    public String getQualificationCode() {
        return qualificationCode;
    }

    public void setQualificationCode(String qualificationCode) {
        this.qualificationCode = qualificationCode == null ? null : qualificationCode.trim();
    }

    public Date getQualificationValidityPeriod() {
        return qualificationValidityPeriod;
    }

    public void setQualificationValidityPeriod(Date qualificationValidityPeriod) {
        this.qualificationValidityPeriod = qualificationValidityPeriod;
    }

    public String getQualificationFrom() {
        return qualificationFrom;
    }

    public void setQualificationFrom(String qualificationFrom) {
        this.qualificationFrom = qualificationFrom == null ? null : qualificationFrom.trim();
    }

    public String getSafetyCode() {
        return safetyCode;
    }

    public void setSafetyCode(String safetyCode) {
        this.safetyCode = safetyCode == null ? null : safetyCode.trim();
    }

    public Date getSafetyValidityPeriod() {
        return safetyValidityPeriod;
    }

    public void setSafetyValidityPeriod(Date safetyValidityPeriod) {
        this.safetyValidityPeriod = safetyValidityPeriod;
    }

    public String getSafetyFrom() {
        return safetyFrom;
    }

    public void setSafetyFrom(String safetyFrom) {
        this.safetyFrom = safetyFrom == null ? null : safetyFrom.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBankFrom() {
        return bankFrom;
    }

    public void setBankFrom(String bankFrom) {
        this.bankFrom = bankFrom == null ? null : bankFrom.trim();
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex == null ? null : annex.trim();
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

    public String getProfessionType() {
        return professionType;
    }

    public void setProfessionType(String professionType) {
        this.professionType = professionType == null ? null : professionType.trim();
    }
}