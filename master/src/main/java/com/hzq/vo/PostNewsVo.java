package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hzq.serializable.Date2LongSerializer;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostNewsVo implements Serializable {

    private Integer id;

    private String title;

    private String description;

    private String imgUrl;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date posted;

    private Integer star;

    private Integer comments;

    private Byte type;

    private Integer userId;

    private String newsDetail;
}
