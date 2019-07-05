package com.hzq.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hzq.constant.MyConstant;
import com.hzq.serializable.Long2DateDeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Setter
@Getter
public class RepairBookDto {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp =MyConstant.Validation.PHONE_REGEX,message = "手机号格式不正确")
    private String telephone;

    @Length(max = 150,message = "地址过长")
    @NotBlank(message = "地址不能为空")
    private String address;

    @Length(max = 500,message = "描述信息过长")
    private String description;

    @JsonDeserialize(using = Long2DateDeSerializer.class)
    private Date hopeTime;
}
