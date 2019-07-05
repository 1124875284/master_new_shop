package com.hzq.mappers;

import com.hzq.domain.WatersuplyDetails;

public interface WatersuplyDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WatersuplyDetails record);

    int insertSelective(WatersuplyDetails record);

    WatersuplyDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WatersuplyDetails record);

    int updateByPrimaryKey(WatersuplyDetails record);

    WatersuplyDetails selectAllByPrimaryKey(Integer id);
}