package com.hzq.mappers;


import com.hzq.domain.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByNickname(String nickname);

    User selectByNicknameAndPassword(String nickname);

    List<User> selectAllUsers();

}