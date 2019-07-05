package com.hzq.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseProductSku implements Serializable {
    private Integer id;

    private String attributeName;

    private Integer sales;

    private Integer stock;

    private BigDecimal price;

    private BigDecimal spellPrice;

    private Integer productId;

    private PurchaseProduct purchaseProduct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName == null ? null : attributeName.trim();
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSpellPrice() {
        return spellPrice;
    }

    public void setSpellPrice(BigDecimal spellPrice) {
        this.spellPrice = spellPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public PurchaseProduct getPurchaseProduct() {
        return purchaseProduct;
    }

    public void setPurchaseProduct(PurchaseProduct purchaseProduct) {
        this.purchaseProduct = purchaseProduct;
    }
}