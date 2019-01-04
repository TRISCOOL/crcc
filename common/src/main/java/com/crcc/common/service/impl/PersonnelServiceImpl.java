package com.crcc.common.service.impl;

import com.crcc.common.mapper.PersonnelMapper;
import com.crcc.common.model.Personnel;
import com.crcc.common.service.PersonnelService;
import com.crcc.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class PersonnelServiceImpl implements PersonnelService{

    @Autowired
    private PersonnelMapper personnelMapper;

    @Autowired
    private RedisService redisService;

    private static String CODE_KEY = "personnel_key";

    @Override
    public Long addPersonnel(Personnel personnel) {
        personnel.setCreateTime(new Date());
        personnel.setCode(getCode());
        personnelMapper.insertSelective(personnel);
        return personnel.getId();
    }

    private String getCode(){
        Long num = redisService.incrby(CODE_KEY,1);
        if (num < 10){
            return "0000"+num;
        }

        if (10<= num && num<100){
            return "000"+num;
        }

        if (num>=100 && num<1000){
            return "00"+num;
        }

        if (num >= 1000 && num < 10000){
            return "0"+num;
        }

        if (num >= 10000)
            return num+"";

        return "";
    }

    @Override
    public boolean updatePersonnel(Personnel personnel) {
        personnel.setUpdateTime(new Date());
        int result = personnelMapper.updateByPrimaryKey(personnel);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public List<Personnel> listForPage(String name, String projectName, String position,String firstDegreeLevel,
                                       String secondDegreeLevel,Integer workTime, Integer offset, Integer length) {
        return personnelMapper.listForPage(name,projectName,position,
                workTime,firstDegreeLevel,secondDegreeLevel, offset,length);
    }

    @Override
    public Integer listForPageSize(String name, String projectName,String position,String firstDegreeLevel,
                                   String secondDegreeLevel, Integer workTime) {
        return personnelMapper.listForPageSize(name,projectName,position,workTime,firstDegreeLevel,secondDegreeLevel);
    }

    @Override
    public Personnel getDetails(Long personnelId) {
        return personnelMapper.selectByPrimaryKey(personnelId);
    }
}
