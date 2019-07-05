package com.hzq.mappers;

import com.hzq.domain.SecretaryBook;

import java.util.List;

public interface SecretaryBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecretaryBook record);

    int insertSelective(SecretaryBook record);

    SecretaryBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecretaryBook record);

    int updateByPrimaryKey(SecretaryBook record);

    List<SecretaryBook> selectByNickname(String nickname);
}