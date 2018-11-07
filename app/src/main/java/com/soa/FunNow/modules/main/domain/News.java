package com.soa.FunNow.modules.main.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {
    @SerializedName("url_3w")
    private String url_3w;
    @SerializedName("source")
    private String source;
    @SerializedName("title")
    private String title;
    @SerializedName("mtime")
    private String mtime;
    @SerializedName("url")
    private String url;
    @SerializedName("digest")
    private String digest;
    @SerializedName("imgsrc")
    private String imgsrc;

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
