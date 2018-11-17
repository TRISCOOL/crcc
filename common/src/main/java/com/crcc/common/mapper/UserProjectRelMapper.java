package com.crcc.common.mapper;

import com.crcc.common.model.UserProjectRel;
import org.apache.ibatis.annotations.Param;

public interface UserProjectRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserProjectRel record);

    int insertSelective(UserProjectRel record);

    UserProjectRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserProjectRel record);

    int updateByPrimaryKey(UserProjectRel record);

    int deleteByUserId(@Param("userId")Long userId);
}