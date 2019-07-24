package com.hzq.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hzq.constant.RedisConstant;
import com.hzq.domain.PostNews;
import com.hzq.domain.PostReply;
import com.hzq.domain.User;
import com.hzq.dto.PostNewsDto;
import com.hzq.dto.PostReplyDto;
import com.hzq.exception.ParamException;
import com.hzq.mappers.PostNewsMapper;
import com.hzq.mappers.PostReplyMapper;
import com.hzq.mappers.UserMapper;
import com.hzq.service.PostNewsService;
import com.hzq.util.AuthenticationInfoUtil;
import com.hzq.util.ParamValidatorUtil;
import com.hzq.vo.PostNewsDetailVo;
import com.hzq.vo.PostNewsVo;
import com.hzq.vo.PostReplyVo;
import com.hzq.vo.PosterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 贴吧模块基本使用redis解决
 */
@Slf4j
@Service
public class PostNewsServiceImpl implements PostNewsService {

    @Autowired
    private PostNewsMapper postNewsMapper;
    @Autowired
    private PostReplyMapper postReplyMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Transactional
    @Override
    public PostNewsVo createPostNews(PostNewsDto postNewsDto, BindingResult result) {
        ParamValidatorUtil.validateBindingResult(result);
        try {
            User user = AuthenticationInfoUtil.getUser(userMapper, stringRedisTemplate);

            PostNews postNews=new PostNews();
            BeanUtils.copyProperties(postNewsDto,postNews);

            postNews.setPosted(new Date());
            postNews.setStar(0);
            postNews.setComments(0);

            if (postNewsDto.getType()==null){
                postNews.setType(Integer.valueOf(0).byteValue());
            }

            postNews.setUserId(user.getId());
            postNewsMapper.insertSelective(postNews);

            PostNewsVo postNewsVo=new PostNewsVo();
            postNewsVo.setTitle(postNews.getTitle());
            postNewsVo.setComments(postNews.getComments());
            postNewsVo.setDescription(postNews.getDescription());
            postNewsVo.setImgUrl(Strings.nullToEmpty(postNews.getImgUrl()));
            postNewsVo.setNewsDetail(postNews.getNewsDetail());
            postNewsVo.setUserId(postNews.getUserId());
            postNewsVo.setId(postNews.getId());
            postNewsVo.setStar(postNews.getStar());
            postNewsVo.setType(postNews.getType());


            //加入redis方便维护点赞量和回复量
            redisTemplate.opsForZSet().add(RedisConstant.POST_NEWS_BELONGTO_ORDER+user.getNickname(),postNewsVo,postNews.getStar());
            redisTemplate.opsForHash().put(RedisConstant.POST_NEWS_BELONGTO+user.getNickname(),
                    RedisConstant.POST_NEWS_PREFIX+postNewsVo.getId(), postNewsVo);
            redisTemplate.opsForZSet().add(RedisConstant.POST_NEWS_COMMUNITY_ORDER+user.getCommunityId(),postNewsVo,postNews.getStar());
            redisTemplate.opsForHash().put(RedisConstant.POST_ALLNEWS,RedisConstant.POST_NEWS_PREFIX+postNewsVo.getId(),postNewsVo);
            PosterVo poster = (PosterVo) redisTemplate.opsForHash().get(RedisConstant.POSTERS_INFO, user.getNickname());
            poster.setPosts(redisTemplate.opsForValue().increment(RedisConstant.COUNTER_POST_NEWS+user.getNickname(),1).intValue());
            if (CollectionUtils.isEmpty(poster.getPostNewsList())){
                List<PostNewsVo> postNewsVoList=Lists.newArrayList();
                poster.setPostNewsList(postNewsVoList);
            }
            poster.getPostNewsList().add(postNewsVo);
            redisTemplate.opsForHash().put(RedisConstant.POSTERS_INFO,user.getNickname(),poster);
            postNewsVo.setPosted(new Date());
            return postNewsVo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PostNewsDetailVo getPostNewsDetail(Integer newsId) {
        try {
            User user = AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            PostNewsVo postNewsVo = (PostNewsVo) redisTemplate.opsForHash().get(
                    RedisConstant.POST_NEWS_BELONGTO + user.getNickname(), RedisConstant.POST_NEWS_PREFIX + newsId);
        PostNewsDetailVo detailVo=new PostNewsDetailVo();
        detailVo.setPostNewsVo(postNewsVo);
        detailVo.setReplyVos(getPostReplysByNewsId(newsId));
        return  detailVo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //从redis中取数据,提高查询性能,
    @Override
    public List<PostNewsVo> getAllPostNewsByPage(Integer pageNum, Integer pageSize) {
        try {
            return getAllPostNews(pageNum,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //热点数据入redis
    @Override
    public List<PostNewsVo> getNicePostNewsByPage(Integer pageNum, Integer pageSize) {
        try {
            List<PostNewsVo> postNewsVos = getAllPostNews(pageNum, pageSize);
            return postNewsVos.stream().filter(news->news.getType().intValue()!=0).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<PostReplyVo> getPostReplysByNewsId(Integer newsId) {
        List<PostReplyVo> replies=Lists.newArrayList();
        List<PostReply> replys=postReplyMapper.selectByPostNewsId(newsId);
        replys.stream().sorted(Comparator.comparing(PostReply::getReplyTime).reversed()).forEach(reply->{
            PostReplyVo postReplyVo=new PostReplyVo();
            BeanUtils.copyProperties(reply,postReplyVo);
            replies.add(postReplyVo);
        });
        return replies;
    }

    @Override
    public List<PostReplyVo> getPostReplysChildrenById(Integer replyId) {
        List<PostReplyVo> replies=Lists.newArrayList();
        Set<PostReply> replySet= Sets.newHashSet();
        getChildrenPostReplys(replySet,replyId).stream().forEach(postReply -> {
            PostReplyVo postReplyVo=new PostReplyVo();
            BeanUtils.copyProperties(postReply,postReplyVo);
            replies.add(postReplyVo);
        });
        return replies;
    }

    @Override
    public PostReplyVo createNewPostReply(PostReplyDto postReplyDto, BindingResult result) {
        ParamValidatorUtil.validateBindingResult(result);
        try {
            User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            PostReply postReply=new PostReply();
            BeanUtils.copyProperties(postReplyDto,postReply);
            if (postReplyDto.getParentId()==null){
                postReply.setParentId(0);
            }
            postReply.setIsParent(0);
            postReply.setReplyTime(new Date());
            postReplyMapper.insert(postReply);
            PostReplyVo postReplyVo=new PostReplyVo();
            BeanUtils.copyProperties(postReply,postReplyVo);
            PosterVo posterVo = (PosterVo) redisTemplate.opsForHash().get(RedisConstant.POSTERS_INFO, user.getNickname());
            posterVo.setReplys(redisTemplate.opsForValue().increment(RedisConstant.COUNTER_POST_REPLYS+user.getNickname(),1).intValue());
            if (CollectionUtils.isEmpty(posterVo.getPostReplyList())){
                List<PostReplyVo> replyVos=Lists.newArrayList();
                posterVo.setPostReplyList(replyVos);
            }
            posterVo.getPostReplyList().add(postReplyVo);
            redisTemplate.opsForHash().put(RedisConstant.POSTERS_INFO,user.getNickname(),posterVo);

            //评论非热点数据,直接加入数据库
//            postReplyMapper.insert(postReply);
            return postReplyVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PosterVo followPoster(String nickname) {
        try {
            boolean isExists = redisTemplate.opsForHash().hasKey(RedisConstant.POSTERS_INFO, nickname).booleanValue();
            if (!isExists){
                throw new ParamException("用户不存在,关注失败");
            }

            User user = AuthenticationInfoUtil.getUser(userMapper, stringRedisTemplate);
            PosterVo poster = (PosterVo) redisTemplate.opsForHash().get(RedisConstant.POSTERS_INFO, nickname);
            PosterVo follower = (PosterVo) redisTemplate.opsForHash().get(RedisConstant.POSTERS_INFO, user.getNickname());
            if (!CollectionUtils.isEmpty(follower.getFollowings())){
                boolean contains = CollectionUtils.contains(follower.getFollowings().iterator(), nickname);
                if (contains){
                    throw new ParamException("用户已经关注,请勿重复关注");
                }
            }
            follower.setFollowingNums(redisTemplate.opsForValue().increment(RedisConstant.COUNTER_FOLLOWING+user.getNickname(),1).intValue());
//            if (CollectionUtils.isEmpty(follower.getFollowings())){
//                List<PosterVo> following=Lists.newArrayList();
//                following.add(poster);
//                follower.setFollowings(following);
//            }else{
//                follower.getFollowings().add(poster);
//            }
//            follower.getFollowings().add(nickname);
            if (CollectionUtils.isEmpty(follower.getFollowings())){
                List<String> followings=Lists.newArrayList();
                follower.setFollowings(followings);
            }
            follower.getFollowings().add(nickname);

            redisTemplate.opsForHash().put(RedisConstant.POSTERS_INFO,user.getNickname(),follower);
            poster.setFollowerNums(redisTemplate.opsForValue().increment(RedisConstant.COUNTER_FOLLOWER+nickname,1).intValue());
//            if (CollectionUtils.isEmpty(poster.getFollowers())){
//                List<PosterVo> followers=Lists.newArrayList();
//                followers.add(follower);
//                poster.setFollowers(followers);
//            }else{
//                poster.getFollowers().add(follower);
//            }
//            poster.getFollowers().add(user.getNickname());
            if (CollectionUtils.isEmpty(poster.getFollowers())){
                List<String> followers=Lists.newArrayList();
                poster.setFollowers(followers);
            }
            poster.getFollowers().add(user.getNickname());
            redisTemplate.opsForHash().put(RedisConstant.POSTERS_INFO,nickname,poster);
            return follower;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PosterVo collectPostNews(Integer newsId) {
        try {
            boolean b = redisTemplate.opsForHash().hasKey(RedisConstant.POST_ALLNEWS, RedisConstant.POST_NEWS_PREFIX + newsId).booleanValue();
            if (!b){
                throw new ParamException("帖子不存在,收藏失败");
            }
            PostNewsVo postNewsVo= (PostNewsVo) redisTemplate.opsForHash().get(RedisConstant.POST_ALLNEWS, RedisConstant.POST_NEWS_PREFIX + newsId);
            User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            PosterVo posterVo = (PosterVo) redisTemplate.opsForHash().get(RedisConstant.POSTERS_INFO, user.getNickname());
            posterVo.setCollections(redisTemplate.opsForValue().increment(RedisConstant.COUNTER_COLLECTIONS+user.getNickname(),1).intValue());
            if (CollectionUtils.isEmpty(posterVo.getCollectNews())){
                List<PostNewsVo> postNewsVoList=Lists.newArrayList();
                posterVo.setCollectNews(postNewsVoList);
            }
            posterVo.getCollectNews().add(postNewsVo);
            redisTemplate.opsForHash().put(RedisConstant.POSTERS_INFO,user.getNickname(),posterVo);
            return posterVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<PostNewsVo> getAllPostNews(Integer pageNum,Integer pageSize) {
        List<PostNewsVo> postNewsVoList= Lists.newArrayList();
        User user = AuthenticationInfoUtil.getUser(userMapper, stringRedisTemplate);
        redisTemplate.opsForZSet().reverseRange(RedisConstant.POST_NEWS_COMMUNITY_ORDER
                +user.getCommunityId(), pageNum - 1, pageSize).stream().forEach(e->{
            postNewsVoList.add((PostNewsVo) e);
        });
        if (CollectionUtils.isEmpty(postNewsVoList)){
            PostNews postNews=postNewsMapper.selectByCommunityId(user.getCommunityId());
            PostNewsVo postNewsVo=new PostNewsVo();
            BeanUtils.copyProperties(postNews,postNewsVo);
            postNewsVoList.add(postNewsVo);
        }
        return postNewsVoList;
    }

    private Set<PostReply> getChildrenPostReplys(Set<PostReply> postReplies,Integer replyId){
        PostReply postReply1 = postReplyMapper.selectByPrimaryKey(replyId);
        if (null!=postReply1){
            postReplies.add(postReply1);
        }
        List<PostReply> replies = postReplyMapper.selectByPostNewsParentId(replyId);
        if (!CollectionUtils.isEmpty(replies)){
            for (PostReply postReply:replies){
                getChildrenPostReplys(postReplies,postReply.getId());
            }
        }
        return postReplies;

    }
}
