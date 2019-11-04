package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureVideo {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<VideoModel> data;

    public DefaultStructureVideo(boolean status, ArrayList<VideoModel> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<VideoModel> getData() {
        return data;
    }

    public void setData(ArrayList<VideoModel> data) {
        this.data = data;
    }
}
