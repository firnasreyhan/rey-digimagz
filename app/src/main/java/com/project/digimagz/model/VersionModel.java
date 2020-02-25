package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

public class VersionModel {
    @SerializedName("version")
    private String version;

    public VersionModel(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
