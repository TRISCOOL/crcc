package com.crcc.common.mapper;

import com.crcc.common.model.Personnel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonnelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Personnel record);

    int insertSelective(Personnel record);

    Personnel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Personnel record);

    int updateByPrimaryKey(Personnel record);

    List<Personnel> listForPage(@Param("name") String name, @Param("projectName") String projectName,
                                @Param("position") String position, @Param("workTime") Integer workTime,
                                @Param("offset") Integer offset, @Param("length") Integer length);
}