package com.example.siki.dto.product;

public class ProductDto {

    private Long id;
    private String name;
    private String slug;
    private String image;
    private Double price;
    private float averageRating;
    private long soldNum;
    private Long productId;

    public ProductDto(Long id, String name, String slug, String image, Double price, float averageRating, long soldNum, Long productId) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.image = image;
        this.price = price;
        this.averageRating = averageRating;
        this.soldNum = soldNum;
        this.productId = productId;
    }

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public long getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(long soldNum) {
        this.soldNum = soldNum;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
