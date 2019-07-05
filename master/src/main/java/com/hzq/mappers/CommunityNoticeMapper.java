package com.hzq.mappers;

import com.hzq.domain.CommunityNotice;

import java.util.List;

public interface CommunityNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommunityNotice record);

    int insertSelective(CommunityNotice record);

    CommunityNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommunityNotice record);

    int updateByPrimaryKeyWithBLOBs(CommunityNotice record);

    int updateByPrimaryKey(CommunityNotice record);

    List<CommunityNotice> selectAllCommunities();

    List<CommunityNotice> selectByCommunityId(Integer communityId);
}