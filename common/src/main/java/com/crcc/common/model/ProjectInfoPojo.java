package com.crcc.common.model;

import com.crcc.common.utils.Utils;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProjectInfoPojo {
    private Long id;

    private Long projectId;

    private String projectType;

    private String address;

    private String orgAddress;

    private Integer status;

    private String statusStr;

    private Integer mileageNumber;

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

    private List<ProjectInfoPeople> manager;
    private List<ProjectInfoPeople> secretary;
    private List<ProjectInfoPeople> engineer;

    public List<ProjectInfoPeople> getManager() {
        return manager;
    }

    public void setManager(List<ProjectInfoPeople> manager) {
        this.manager = manager;
    }

    public List<ProjectInfoPeople> getSecretary() {
        return secretary;
    }

    public void setSecretary(List<ProjectInfoPeople> secretary) {
        this.secretary = secretary;
    }

    public List<ProjectInfoPeople> getEngineer() {
        return engineer;
    }

    public void setEngineer(List<ProjectInfoPeople> engineer) {
        this.engineer = engineer;
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
        this.projectType = projectType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Integer getMileageNumber() {
        return mileageNumber;
    }

    public void setMileageNumber(Integer mileageNumber) {
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
        this.contractNumber = contractNumber;
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
        this.proprietorCompany = proprietorCompany;
    }

    public String getProprietorAddress() {
        return proprietorAddress;
    }

    public void setProprietorAddress(String proprietorAddress) {
        this.proprietorAddress = proprietorAddress;
    }

    public String getProprietorPhone() {
        return proprietorPhone;
    }

    public void setProprietorPhone(String proprietorPhone) {
        this.proprietorPhone = proprietorPhone;
    }

    public String getSupervisionCompany() {
        return supervisionCompany;
    }

    public void setSupervisionCompany(String supervisionCompany) {
        this.supervisionCompany = supervisionCompany;
    }

    public String getSupervisionAddress() {
        return supervisionAddress;
    }

    public void setSupervisionAddress(String supervisionAddress) {
        this.supervisionAddress = supervisionAddress;
    }

    public String getSupervisionPhone() {
        return supervisionPhone;
    }

    public void setSupervisionPhone(String supervisionPhone) {
        this.supervisionPhone = supervisionPhone;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
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
        this.projectManagerPhone = projectManagerPhone;
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
        this.projectSecretary = projectSecretary;
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
        this.projectSecretaryPhone = projectSecretaryPhone;
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
        this.chiefEngineer = chiefEngineer;
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
        this.chiefEngineerPhone = chiefEngineerPhone;
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
        this.description = description;
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

    public BigDecimal getTemporarilyPrice() {
        return temporarilyPrice;
    }

    public void setTemporarilyPrice(BigDecimal temporarilyPrice) {
        this.temporarilyPrice = temporarilyPrice;
    }

    public static ProjectInfoPojo getPojoByInfo(ProjectInfo info){
        if (info == null)
            return null;

        ProjectInfoPojo pojo = new ProjectInfoPojo();
        pojo.setManager(info.getManager() == null?null:Utils.fromJson(info.getManager(),new TypeToken<List<ProjectInfoPeople>>(){}));
        pojo.setEngineer(info.getEngineer() == null?null:Utils.fromJson(info.getEngineer(),new TypeToken<List<ProjectInfoPeople>>(){}));
        pojo.setSecretary(info.getSecretary() == null?null:Utils.fromJson(info.getSecretary(),new TypeToken<List<ProjectInfoPeople>>(){}));
        pojo.setStatusStr(info.getStatusStr());
        pojo.setStatus(info.getStatus());
        pojo.setUpdateUser(info.getUpdateUser());
        pojo.setCreateUser(info.getCreateUser());
        pojo.setUpdateTime(info.getUpdateTime());
        pojo.setCreateTime(info.getCreateTime());
        pojo.setAddress(info.getAddress());
        pojo.setCode(info.getCode());
        pojo.setContractDay(info.getContractDay());
        pojo.setContractEndTime(info.getContractEndTime());
        pojo.setContractStartTime(info.getContractStartTime());
        pojo.setContractNumber(info.getContractNumber());
        pojo.setDescription(info.getDescription());
        pojo.setExternalEmployee(info.getExternalEmployee());
        pojo.setFormalEmployee(info.getFormalEmployee());
        pojo.setId(info.getId());
        pojo.setInputPerson(info.getInputPerson());
        pojo.setMileageNumber(info.getMileageNumber());
        pojo.setOrgAddress(info.getOrgAddress());
        pojo.setProjectId(info.getProjectId());
        pojo.setProjectName(info.getProjectName());
        pojo.setProjectType(info.getProjectType());
        pojo.setProprietorAddress(info.getProprietorAddress());
        pojo.setProprietorCompany(info.getProprietorCompany());
        pojo.setProprietorPhone(info.getProprietorPhone());
        pojo.setTotalPrice(info.getTotalPrice());
        pojo.setTemporarilyPrice(info.getTemporarilyPrice());
        pojo.setSupervisionPhone(info.getSupervisionPhone());
        pojo.setSupervisionCompany(info.getSupervisionCompany());
        pojo.setSupervisionAddress(info.getSupervisionAddress());
        pojo.setRealContractStartTime(info.getRealContractStartTime());
        pojo.setRealContractEndTime(info.getRealContractEndTime());
        pojo.setRealContractDay(info.getRealContractDay());
        return pojo;
    }
}
