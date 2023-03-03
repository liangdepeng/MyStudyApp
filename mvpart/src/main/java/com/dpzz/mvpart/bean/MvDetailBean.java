package com.dpzz.mvpart.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MvDetailBean {

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
        public Integer movieId;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
        @SerializedName("overallRating")
        public Double overallRating;
        @SerializedName("isEReleased")
        public Integer isEReleased;
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
        public Integer movieStatus;
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
        public Integer hotRanking;
        @SerializedName("director")
        public DirectorDataBean director;
        @SerializedName("actors")
        public List<ActorsDataBean> actors;
        @SerializedName("personCount")
        public Integer personCount;
        @SerializedName("isTicket")
        public Boolean isTicket;
        @SerializedName("showCinemaCount")
        public Integer showCinemaCount;
        @SerializedName("showtimeCount")
        public Integer showtimeCount;
        @SerializedName("showDay")
        public Integer showDay;
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
        public Integer ratingCount;
        @SerializedName("ratingCountShow")
        public String ratingCountShow;
        @SerializedName("subItemRatingCount")
        public Integer subItemRatingCount;
        @SerializedName("subItemRatingCountShow")
        public String subItemRatingCountShow;
        @SerializedName("attitude")
        public Integer attitude;
        @SerializedName("wantToSeeCount")
        public Integer wantToSeeCount;
        @SerializedName("wantToSeeCountShow")
        public String wantToSeeCountShow;
        @SerializedName("hasSeenCount")
        public Integer hasSeenCount;
        @SerializedName("hasSeenCountShow")
        public String hasSeenCountShow;
        @SerializedName("wantToSeeNumberShow")
        public Object wantToSeeNumberShow;
        @SerializedName("userComment")
        public Object userComment;
        @SerializedName("userCommentId")
        public Integer userCommentId;
        @SerializedName("userRating")
        public Integer userRating;
        @SerializedName("userMovieSubItemRatings")
        public Object userMovieSubItemRatings;
        @SerializedName("userImg")
        public Object userImg;
        @SerializedName("userName")
        public Object userName;
        @SerializedName("isFavorite")
        public Integer isFavorite;
        @SerializedName("year")
        public String year;
        @SerializedName("summary")
        public Object summary;
        @SerializedName("episodeCnt")
        public String episodeCnt;
        @SerializedName("broadcastDes")
        public String broadcastDes;
        @SerializedName("videoCount")
        public Integer videoCount;
        @SerializedName("imageCount")
        public Integer imageCount;
        @SerializedName("commentCount")
        public Integer commentCount;
        @SerializedName("longCommentCount")
        public Integer longCommentCount;
        @SerializedName("shortCommentCount")
        public Integer shortCommentCount;
        @SerializedName("newsCount")
        public Integer newsCount;
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
        public Integer currentUserId;
        @SerializedName("lowestTicketPrice")
        public Integer lowestTicketPrice;
        @SerializedName("countries")
        public List<CountriesDataBean> countries;
        @SerializedName("movieGenres")
        public List<MovieGenresDataBean> movieGenres;
        @SerializedName("releaseDateNew")
        public String releaseDateNew;
        @SerializedName("rankName")
        public Object rankName;
        @SerializedName("movieType")
        public Integer movieType;
        @SerializedName("favoriteCount")
        public Integer favoriteCount;
    }

    public static class VideoDataBean {
        @SerializedName("url")
        public String url;
        @SerializedName("videoId")
        public Integer videoId;
        @SerializedName("videoSourceType")
        public Integer videoSourceType;
        @SerializedName("title")
        public String title;
        @SerializedName("hightUrl")
        public String hightUrl;
        @SerializedName("img")
        public String img;
        @SerializedName("count")
        public Integer count;
        @SerializedName("length")
        public Integer length;
    }

    public static class DirectorDataBean {
        @SerializedName("directorId")
        public Integer directorId;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
        @SerializedName("img")
        public String img;
    }

    public static class AwardDataBean {
        @SerializedName("totalWinAward")
        public Integer totalWinAward;
        @SerializedName("totalNominateAward")
        public Integer totalNominateAward;
        @SerializedName("awardList")
        public List<?> awardList;
    }

    public static class StageImgDataBean {
        @SerializedName("count")
        public Integer count;
        @SerializedName("list")
        public List<ListDataBean> list;
    }


    public static class ListDataBean {
        @SerializedName("imgId")
        public Integer imgId;
        @SerializedName("imgUrl")
        public String imgUrl;
    }

    public static class MovieSubItemRatingsDataBean {
        @SerializedName("index")
        public Integer index;
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
        public Integer actorId;
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
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class DirectorsDataBean {
        @SerializedName("directorId")
        public Integer directorId;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
        @SerializedName("img")
        public String img;
    }

    public static class CompaniesDataBean {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public Object name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class ProductionCompaniesDataBean {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public Object name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class PresentCompaniesDataBean {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class UnionPresentCompaniesDataBean {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class VideosDataBean {
        @SerializedName("url")
        public String url;
        @SerializedName("videoId")
        public Integer videoId;
        @SerializedName("videoSourceType")
        public Integer videoSourceType;
        @SerializedName("title")
        public String title;
        @SerializedName("hightUrl")
        public String hightUrl;
        @SerializedName("img")
        public String img;
        @SerializedName("count")
        public Integer count;
        @SerializedName("length")
        public Integer length;
    }

    public static class CountriesDataBean {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class MovieGenresDataBean {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameEn")
        public String nameEn;
    }

    public static class AdvertisementDataBean {
        @SerializedName("count")
        public Integer count;
        @SerializedName("success")
        public Boolean success;
        @SerializedName("error")
        public Object error;
        @SerializedName("advList")
        public Object advList;
    }

    public static class BehindInfoDataBean {
        @SerializedName("eventCount")
        public Integer eventCount;
        @SerializedName("title")
        public String title;
        @SerializedName("list")
        public List<?> list;
    }

    public static class NewsDataBean {
        @SerializedName("newsId")
        public Integer newsId;
        @SerializedName("image")
        public String image;
        @SerializedName("title")
        public String title;
        @SerializedName("content")
        public String content;
    }
}
