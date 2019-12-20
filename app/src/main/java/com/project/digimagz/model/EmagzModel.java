package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

public class EmagzModel {

    @SerializedName("ID_EMAGZ")
    private String idEmagz;
    @SerializedName("THUMBNAIL")
    private String thumbnail;
    @SerializedName("FILE")
    private String file;
    @SerializedName("DATE_UPLOADED")
    private String dateUploaded;
    @SerializedName("NAME")
    private String name;

    public EmagzModel(String idEmagz, String thumbnail, String file, String dateUploaded, String name) {
        this.idEmagz = idEmagz;
        this.thumbnail = thumbnail;
        this.file = file;
        this.dateUploaded = dateUploaded;
        this.name = name;
    }

    public String getIdEmagz() {
        return idEmagz;
    }

    public void setIdEmagz(String idEmagz) {
        this.idEmagz = idEmagz;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
