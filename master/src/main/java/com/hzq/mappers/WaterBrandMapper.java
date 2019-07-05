package com.hzq.mappers;

import com.hzq.domain.WaterBrand;

import java.util.List;

public interface WaterBrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterBrand record);

    int insertSelective(WaterBrand record);

    WaterBrand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterBrand record);

    int updateByPrimaryKey(WaterBrand record);

    List<WaterBrand> selectByUserId(Integer userId);
}