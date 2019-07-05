package com.hzq.dto;

import com.hzq.constant.MyConstant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SecretaryBookDto {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = MyConstant.Validation.PHONE_REGEX,message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Length(max = 500,message = "描述内容过多")
    private String description;
}
