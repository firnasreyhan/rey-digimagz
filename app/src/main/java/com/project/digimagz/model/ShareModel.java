package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

public class ShareModel {

    @SerializedName("id_news")
    private String idNews;
    @SerializedName("email")
    private String email;

    public ShareModel(String idNews, String email) {
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
