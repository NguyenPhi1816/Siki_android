package com.example.siki.dto;

public class StoreDto {
    private Integer id;
    private String name;

    public StoreDto() {
    }

    public StoreDto(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
