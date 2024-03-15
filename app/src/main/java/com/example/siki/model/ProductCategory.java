package com.example.siki.model;

public class ProductCategory {
    private Long id;
    private Long productId;
    private Long categoryId;

    public ProductCategory(Long id, Long productId, Long categoryId) {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
