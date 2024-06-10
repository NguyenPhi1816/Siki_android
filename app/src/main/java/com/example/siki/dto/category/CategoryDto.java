package com.example.siki.dto.category;

public class CategoryDto {
    private Integer id;
    private String name;
    private String image;


    public CategoryDto(Integer id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public CategoryDto() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
