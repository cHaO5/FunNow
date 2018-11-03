package com.soa.FunNow.modules.main.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    @SerializedName("rating")
    private RatingEntity rating;
    @SerializedName("collect_count")
    private int collect_count;
    @SerializedName("title")
    private String title;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("subtype")
    private String subtype;
    @SerializedName("year")
    private String year;
    @SerializedName("images")
    private ImagesEntity images;
    @SerializedName("alt")
    private String alt;
    @SerializedName("id")
    private String id;
    @SerializedName("genres")
    private List<String> genres;
    @SerializedName("casts")
    private List<CelebrityEntity> casts;
    @SerializedName("directors")
    private List<CelebrityEntity> directors;

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setCasts(List<CelebrityEntity> casts) {
        this.casts = casts;
    }

    public void setDirectors(List<CelebrityEntity> directors) {
        this.directors = directors;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getYear() {
        return year;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<CelebrityEntity> getCasts() {
        return casts;
    }

    public List<CelebrityEntity> getDirectors() {
        return directors;
    }

}
