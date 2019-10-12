package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureNewsCoverStory {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<NewsCoverStoryModel> data;

    public DefaultStructureNewsCoverStory(boolean status, ArrayList<NewsCoverStoryModel> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<NewsCoverStoryModel> getData() {
        return data;
    }

    public void setData(ArrayList<NewsCoverStoryModel> data) {
        this.data = data;
    }
}
