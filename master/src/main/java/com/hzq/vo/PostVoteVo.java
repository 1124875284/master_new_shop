package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hzq.serializable.Date2LongSerializer;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostVoteVo {
    private Integer id;

    private String title;

    private String description;

    private Integer voteTotal;

    private Integer choice;

    private Integer userId;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date validityTime;

    private List<VoteOptionVo> optionList;
}
