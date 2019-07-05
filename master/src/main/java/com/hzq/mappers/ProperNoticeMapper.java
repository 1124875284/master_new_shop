package com.hzq.mappers;

import com.hzq.domain.ProperNotice;

import java.util.List;

public interface ProperNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProperNotice record);

    int insertSelective(ProperNotice record);

    ProperNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProperNotice record);

    int updateByPrimaryKeyWithBLOBs(ProperNotice record);

    int updateByPrimaryKey(ProperNotice record);

    List<ProperNotice> selectAllPropers();

    List<ProperNotice> selectByUserId(Integer userId);
}