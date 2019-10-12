package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsCoverStoryModel implements Serializable {

    @SerializedName("ID_NEWS")
    private String idNews;
    @SerializedName("NAME_CATEGORY")
    private String nameCategory;
    @SerializedName("CONTENT_NEWS")
    private String contentNews;
    @SerializedName("TITLE_NEWS")
    private String titleNews;
    @SerializedName("DATE_NEWS")
    private String dateNews;
    @SerializedName("NEWS_IMAGE")
    private String newsImage;
    @SerializedName("VIEWS_COUNT")
    private int viewsCount;
    @SerializedName("LIKES")
    private int likes;
    @SerializedName("COMMENTS")
    private int comments;
    @SerializedName("SHARES_COUNT")
    private int sharesCount;
    @SerializedName("ID_COVERSTORY")
    private String idCoverStory;
    @SerializedName("TITLE_COVERSTORY")
    private String titleCoverStory;

    public NewsCoverStoryModel(String idNews, String nameCategory, String contentNews, String titleNews, String dateNews, String newsImage, int viewsCount, int likes, int comments, int sharesCount, String idCoverStory, String titleCoverStory) {
        this.idNews = idNews;
        this.nameCategory = nameCategory;
        this.contentNews = contentNews;
        this.titleNews = titleNews;
        this.dateNews = dateNews;
        this.newsImage = newsImage;
        this.viewsCount = viewsCount;
        this.likes = likes;
        this.comments = comments;
        this.sharesCount = sharesCount;
        this.idCoverStory = idCoverStory;
        this.titleCoverStory = titleCoverStory;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getContentNews() {
        return contentNews;
    }

    public void setContentNews(String contentNews) {
        this.contentNews = contentNews;
    }

    public String getTitleNews() {
        return titleNews;
    }

    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;
    }

    public String getDateNews() {
        return dateNews;
    }

    public void setDateNews(String dateNews) {
        this.dateNews = dateNews;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(int sharesCount) {
        this.sharesCount = sharesCount;
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
}
