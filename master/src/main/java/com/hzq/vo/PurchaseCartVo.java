package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hzq.dto.PurchaseCartItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseCartVo implements Serializable {
    private String nickname;
    private List<PurchaseCartItemDto> cartItems;
}
