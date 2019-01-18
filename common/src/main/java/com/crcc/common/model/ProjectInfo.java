package com.crcc.common.model;

import com.crcc.common.utils.Utils;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProjectInfo {
    private Long id;

    private Long projectId;

    private String projectType;

    private String address;

    private String orgAddress;

    private Integer status;

    private String statusStr;

    private String mileageNumber;

    private BigDecimal totalPrice;

    private String contractNumber;

    private Integer contractDay;

    private Date contractStartTime;

    private Date contractEndTime;

    private Integer realContractDay;

    private Date realContractStartTime;

    private Date realContractEndTime;

    private String proprietorCompany;

    private String proprietorAddress;

    private String proprietorPhone;

    private String supervisionCompany;

    private String supervisionAddress;

    private String supervisionPhone;

    private String projectManager;

    private Long projectManagerPositionId;

    private String projectManagerPhone;

    private Date projectManagerTime;

    private String projectSecretary;

    private Long projectSecretaryPositionId;

    private String projectSecretaryPhone;

    private Date projectSecretaryTime;

    private String chiefEngineer;

    private Long chiefEngineerPositionId;

    private Date chiefEngineerTime;

    private String chiefEngineerPhone;

    private Integer inputPerson;

    private Integer formalEmployee;

    private Integer externalEmployee;

    private String description;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    private String projectName;

    private String code;

    private BigDecimal temporarilyPrice;

    private String manager;
    private String secretary;
    private String engineer;

    private String createUserStr;
    private String updateUserStr;

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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public BigDecimal getTemporarilyPrice() {
        return temporarilyPrice;
    }

    public void setTemporarilyPrice(BigDecimal temporarilyPrice) {
        this.temporarilyPrice = temporarilyPrice;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        if (status != null){
            switch (status){
                case 0:
                    setStatusStr("在建");
                    break;
                case 1:
                    setStatusStr("完工未结算");
                    break;
                case 2:
                    setStatusStr("完工以结算");
                    break;
                case 3:
                    setStatusStr("停工");
                    break;
                default:
                    break;
            }
        }
    }

    public String getMileageNumber() {
        return mileageNumber;
    }

    public void setMileageNumber(String mileageNumber) {
        this.mileageNumber = mileageNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber == null ? null : contractNumber.trim();
    }

    public Integer getContractDay() {
        return contractDay;
    }

    public void setContractDay(Integer contractDay) {
        this.contractDay = contractDay;
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

    public Integer getRealContractDay() {
        return realContractDay;
    }

    public void setRealContractDay(Integer realContractDay) {
        this.realContractDay = realContractDay;
    }

    public Date getRealContractStartTime() {
        return realContractStartTime;
    }

    public void setRealContractStartTime(Date realContractStartTime) {
        this.realContractStartTime = realContractStartTime;
    }

    public Date getRealContractEndTime() {
        return realContractEndTime;
    }

    public void setRealContractEndTime(Date realContractEndTime) {
        this.realContractEndTime = realContractEndTime;
    }

    public String getProprietorCompany() {
        return proprietorCompany;
    }

    public void setProprietorCompany(String proprietorCompany) {
        this.proprietorCompany = proprietorCompany == null ? null : proprietorCompany.trim();
    }

    public String getProprietorAddress() {
        return proprietorAddress;
    }

    public void setProprietorAddress(String proprietorAddress) {
        this.proprietorAddress = proprietorAddress == null ? null : proprietorAddress.trim();
    }

    public String getProprietorPhone() {
        return proprietorPhone;
    }

    public void setProprietorPhone(String proprietorPhone) {
        this.proprietorPhone = proprietorPhone == null ? null : proprietorPhone.trim();
    }

    public String getSupervisionCompany() {
        return supervisionCompany;
    }

    public void setSupervisionCompany(String supervisionCompany) {
        this.supervisionCompany = supervisionCompany == null ? null : supervisionCompany.trim();
    }

    public String getSupervisionAddress() {
        return supervisionAddress;
    }

    public void setSupervisionAddress(String supervisionAddress) {
        this.supervisionAddress = supervisionAddress == null ? null : supervisionAddress.trim();
    }

    public String getSupervisionPhone() {
        return supervisionPhone;
    }

    public void setSupervisionPhone(String supervisionPhone) {
        this.supervisionPhone = supervisionPhone == null ? null : supervisionPhone.trim();
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager == null ? null : projectManager.trim();
    }

    public Long getProjectManagerPositionId() {
        return projectManagerPositionId;
    }

    public void setProjectManagerPositionId(Long projectManagerPositionId) {
        this.projectManagerPositionId = projectManagerPositionId;
    }

    public String getProjectManagerPhone() {
        return projectManagerPhone;
    }

    public void setProjectManagerPhone(String projectManagerPhone) {
        this.projectManagerPhone = projectManagerPhone == null ? null : projectManagerPhone.trim();
    }

    public Date getProjectManagerTime() {
        return projectManagerTime;
    }

    public void setProjectManagerTime(Date projectManagerTime) {
        this.projectManagerTime = projectManagerTime;
    }

    public String getProjectSecretary() {
        return projectSecretary;
    }

    public void setProjectSecretary(String projectSecretary) {
        this.projectSecretary = projectSecretary == null ? null : projectSecretary.trim();
    }

    public Long getProjectSecretaryPositionId() {
        return projectSecretaryPositionId;
    }

    public void setProjectSecretaryPositionId(Long projectSecretaryPositionId) {
        this.projectSecretaryPositionId = projectSecretaryPositionId;
    }

    public String getProjectSecretaryPhone() {
        return projectSecretaryPhone;
    }

    public void setProjectSecretaryPhone(String projectSecretaryPhone) {
        this.projectSecretaryPhone = projectSecretaryPhone == null ? null : projectSecretaryPhone.trim();
    }

    public Date getProjectSecretaryTime() {
        return projectSecretaryTime;
    }

    public void setProjectSecretaryTime(Date projectSecretaryTime) {
        this.projectSecretaryTime = projectSecretaryTime;
    }

    public String getChiefEngineer() {
        return chiefEngineer;
    }

    public void setChiefEngineer(String chiefEngineer) {
        this.chiefEngineer = chiefEngineer == null ? null : chiefEngineer.trim();
    }

    public Long getChiefEngineerPositionId() {
        return chiefEngineerPositionId;
    }

    public void setChiefEngineerPositionId(Long chiefEngineerPositionId) {
        this.chiefEngineerPositionId = chiefEngineerPositionId;
    }

    public Date getChiefEngineerTime() {
        return chiefEngineerTime;
    }

    public void setChiefEngineerTime(Date chiefEngineerTime) {
        this.chiefEngineerTime = chiefEngineerTime;
    }

    public String getChiefEngineerPhone() {
        return chiefEngineerPhone;
    }

    public void setChiefEngineerPhone(String chiefEngineerPhone) {
        this.chiefEngineerPhone = chiefEngineerPhone == null ? null : chiefEngineerPhone.trim();
    }

    public Integer getInputPerson() {
        return inputPerson;
    }

    public void setInputPerson(Integer inputPerson) {
        this.inputPerson = inputPerson;
    }

    public Integer getFormalEmployee() {
        return formalEmployee;
    }

    public void setFormalEmployee(Integer formalEmployee) {
        this.formalEmployee = formalEmployee;
    }

    public Integer getExternalEmployee() {
        return externalEmployee;
    }

    public void setExternalEmployee(Integer externalEmployee) {
        this.externalEmployee = externalEmployee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public static ProjectInfo getInfoByPojo(ProjectInfoPojo pojo){
        if (pojo == null)
            return null;

        ProjectInfo info = new ProjectInfo();
        info.setManager(pojo.getManager() == null?null:Utils.toJson(pojo.getManager()));
        info.setEngineer(pojo.getEngineer() == null?null:Utils.toJson(pojo.getEngineer()));
        info.setSecretary(pojo.getSecretary() == null?null:Utils.toJson(pojo.getSecretary()));
        info.setStatusStr(pojo.getStatusStr());
        info.setStatus(pojo.getStatus());
        info.setUpdateUser(pojo.getUpdateUser());
        info.setCreateUser(pojo.getCreateUser());
        info.setUpdateTime(pojo.getUpdateTime());
        info.setCreateTime(pojo.getCreateTime());
        info.setAddress(pojo.getAddress());
        info.setCode(pojo.getCode());
        info.setContractDay(pojo.getContractDay());
        info.setContractEndTime(pojo.getContractEndTime());
        info.setContractStartTime(pojo.getContractStartTime());
        info.setContractNumber(pojo.getContractNumber());
        info.setDescription(pojo.getDescription());
        info.setExternalEmployee(pojo.getExternalEmployee());
        info.setFormalEmployee(pojo.getFormalEmployee());
        info.setId(pojo.getId());
        info.setInputPerson(pojo.getInputPerson());
        info.setMileageNumber(pojo.getMileageNumber());
        info.setOrgAddress(pojo.getOrgAddress());
        info.setProjectId(pojo.getProjectId());
        info.setProjectName(pojo.getProjectName());
        info.setProjectType(pojo.getProjectType());
        info.setProprietorAddress(pojo.getProprietorAddress());
        info.setProprietorCompany(pojo.getProprietorCompany());
        info.setProprietorPhone(pojo.getProprietorPhone());
        info.setTotalPrice(pojo.getTotalPrice());
        info.setTemporarilyPrice(pojo.getTemporarilyPrice());
        info.setSupervisionPhone(pojo.getSupervisionPhone());
        info.setSupervisionCompany(pojo.getSupervisionCompany());
        info.setSupervisionAddress(pojo.getSupervisionAddress());
        info.setRealContractStartTime(pojo.getRealContractStartTime());
        info.setRealContractEndTime(pojo.getRealContractEndTime());
        info.setRealContractDay(pojo.getRealContractDay());
        return info;
    }
}