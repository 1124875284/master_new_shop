package com.hzq.mappers;


import com.hzq.domain.BannerImage;

import java.util.List;

public interface BannerImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BannerImage record);

    int insertSelective(BannerImage record);

    BannerImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BannerImage record);

    int updateByPrimaryKey(BannerImage record);

    List<BannerImage> getCarousalImage();
}