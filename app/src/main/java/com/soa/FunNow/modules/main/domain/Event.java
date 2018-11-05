package com.soa.FunNow.modules.main.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Event implements Serializable {
    @SerializedName("image")
    private String image;
    @SerializedName("adapt_url")
    private String adapt_url;
    @SerializedName("loc_name")
    private String loc_name;
    @SerializedName("id")
    private String id;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("tags")
    private String tags;
    @SerializedName("image_lmobile")
    private String image_lmobile;
    @SerializedName("image_hlarge")
    private String image_hlarge;
    @SerializedName("begin_time")
    private String begin_time;
    @SerializedName("end_time")
    private String end_time;
    @SerializedName("address")
    private String address;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdapt_url() {
        return adapt_url;
    }

    public void setAdapt_url(String adapt_url) {
        this.adapt_url = adapt_url;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImage_lmobile() {
        return image_lmobile;
    }

    public void setImage_lmobile(String image_lmobile) {
        this.image_lmobile = image_lmobile;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_hlarge() {
        return image_hlarge;
    }

    public void setImage_hlarge(String image_hlarge) {
        this.image_hlarge = image_hlarge;
    }
}
