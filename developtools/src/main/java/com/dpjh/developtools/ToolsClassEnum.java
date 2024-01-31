package com.dpjh.developtools;

import com.dpjh.developtools.activitytools.ActivityToolsPage;

public enum ToolsClassEnum {

    ACTIVITY_GET_CURRENT_PAGE_NAME("当前Activity", R.mipmap.ic_launcher, ActivityToolsPage.class);

    private String title;
    private Object icon;
    private Class<?> activityClass;

    ToolsClassEnum(String title, Object icon, Class<?> activityClass) {
        this.title = title;
        this.icon = icon;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<?> activityClass) {
        this.activityClass = activityClass;
    }
}
