package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hzq.domain.JournalismContent;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JournalismVo {
    private String id;

    private String title;

    private String description;

    private String images;

    private String videos;

    private Date publishTime;

    private String publishName;

    private String author;

    private Integer commentNums;

    private Integer starNums;

    private List<JournalismContent> contents;

}
