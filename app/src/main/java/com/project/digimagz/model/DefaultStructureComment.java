package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureComment {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<CommentModel> data;

    public DefaultStructureComment(boolean status, ArrayList<CommentModel> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<CommentModel> getData() {
        return data;
    }

    public void setData(ArrayList<CommentModel> data) {
        this.data = data;
    }
}
