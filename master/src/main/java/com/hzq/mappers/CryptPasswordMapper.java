package com.hzq.mappers;

import com.hzq.domain.CryptPassword;

public interface CryptPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CryptPassword record);

    int insertSelective(CryptPassword record);

    CryptPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CryptPassword record);

    int updateByPrimaryKey(CryptPassword record);

    CryptPassword selectByUserId(Integer userId);
}