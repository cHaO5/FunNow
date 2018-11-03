package com.soa.FunNow.modules.main.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CelebrityEntity implements Serializable {
    @SerializedName("avatars")
    private ImagesEntity avatars;
    @SerializedName("alt")
    private String alt;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public void setAvatars(ImagesEntity avatars) {
        this.avatars = avatars;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImagesEntity getAvatars() {
        return avatars;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
