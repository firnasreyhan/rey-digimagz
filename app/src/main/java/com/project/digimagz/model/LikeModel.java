package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LikeModel implements Serializable {

    @SerializedName("ID_NEWS")
    private String idNews;
    @SerializedName("EMAIL")
    private String email;

    public LikeModel(String idNews, String email) {
        this.idNews = idNews;
        this.email = email;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
