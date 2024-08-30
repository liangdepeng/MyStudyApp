package com.dpim.application;

import android.text.TextUtils;

public class UserInfoBean {

    private String userId;
    private String userName;
    private String userLogoUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogoUrl() {
        if (TextUtils.isEmpty(userLogoUrl))
            userLogoUrl = "_";
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }
}
