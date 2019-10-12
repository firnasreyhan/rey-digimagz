package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureStory {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<StoryModel> data;

    public DefaultStructureStory(boolean status, ArrayList<StoryModel> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<StoryModel> getData() {
        return data;
    }

    public void setData(ArrayList<StoryModel> data) {
        this.data = data;
    }
}
