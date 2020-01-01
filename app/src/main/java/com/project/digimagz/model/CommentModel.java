package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentModel implements Serializable {

    @SerializedName("ID_COMMENT")
    private String idNComment;
    @SerializedName("ID_NEWS")
    private String idNews;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("COMMENT_TEXT")
    private String commentText;
    @SerializedName("IS_APPROVED")
    private String isApproved;
    @SerializedName("DATE_COMMENT")
    private String dateComment;
    @SerializedName("USER_NAME")
    private String userName;
    @SerializedName("PROFILEPIC_URL")
    private String profilpicUrl;
    @SerializedName("ADMIN_REPLY")
    private String adminReply;

    public CommentModel(String idNComment, String idNews, String email, String commentText, String isApproved, String dateComment, String userName, String profilpicUrl, String adminReply) {
        this.idNComment = idNComment;
        this.idNews = idNews;
        this.email = email;
        this.commentText = commentText;
        this.isApproved = isApproved;
        this.dateComment = dateComment;
        this.userName = userName;
        this.profilpicUrl = profilpicUrl;
        this.adminReply = adminReply;
    }

    public String getIdNComment() {
        return idNComment;
    }

    public void setIdNComment(String idNComment) {
        this.idNComment = idNComment;
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

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getDateComment() {
        return dateComment;
    }

    public void setDateComment(String dateComment) {
        this.dateComment = dateComment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilpicUrl() {
        return profilpicUrl;
    }

    public void setProfilpicUrl(String profilpicUrl) {
        this.profilpicUrl = profilpicUrl;
    }

    public String getAdminReply() {
        return adminReply;
    }

    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }
}
