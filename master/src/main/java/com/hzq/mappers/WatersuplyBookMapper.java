package com.hzq.mappers;

import com.hzq.domain.WatersuplyBook;

import java.util.List;

public interface WatersuplyBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WatersuplyBook record);

    int insertSelective(WatersuplyBook record);

    WatersuplyBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WatersuplyBook record);

    int updateByPrimaryKey(WatersuplyBook record);

    List<WatersuplyBook> selectByNickname(String nickname);

    List<WatersuplyBook> selectAllByNickname(String nickname);
}