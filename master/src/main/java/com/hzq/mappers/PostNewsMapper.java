package com.hzq.mappers;

import com.hzq.domain.PostNews;


public interface PostNewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostNews record);

    int insertSelective(PostNews record);

    PostNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostNews record);

    int updateByPrimaryKeyWithBLOBs(PostNews record);

    int updateByPrimaryKey(PostNews record);

    PostNews selectByCommunityId(Integer communityId);
}