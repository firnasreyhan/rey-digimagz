package com.project.digimagz.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("EMAIL")
    private String email;
    @SerializedName("USER_NAME")
    private String userName;
    @SerializedName("PROFILEPIC_URL")
    private String urlPic;
    @SerializedName("LAST_LOGIN")
    private String lastLogin;

    public UserModel(String email, String userName, String urlPic, String lastLogin) {
        this.email = email;
        this.userName = userName;
        this.urlPic = urlPic;
        this.lastLogin = lastLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrlPic() {
        return urlPic;
    }

    public void setUrlPic(String urlPic) {
        this.urlPic = urlPic;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
