package com.hzq.mappers;

import com.hzq.domain.Assemble;

import java.util.List;

public interface AssembleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assemble record);

    int insertSelective(Assemble record);

    Assemble selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assemble record);

    int updateByPrimaryKey(Assemble record);

    List<Assemble> listAllAssemblesByProductId(Integer productId);

    Assemble selectAssembleDetailInfoById(Integer assembleId);


}