package com.dpzz.mvpart.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MvDetailBean implements Serializable {

    private static final long serialVersionUID = 4322403255959105728L;

    @SerializedName("basic")
    public BasicDataBean basic;
    @SerializedName("related")
    public Object related;
    @SerializedName("advertisement")
    public AdvertisementDataBean advertisement;
    @SerializedName("boxOffice")
    public Object boxOffice;
    @SerializedName("live")
    public Object live;
    @SerializedName("playState")
    public String playState;
    @SerializedName("playlist")
    public List<?> playlist;
    @SerializedName("behindInfo")
    public BehindInfoDataBean behindInfo;
    @SerializedName("news")
    public List<NewsDataBean> news;

    public static class BasicDataBean {
        @SerializedName("movieId")
        public int movieId;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
        @SerializedName("overallRating")
        public Double overallRating;
        @SerializedName("isEReleased")
        public int isEReleased;
        @SerializedName("movieSubItemRatings")
        public List<MovieSubItemRatingsDataBean> movieSubItemRatings;
        @SerializedName("ratingCountRatios")
        public List<RatingCountRatiosDataBean> ratingCountRatios;
        @SerializedName("message")
        public String message;
        @SerializedName("isFilter")
        public Boolean isFilter;
        @SerializedName("url")
        public String url;
        @SerializedName("img")
        public String img;
        @SerializedName("bigImage")
        public String bigImage;
        @SerializedName("sensitiveStatus")
        public Boolean sensitiveStatus;
        @SerializedName("movieStatus")
        public int movieStatus;
        @SerializedName("video")
        public VideoDataBean video;
        @SerializedName("type")
        public List<String> type;
        @SerializedName("isEggHunt")
        public Boolean isEggHunt;
        @SerializedName("eggDesc")
        public Object eggDesc;
        @SerializedName("community")
        public Object community;
        @SerializedName("commentSpecial")
        public Object commentSpecial;
        @SerializedName("hotRanking")
        public int hotRanking;
        @SerializedName("director")
        public DirectorDataBean director;
        @SerializedName("actors")
        public List<ActorsDataBean> actors;
        @SerializedName("personCount")
        public int personCount;
        @SerializedName("isTicket")
        public Boolean isTicket;
        @SerializedName("showCinemaCount")
        public int showCinemaCount;
        @SerializedName("showtimeCount")
        public int showtimeCount;
        @SerializedName("showDay")
        public int showDay;
        @SerializedName("style")
        public Object style;
        @SerializedName("is3D")
        public Boolean is3D;
        @SerializedName("isIMAX3D")
        public Boolean isIMAX3D;
        @SerializedName("isDMAX")
        public Boolean isDMAX;
        @SerializedName("isIMAX")
        public Boolean isIMAX;
        @SerializedName("festivals")
        public List<?> festivals;
        @SerializedName("award")
        public AwardDataBean award;
        @SerializedName("releaseDate")
        public String releaseDate;
        @SerializedName("releaseArea")
        public String releaseArea;
        @SerializedName("mins")
        public String mins;
        @SerializedName("story")
        public String story;
        @SerializedName("stageImg")
        public StageImgDataBean stageImg;
        @SerializedName("quizGame")
        public Object quizGame;
        @SerializedName("ratingCount")
        public int ratingCount;
        @SerializedName("ratingCountShow")
        public String ratingCountShow;
        @SerializedName("subItemRatingCount")
        public int subItemRatingCount;
        @SerializedName("subItemRatingCountShow")
        public String subItemRatingCountShow;
        @SerializedName("attitude")
        public int attitude;
        @SerializedName("wantToSeeCount")
        public int wantToSeeCount;
        @SerializedName("wantToSeeCountShow")
        public String wantToSeeCountShow;
        @SerializedName("hasSeenCount")
        public int hasSeenCount;
        @SerializedName("hasSeenCountShow")
        public String hasSeenCountShow;
        @SerializedName("wantToSeeNumberShow")
        public Object wantToSeeNumberShow;
        @SerializedName("userComment")
        public Object userComment;
        @SerializedName("userCommentId")
        public int userCommentId;
        @SerializedName("userRating")
        public int userRating;
        @SerializedName("userMovieSubItemRatings")
        public Object userMovieSubItemRatings;
        @SerializedName("userImg")
        public Object userImg;
        @SerializedName("userName")
        public Object userName;
        @SerializedName("isFavorite")
        public int isFavorite;
        @SerializedName("year")
        public String year;
        @SerializedName("summary")
        public Object summary;
        @SerializedName("episodeCnt")
        public String episodeCnt;
        @SerializedName("broadcastDes")
        public String broadcastDes;
        @SerializedName("videoCount")
        public int videoCount;
        @SerializedName("imageCount")
        public int imageCount;
        @SerializedName("commentCount")
        public int commentCount;
        @SerializedName("longCommentCount")
        public int longCommentCount;
        @SerializedName("shortCommentCount")
        public int shortCommentCount;
        @SerializedName("newsCount")
        public int newsCount;
        @SerializedName("writers")
        public List<WritersDataBean> writers;
        @SerializedName("directors")
        public List<DirectorsDataBean> directors;
        @SerializedName("companies")
        public List<CompaniesDataBean> companies;
        @SerializedName("productionCompanies")
        public List<ProductionCompaniesDataBean> productionCompanies;
        @SerializedName("presentCompanies")
        public List<PresentCompaniesDataBean> presentCompanies;
        @SerializedName("unionPresentCompanies")
        public List<UnionPresentCompaniesDataBean> unionPresentCompanies;
        @SerializedName("videos")
        public List<VideosDataBean> videos;
        @SerializedName("releaseAreaEn")
        public String releaseAreaEn;
        @SerializedName("otherTitles")
        public List<?> otherTitles;
        @SerializedName("versions")
        public List<String> versions;
        @SerializedName("relatedMovies")
        public Object relatedMovies;
        @SerializedName("currentUserId")
        public int currentUserId;
        @SerializedName("lowestTicketPrice")
        public int lowestTicketPrice;
        @SerializedName("countries")
        public List<CountriesDataBean> countries;
        @SerializedName("movieGenres")
        public List<MovieGenresDataBean> movieGenres;
        @SerializedName("releaseDateNew")
        public String releaseDateNew;
        @SerializedName("rankName")
        public Object rankName;
        @SerializedName("movieType")
        public int movieType;
        @SerializedName("favoriteCount")
        public int favoriteCount;
    }

    public static class VideoDataBean {
        @SerializedName("url")
        public String url;
        @SerializedName("videoId")
        public int videoId;
        @SerializedName("videoSourceType")
        public int videoSourceType;
        @SerializedName("title")
        public String title;
        @SerializedName("hightUrl")
        public String hightUrl;
        @SerializedName("img")
        public String img;
        @SerializedName("count")
        public int count;
        @SerializedName("length")
        public int length;
    }

    public static class DirectorDataBean {
        @SerializedName("directorId")
        public int directorId;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
        @SerializedName("img")
        public String img;
    }

    public static class AwardDataBean {
        @SerializedName("totalWinAward")
        public int totalWinAward;
        @SerializedName("totalNominateAward")
        public int totalNominateAward;
        @SerializedName("awardList")
        public List<?> awardList;
    }

    public static class StageImgDataBean {
        @SerializedName("count")
        public int count;
        @SerializedName("list")
        public List<ListDataBean> list;
    }


    public static class ListDataBean {
        @SerializedName("imgId")
        public int imgId;
        @SerializedName("imgUrl")
        public String imgUrl;
    }

    public static class MovieSubItemRatingsDataBean {
        @SerializedName("index")
        public int index;
        @SerializedName("title")
        public String title;
        @SerializedName("rating")
        public Double rating;
    }

    public static class RatingCountRatiosDataBean {
        @SerializedName("title")
        public String title;
        @SerializedName("ratio")
        public Double ratio;
    }

    public static class ActorsDataBean {
        @SerializedName("actorId")
        public int actorId;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
        @SerializedName("img")
        public String img;
        @SerializedName("roleName")
        public Object roleName;
        @SerializedName("roleImg")
        public String roleImg;
    }

    public static class WritersDataBean {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class DirectorsDataBean {
        @SerializedName("directorId")
        public int directorId;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
        @SerializedName("img")
        public String img;
    }

    public static class CompaniesDataBean {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public Object name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class ProductionCompaniesDataBean {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public Object name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class PresentCompaniesDataBean {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class UnionPresentCompaniesDataBean {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class VideosDataBean {
        @SerializedName("url")
        public String url;
        @SerializedName("videoId")
        public int videoId;
        @SerializedName("videoSourceType")
        public int videoSourceType;
        @SerializedName("title")
        public String title;
        @SerializedName("hightUrl")
        public String hightUrl;
        @SerializedName("img")
        public String img;
        @SerializedName("count")
        public int count;
        @SerializedName("length")
        public int length;
    }

    public static class CountriesDataBean {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class MovieGenresDataBean {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class AdvertisementDataBean {
        @SerializedName("count")
        public int count;
        @SerializedName("success")
        public Boolean success;
        @SerializedName("error")
        public Object error;
        @SerializedName("advList")
        public Object advList;
    }

    public static class BehindInfoDataBean {
        @SerializedName("eventCount")
        public int eventCount;
        @SerializedName("title")
        public String title;
        @SerializedName("list")
        public List<?> list;
    }

    public static class NewsDataBean {
        @SerializedName("newsId")
        public int newsId;
        @SerializedName("image")
        public String image;
        @SerializedName("title")
        public String title;
        @SerializedName("content")
        public String content;
    }
}
