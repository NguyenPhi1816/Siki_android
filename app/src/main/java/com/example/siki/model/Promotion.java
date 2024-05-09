package com.example.siki.model;

import java.io.Serializable;
import java.util.Date;

public class Promotion implements Serializable {
    private Long id;
    private String name;
    private String reason;
    private int percentPromotion;
    private String startDate;
    private String endDate;
    private String nameCategory;
    private String imagePath;
    public Promotion() {
    }

    public Promotion(String name, String reason, int percentPromotion, String startDate, String endDate, String nameCategory, String imagePath) {
        this.name = name;
        this.reason = reason;
        this.percentPromotion = percentPromotion;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nameCategory = nameCategory;
        this.imagePath = imagePath;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPercentPromotion() {
        return percentPromotion;
    }

    public void setPercentPromotion(int percentPromotion) {
        this.percentPromotion = percentPromotion;
    }
}
