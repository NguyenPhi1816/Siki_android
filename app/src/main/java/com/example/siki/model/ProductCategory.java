package com.example.siki.model;

public class ProductCategory {
    private Long id;
    private Long productId;
    private Integer categoryId;

    public ProductCategory(Long id, Long productId, Integer categoryId) {
        this.id = id;
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public ProductCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
