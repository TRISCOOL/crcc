package com.crcc.common.service;

import com.crcc.common.model.Personnel;

import java.util.List;

public interface PersonnelService {
    Long addPersonnel(Personnel personnel);

    boolean updatePersonnel(Personnel personnel);

    List<Personnel> listForPage(String name,String projectName,String position,String firstDegreeLevel,
                                String secondDegreeLevel,Integer workTime, Integer offset,Integer length);

    Integer listForPageSize(String name,String projectName,String position,String firstDegreeLevel,
                            String secondDegreeLevel,Integer workTime);

    Personnel getDetails(Long personnelId);

    boolean deletedById(Long id);
}
