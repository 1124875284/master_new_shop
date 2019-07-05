package com.hzq.mappers;

import com.hzq.domain.PurchaseProduct;

import java.util.List;

public interface PurchaseProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseProduct record);

    int insertSelective(PurchaseProduct record);

    PurchaseProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseProduct record);

    int updateByPrimaryKey(PurchaseProduct record);

    PurchaseProduct selectProductDetailById(Integer productId);

    List<PurchaseProduct> selectAllPurchaseProducts();
}