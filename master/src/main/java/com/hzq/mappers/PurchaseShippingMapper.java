package com.hzq.mappers;

import com.hzq.domain.PurchaseShipping;

import java.util.List;

public interface PurchaseShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseShipping record);

    int insertSelective(PurchaseShipping record);

    PurchaseShipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseShipping record);

    int updateByPrimaryKey(PurchaseShipping record);

    List<PurchaseShipping> selectAllShippingsByUserId(Integer userId);
}