package com.hzq.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseProduct implements Serializable {
    @Id
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEvaluationNums() {
        return evaluationNums;
    }

    public void setEvaluationNums(Integer evaluationNums) {
        this.evaluationNums = evaluationNums;
    }

    public Integer getGoodEvaluationNums() {
        return goodEvaluationNums;
    }

    public void setGoodEvaluationNums(Integer goodEvaluationNums) {
        this.goodEvaluationNums = goodEvaluationNums;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages == null ? null : subImages.trim();
    }

    public List<PurchaseProductSku> getPurchaseProductSkus() {
        return purchaseProductSkus;
    }

    public void setPurchaseProductSkus(List<PurchaseProductSku> purchaseProductSkus) {
        this.purchaseProductSkus = purchaseProductSkus;
    }

    public Double getGoodRatio() {
        return goodRatio;
    }

    public void setGoodRatio(Double goodRatio) {
        this.goodRatio = goodRatio;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}