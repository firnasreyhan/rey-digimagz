package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

public class VideoModel {

    @SerializedName("ID_VIDEO")
    private String idVideo;
    @SerializedName("TITLE")
    private String title;
    @SerializedName("DESCRIPTION")
    private String description;
    @SerializedName("DATE_PUBLISHED")
    private String datePublished;
    @SerializedName("URL_DEFAULT_THUMBNAIL")
    private String urlDefaultThumbnail;
    @SerializedName("URL_MEDIUM_THUMBNAIL")
    private String urlMediumThumbnail;
    @SerializedName("URL_HIGH_THUMBNAIL")
    private String urlLargeThumbnail;
    @SerializedName("STATUS_PUBLISHED")
    private String statusPublished;

    public VideoModel(String idVideo, String title, String description, String datePublished, String urlDefaultThumbnail, String urlMediumThumbnail, String urlLargeThumbnail, String statusPublished) {
        this.idVideo = idVideo;
        this.title = title;
        this.description = description;
        this.datePublished = datePublished;
        this.urlDefaultThumbnail = urlDefaultThumbnail;
        this.urlMediumThumbnail = urlMediumThumbnail;
        this.urlLargeThumbnail = urlLargeThumbnail;
        this.statusPublished = statusPublished;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getUrlDefaultThumbnail() {
        return urlDefaultThumbnail;
    }

    public void setUrlDefaultThumbnail(String urlDefaultThumbnail) {
        this.urlDefaultThumbnail = urlDefaultThumbnail;
    }

    public String getUrlMediumThumbnail() {
        return urlMediumThumbnail;
    }

    public void setUrlMediumThumbnail(String urlMediumThumbnail) {
        this.urlMediumThumbnail = urlMediumThumbnail;
    }

    public String getUrlLargeThumbnail() {
        return urlLargeThumbnail;
    }

    public void setUrlLargeThumbnail(String urlLargeThumbnail) {
        this.urlLargeThumbnail = urlLargeThumbnail;
    }

    public String getStatusPublished() {
        return statusPublished;
    }

    public void setStatusPublished(String statusPublished) {
        this.statusPublished = statusPublished;
    }
}
