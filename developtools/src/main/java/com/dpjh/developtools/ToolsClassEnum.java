package com.dpjh.developtools;

import com.dpjh.developtools.activitytools.ActivityToolsPage;

public enum ToolsClassEnum {

    ACTIVITY_GET_CURRENT_PAGE_NAME("当前Activity", R.mipmap.ic_launcher,"", ActivityToolsPage.class),

    ACTIVITY_GET_LAYOUT_QUERY("布局边界查看", R.mipmap.ic_launcher,Constants.CODE_LAYOUT_QUERY, null);

    private String title;
    private Object icon;
    private String functionCode;
    private Class<?> activityClass;

    ToolsClassEnum(String title, Object icon, String functionCode, Class<?> activityClass) {
        this.title = title;
        this.icon = icon;
        this.functionCode = functionCode;
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

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<?> activityClass) {
        this.activityClass = activityClass;
    }
}
