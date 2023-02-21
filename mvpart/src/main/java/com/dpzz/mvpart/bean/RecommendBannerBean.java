package com.dpzz.mvpart.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendBannerBean {

    @SerializedName("codes")
    public List<String> codes;
    @SerializedName("regionList")
    public List<RegionListDataBean> regionList;

    public static class RegionListDataBean {
        @SerializedName("code")
        public String code;
        @SerializedName("items")
        public List<ItemsDataBean> items;
    }

    public static class ItemsDataBean {
        @SerializedName("appLink")
        public String appLink;
        @SerializedName("imgApp")
        public String imgApp;
        @SerializedName("subTitle")
        public String subTitle;
        @SerializedName("urlPc")
        public String urlPc;
        @SerializedName("tag")
        public String tag;
        @SerializedName("title")
        public String title;
        @SerializedName("imgPc")
        public String imgPc;
    }
}
