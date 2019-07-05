package com.hzq.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class WatersuplyDetails implements Serializable {
    private Integer id;

    private Integer suplyId;

    @NotNull(message = "水的品牌不能为空")
    private Integer brandId;

    @Min(value = 1,message = "数量不能少于1")
    private Integer waterNums;

    private WaterBrand waterBrand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSuplyId() {
        return suplyId;
    }

    public void setSuplyId(Integer suplyId) {
        this.suplyId = suplyId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getWaterNums() {
        return waterNums;
    }

    public void setWaterNums(Integer waterNums) {
        this.waterNums = waterNums;
    }

    public WaterBrand getWaterBrand() {
        return waterBrand;
    }

    public void setWaterBrand(WaterBrand waterBrand) {
        this.waterBrand = waterBrand;
    }
}