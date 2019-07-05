package com.hzq.mappers;

import com.hzq.domain.PurchaseCategory;

import java.util.List;

public interface PurchaseCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseCategory record);

    int insertSelective(PurchaseCategory record);

    PurchaseCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseCategory record);

    int updateByPrimaryKey(PurchaseCategory record);

    List<PurchaseCategory> selectAllParentCategories();

    List<PurchaseCategory> selectAllChildrenCategories();

    List<PurchaseCategory> selectAllPurchaseInfoByCategoryId(Integer categoryId);
}