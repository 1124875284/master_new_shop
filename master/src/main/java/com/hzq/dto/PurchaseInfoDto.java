package com.hzq.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInfoDto implements Serializable {
    @Id
    private Integer id;

    private String category;

    private String name;

    private String subtitle;

    private String detail;

    private String mainImage;

    private String subImages;

    private Double goodRatio;

    private Integer sales;

    private BigDecimal price;

    private BigDecimal spellPrice;
}
