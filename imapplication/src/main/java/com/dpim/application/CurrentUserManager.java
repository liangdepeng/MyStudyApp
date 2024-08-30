package com.dpim.application;

import com.dpim.application.base.SPUtils;

public class CurrentUserManager {

    private String userId;
    private String token;
    private String userName;
    private String userLogoUrl;


    private final static class Instance {
        private final static CurrentUserManager manager = new CurrentUserManager();
    }

    public static CurrentUserManager getInstance() {
        return Instance.manager;
    }

    private CurrentUserManager() {
    }

    public void clear() {
        userId = "";
        userName = "";
        userLogoUrl = "";
        token = "";
        SPUtils.put(SPUtils.Keys.currentUserId, userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        SPUtils.put(SPUtils.Keys.currentUserId, userId);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogoUrl() {
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }
}
