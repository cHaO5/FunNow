package com.soa.FunNow.modules.main.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImagesEntity implements Serializable {
    @SerializedName("small")
    private String small;
    @SerializedName("large")
    private String large;
    @SerializedName("medium")
    private String medium;

    public void setSmall(String small) {
        this.small = small;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }
}
