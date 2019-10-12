package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureUser {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<UserModel> data;

    public DefaultStructureUser(boolean status, ArrayList<UserModel> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<UserModel> getData() {
        return data;
    }

    public void setData(ArrayList<UserModel> data) {
        this.data = data;
    }
}
