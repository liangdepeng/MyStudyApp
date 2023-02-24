package com.dpzz.mvpart.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RecommendMvBean implements Serializable {

    private static final long serialVersionUID = 6712820094317023942L;
    @SerializedName("hotPlayMovies")
    public List<MoviesDataBean> hotPlayMovies;
    @SerializedName("mobilemoviecoming")
    public MobilemoviecomingDataBean mobilemoviecoming;

    public static class MobilemoviecomingDataBean {
        @SerializedName("recommends")
        public List<RecommendsDataBean> recommends;
        @SerializedName("moviecomings")
        public List<MoviesDataBean> moviecomings;
        @SerializedName("totalMovieComings")
        public Integer totalMovieComings;
    }

    public static class RecommendsDataBean {
        @SerializedName("recommendTitle")
        public String recommendTitle;
        @SerializedName("movies")
        public List<MoviesDataBean> movies;
    }


    public static class MoviesDataBean {
        @SerializedName("imgUrl")
        public String imgUrl;
        @SerializedName("videoId")
        public Integer videoId;
        @SerializedName("movieId")
        public Integer movieId;
        @SerializedName("score")
        public String score;
        @SerializedName("movieCn")
        public String movieCn;
        @SerializedName("movieEn")
        public String movieEn;
        @SerializedName("title")
        public String title;
        @SerializedName("currentType")
        public Integer currentType;
        @SerializedName("btnShow")
        public Integer btnShow;
        @SerializedName("directorName")
        public String directorName;
        @SerializedName("actorNames")
        public Object actorNames;
        @SerializedName("type")
        public String type;
        @SerializedName("duration")
        public String duration;
        @SerializedName("wantedCount")
        public Integer wantedCount;
        @SerializedName("releaseDate")
        public Long releaseDate;
        @SerializedName("releaseDateStr")
        public String releaseDateStr;
        @SerializedName("shortComment")
        public Object shortComment;
        @SerializedName("paragraph")
        public String paragraph;
        @SerializedName("isVideo")
        public Boolean isVideo;
        @SerializedName("is3D")
        public Boolean is3D;
        @SerializedName("isDMAX")
        public Boolean isDMAX;
        @SerializedName("isIMAX")
        public Boolean isIMAX;
        @SerializedName("isIMAX3D")
        public Boolean isIMAX3D;
        @SerializedName("isNew")
        public Boolean isNew;
        @SerializedName("isHot")
        public Boolean isHot;
        @SerializedName("summary")
        public String summary;
        @SerializedName("releaseYear")
        public Integer releaseYear;
        @SerializedName("releaseMonth")
        public Integer releaseMonth;
        @SerializedName("releaseDay")
        public Integer releaseDay;
        @SerializedName("director")
        public String director;
        @SerializedName("actors")
        public String actors;
        @SerializedName("isFilter")
        public Boolean isFilter;
        @SerializedName("isTicket")
        public Boolean isTicket;
        @SerializedName("isWantSee")
        public Boolean isWantSee;
    }

    @Override
    public String toString() {
        return "RecommendMvBean{" +
                "hotPlayMovies=" + hotPlayMovies +
                ", mobilemoviecoming=" + mobilemoviecoming +
                '}';
    }
}
