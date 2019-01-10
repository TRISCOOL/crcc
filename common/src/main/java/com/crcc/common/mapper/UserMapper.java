package com.crcc.common.mapper;

import com.crcc.common.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findUserByAccount(@Param("account") String account);

    int deleteUserById(@Param("userId")Long userId);

    List<User> listUser(@Param("code")String code,@Param("name")String name,
                        @Param("disable")Integer disable,@Param("offset")Integer offset,
                        @Param("length")Integer length);

    Integer listUserSize(@Param("code")String code,@Param("name")String name,
                         @Param("disable")Integer disable);
}