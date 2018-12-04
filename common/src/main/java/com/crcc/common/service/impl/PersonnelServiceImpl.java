package com.crcc.common.service.impl;

import com.crcc.common.mapper.PersonnelMapper;
import com.crcc.common.model.Personnel;
import com.crcc.common.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class PersonnelServiceImpl implements PersonnelService{

    @Autowired
    private PersonnelMapper personnelMapper;

    @Override
    public Long addPersonnel(Personnel personnel) {
        personnel.setCreateTime(new Date());
        personnelMapper.insertSelective(personnel);
        return personnel.getId();
    }

    @Override
    public boolean updatePersonnel(Personnel personnel) {
        personnel.setUpdateTime(new Date());
        int result = personnelMapper.updateByPrimaryKeySelective(personnel);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public List<Personnel> listForPage(String name, String projectName, String position, Integer workTime, Integer offset, Integer length) {
        return personnelMapper.listForPage(name,projectName,position,workTime,offset,length);
    }

    @Override
    public Integer listForPageSize(String name, String projectName, String position, Integer workTime) {
        return personnelMapper.listForPageSize(name,projectName,position,workTime);
    }

    @Override
    public Personnel getDetails(Long personnelId) {
        return personnelMapper.selectByPrimaryKey(personnelId);
    }
}
