package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoryModel implements Serializable {

    @SerializedName("ID_COVERSTORY")
    private String idCoverStory;
    @SerializedName("TITLE_COVERSTORY")
    private String titleCoverStory;
    @SerializedName("SUMMARY")
    private String summary;
    @SerializedName("IMAGE_COVERSTORY")
    private String imageCoverStory;
    @SerializedName("DATE_COVERSTORY")
    private String dateCoverStory;

    public StoryModel(String idCoverStory, String titleCoverStory, String summary, String imageCoverStory, String dateCoverStory) {
        this.idCoverStory = idCoverStory;
        this.titleCoverStory = titleCoverStory;
        this.summary = summary;
        this.imageCoverStory = imageCoverStory;
        this.dateCoverStory = dateCoverStory;
    }

    public String getIdCoverStory() {
        return idCoverStory;
    }

    public void setIdCoverStory(String idCoverStory) {
        this.idCoverStory = idCoverStory;
    }

    public String getTitleCoverStory() {
        return titleCoverStory;
    }

    public void setTitleCoverStory(String titleCoverStory) {
        this.titleCoverStory = titleCoverStory;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImageCoverStory() {
        return imageCoverStory;
    }

    public void setImageCoverStory(String imageCoverStory) {
        this.imageCoverStory = imageCoverStory;
    }

    public String getDateCoverStory() {
        return dateCoverStory;
    }

    public void setDateCoverStory(String dateCoverStory) {
        this.dateCoverStory = dateCoverStory;
    }
}
