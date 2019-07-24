package com.hzq.service;

import com.hzq.dto.PostNewsDto;
import com.hzq.dto.PostReplyDto;
import com.hzq.vo.PostNewsDetailVo;
import com.hzq.vo.PostNewsVo;
import com.hzq.vo.PostReplyVo;
import com.hzq.vo.PosterVo;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PostNewsService {
    /**
     * 创建新帖
     * @param postNewsDto
     * @param result
     * @return
     */
    PostNewsVo createPostNews(PostNewsDto postNewsDto, BindingResult result);

    /**
     * 新帖详情
     * @param newsId
     * @return
     */
    PostNewsDetailVo getPostNewsDetail(Integer newsId);

    /**
     * 获取所有帖子
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PostNewsVo> getAllPostNewsByPage(Integer pageNum, Integer pageSize);
    List<PostNewsVo> getNicePostNewsByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据帖子的ID获取本条帖子的所有回帖
     * @param newsId
     * @return
     */
    List<PostReplyVo> getPostReplysByNewsId(Integer newsId);


    List<PostReplyVo> getPostReplysChildrenById(Integer replyId);

    /**
     * 创建回帖
     * @param postReplyDto
     * @param result
     * @return
     */
    PostReplyVo createNewPostReply(PostReplyDto postReplyDto, BindingResult result);

    /**
     * 获取用户详情
     * @param nickname
     * @return
     */
    PosterVo followPoster(String nickname);

    PosterVo collectPostNews(Integer newsId);
}
