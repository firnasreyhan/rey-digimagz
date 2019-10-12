package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentModel implements Serializable {

    @SerializedName("ID_NEWS")
    private String idNews;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("COMMENT_TEXT")
    private String commentText;
    @SerializedName("IS_APPROVED")
    private boolean isApproved;
    @SerializedName("DATE_COMMENT")
    private String dateComment;

    public CommentModel(String idNews, String email, String commentText, boolean isApproved, String dateComment) {
        this.idNews = idNews;
        this.email = email;
        this.commentText = commentText;
        this.isApproved = isApproved;
        this.dateComment = dateComment;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getDateComment() {
        return dateComment;
    }

    public void setDateComment(String dateComment) {
        this.dateComment = dateComment;
    }
}
