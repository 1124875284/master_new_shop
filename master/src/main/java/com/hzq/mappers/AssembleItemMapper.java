package com.hzq.mappers;

import com.hzq.domain.AssembleItem;

public interface AssembleItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AssembleItem record);

    int insertSelective(AssembleItem record);

    AssembleItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AssembleItem record);

    int updateByPrimaryKey(AssembleItem record);
}