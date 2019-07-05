package com.hzq.mappers;

import com.hzq.domain.PurchaseEvaluation;

public interface PurchaseEvaluationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseEvaluation record);

    int insertSelective(PurchaseEvaluation record);

    PurchaseEvaluation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseEvaluation record);

    int updateByPrimaryKeyWithBLOBs(PurchaseEvaluation record);

    int updateByPrimaryKey(PurchaseEvaluation record);
}