package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostNewsDetailVo implements Serializable {

    @JsonProperty("post_news")
    private PostNewsVo postNewsVo;

    @JsonProperty("post_replys")
    private List<PostReplyVo> replyVos;
}
