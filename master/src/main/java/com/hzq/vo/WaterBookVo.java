package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hzq.domain.WatersuplyDetails;
import com.hzq.serializable.Date2LongSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaterBookVo {
    private Integer id;

    private String phone;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date hopeTime;

    private String address;

    private String description;

    private List<WatersuplyDetails> detailsList;
}
