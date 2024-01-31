package com.dpjh.developtools.activitytools;

public class AppInfoBean {

    private String currentPackageName;
    private String CurrentActivity;
    private Object iconUrl;
    private String appName;

    public String getCurrentPackageName() {
        return currentPackageName;
    }

    public void setCurrentPackageName(String currentPackageName) {
        this.currentPackageName = currentPackageName;
    }

    public String getCurrentActivity() {
        return CurrentActivity;
    }

    public void setCurrentActivity(String currentActivity) {
        CurrentActivity = currentActivity;
    }

    public Object getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(Object iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "currentPackageName='" + currentPackageName + '\'' +
                ", CurrentActivity='" + CurrentActivity + '\'' +
                ", iconUrl=" + iconUrl +
                ", appName='" + appName + '\'' +
                '}';
    }
}
