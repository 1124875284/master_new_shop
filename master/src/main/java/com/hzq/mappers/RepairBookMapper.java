package com.hzq.mappers;

import com.hzq.domain.RepairBook;

import java.util.List;

public interface RepairBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepairBook record);

    int insertSelective(RepairBook record);

    RepairBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RepairBook record);

    int updateByPrimaryKey(RepairBook record);

    List<RepairBook> selectByNickname(String nickname);
}