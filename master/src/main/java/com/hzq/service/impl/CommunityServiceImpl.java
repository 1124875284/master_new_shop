package com.hzq.service.impl;

import com.google.common.collect.Lists;
import com.hzq.domain.Community;
import com.hzq.mappers.CommunityMapper;
import com.hzq.service.CommunityService;
import com.hzq.vo.CommunityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@CacheConfig(cacheNames = "community")
@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<CommunityVo> getAllCommunities() {
        List<CommunityVo> communityVos= Lists.newArrayList();
        List<Community> communities=communityMapper.selectAllCommunities();
        if (!CollectionUtils.isEmpty(communities)){
            communities.stream().parallel()
                    .forEach(community -> {
                        CommunityVo vo=new CommunityVo();
                        BeanUtils.copyProperties(community,vo);
                        communityVos.add(vo);
                    });
            return communityVos;
        }
        return null;
    }
}
