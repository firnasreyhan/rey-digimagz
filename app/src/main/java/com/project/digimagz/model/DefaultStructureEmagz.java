package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultStructureEmagz {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<EmagzModel> data;

    public DefaultStructureEmagz(boolean status, ArrayList<EmagzModel> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<EmagzModel> getData() {
        return data;
    }

    public void setData(ArrayList<EmagzModel> data) {
        this.data = data;
    }
}
