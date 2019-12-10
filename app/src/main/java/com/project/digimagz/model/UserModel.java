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
    @SerializedName("DATE_BIRTH")
    private String dateBirth;
    @SerializedName("GENDER")
    private String gender;
    @SerializedName("USER_TYPE")
    private String userType;
    @SerializedName("PASSWORD")
    private String password;

    public UserModel(String email, String userName, String urlPic, String lastLogin, String dateBirth, String gender, String userType, String password) {
        this.email = email;
        this.userName = userName;
        this.urlPic = urlPic;
        this.lastLogin = lastLogin;
        this.dateBirth = dateBirth;
        this.gender = gender;
        this.userType = userType;
        this.password = password;
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

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
