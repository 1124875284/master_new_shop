package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hzq.domain.PurchaseProductSku;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;
    private String subtitle;
    private String detail;

    private Integer status;

    private Integer evaluationNums;

    private Integer goodEvaluationNums;
    private String mainImage;
    private String subImages;

    private Double goodRatio;

    private List<PurchaseProductSku> purchaseProductSkus;
}
