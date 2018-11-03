package com.soa.FunNow.modules.main.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RatingEntity implements Serializable {
    @SerializedName("max")
    private int max;
    @SerializedName("average")
    private double average;
    @SerializedName("stars")
    private String stars;
    @SerializedName("min")
    private int min;

    public void setMax(int max) {
        this.max = max;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public double getAverage() {
        return average;
    }

    public String getStars() {
        return stars;
    }

    public int getMin() {
        return min;
    }
}
