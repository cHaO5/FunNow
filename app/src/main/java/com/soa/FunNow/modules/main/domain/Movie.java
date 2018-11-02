package com.soa.FunNow.modules.main.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("poster")
    public String poster;

    public boolean isValid() {
        return id != null && title != null && poster != null;
    }

}
