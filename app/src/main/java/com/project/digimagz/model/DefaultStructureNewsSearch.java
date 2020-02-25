package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureNewsSearch {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private NewsModel data;

    public DefaultStructureNewsSearch(boolean status, NewsModel data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public NewsModel getData() {
        return data;
    }

    public void setData(NewsModel data) {
        this.data = data;
    }
}
