package com.example.siki.model;

public class ProductImage {
    private Long id;
    private Product product;
    private String path;
    private boolean isDefault;

    public ProductImage() {
    }

    public ProductImage(Long id, Product product, String path, boolean isDefault) {
        this.id = id;
        this.product = product;
        this.path = path;
        this.isDefault = isDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", product=" + product +
                ", path='" + path + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
