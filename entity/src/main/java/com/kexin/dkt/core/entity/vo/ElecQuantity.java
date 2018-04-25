package com.kexin.dkt.core.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by kexin on 2017/10/24.
 */
public class ElecQuantity {

    private String id;
    private Double sumElectric;
    private Double peakElectric;
    private Double valleyElectric;
    private Double flatElectric;
    private Date collectDate;
    private String dateStr;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    private Double maxTotal;

    public Double getMaxTotal() {
        if (maxTotal == null)
            maxTotal = 0.0;
        return maxTotal;
    }

    public void setMaxTotal(Double maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getSumElectric() {
        if (sumElectric == null)
            sumElectric = 0.0;
        return sumElectric;
    }

    public void setSumElectric(Double sumElectric) {
        this.sumElectric = sumElectric;
    }

    public Double getPeakElectric() {
        if (peakElectric == null)
            peakElectric = 0.0;
        return peakElectric;
    }

    public void setPeakElectric(Double peakElectric) {

        this.peakElectric = peakElectric;
    }

    public Double getValleyElectric() {
        if (valleyElectric == null)
            valleyElectric = 0.0;
        return valleyElectric;
    }

    public void setValleyElectric(Double valleyElectric) {

        this.valleyElectric = valleyElectric;
    }

    public Double getFlatElectric() {
        if (flatElectric == null)
            flatElectric = 0.0;
        return flatElectric;
    }

    public void setFlatElectric(Double flatElectric) {

        this.flatElectric = flatElectric;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }
}
