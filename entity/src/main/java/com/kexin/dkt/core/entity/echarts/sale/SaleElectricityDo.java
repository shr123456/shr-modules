package com.kexin.dkt.core.entity.echarts.sale;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @auther shr
 * @date 2017/11/2
 * @time 11:41
 * @desc
 **/
public class SaleElectricityDo {
    private int id;
    private String collectDate;
    private String customerId;
    private String userId;
    private Integer genre;
    private double electric;
    private String collectConfigName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public double getElectric() {
        return electric;
    }

    public void setElectric(double electric) {
        electric = Math.ceil(electric);
        this.electric = electric;
    }

    public String getCollectConfigName() {
        return collectConfigName;
    }

    public void setCollectConfigName(String collectConfigName) {
        this.collectConfigName = collectConfigName;
    }
}
