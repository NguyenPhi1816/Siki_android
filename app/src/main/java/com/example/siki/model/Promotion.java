package com.example.siki.model;

import java.io.Serializable;
import java.util.Date;

public class Promotion implements Serializable {
    private Long id;
    private String name;
    private String reason;
    private int percentPromotion;
    private Date startDate;
    private Date endDate;
    private Integer idCategory;
    private String imagePath;
    public Promotion() {
    }

    public Promotion(Long id, String name, String reason, int percentPromotion, Date startDate, Date endDate, Integer idCategory, String imagePath) {
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.percentPromotion = percentPromotion;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idCategory = idCategory;
        this.imagePath = imagePath;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
