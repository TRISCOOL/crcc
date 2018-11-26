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

    private Long professionType;

    private String shareEvaluation;

    private String qualification;

    private String shareRemark;

    private String groupEvaluation;

    private String groupRemark;

    private String companyEvaluation;

    private String companyGroupEvaluation;

    private String name;

    private String taxpayerTypeStr;

    private String typeStr;

    private String professionStr;

    public String getTaxpayerTypeStr() {
        return taxpayerTypeStr;
    }

    public void setTaxpayerTypeStr(String taxpayerTypeStr) {
        this.taxpayerTypeStr = taxpayerTypeStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getProfessionStr() {
        return professionStr;
    }

    public void setProfessionStr(String professionStr) {
        this.professionStr = professionStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Long getProfessionType() {
        return professionType;
    }

    public void setProfessionType(Long professionType) {
        this.professionType = professionType;
    }

    public String getShareEvaluation() {
        return shareEvaluation;
    }

    public void setShareEvaluation(String shareEvaluation) {
        this.shareEvaluation = shareEvaluation == null ? null : shareEvaluation.trim();
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification == null ? null : qualification.trim();
    }

    public String getShareRemark() {
        return shareRemark;
    }

    public void setShareRemark(String shareRemark) {
        this.shareRemark = shareRemark == null ? null : shareRemark.trim();
    }

    public String getGroupEvaluation() {
        return groupEvaluation;
    }

    public void setGroupEvaluation(String groupEvaluation) {
        this.groupEvaluation = groupEvaluation == null ? null : groupEvaluation.trim();
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark == null ? null : groupRemark.trim();
    }

    public String getCompanyEvaluation() {
        return companyEvaluation;
    }

    public void setCompanyEvaluation(String companyEvaluation) {
        this.companyEvaluation = companyEvaluation == null ? null : companyEvaluation.trim();
    }

    public String getCompanyGroupEvaluation() {
        return companyGroupEvaluation;
    }

    public void setCompanyGroupEvaluation(String companyGroupEvaluation) {
        this.companyGroupEvaluation = companyGroupEvaluation == null ? null : companyGroupEvaluation.trim();
    }
}