package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureNews {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<NewsModel> data;

    public DefaultStructureNews(boolean status, ArrayList<NewsModel> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<NewsModel> getData() {
        return data;
    }

    public void setData(ArrayList<NewsModel> data) {
        this.data = data;
    }
}
