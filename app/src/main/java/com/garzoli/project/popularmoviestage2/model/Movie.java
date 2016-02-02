
package com.garzoli.project.popularmoviestage2.model;

import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.garzoli.project.popularmoviestage2.model.review.MovieReview;
import com.garzoli.project.popularmoviestage2.model.video.MovieVideo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    public final static String MOVIE_ADULT = "adult";
    public final static String MOVIE_BACKDROP = "backdrop_path";
    public final static String MOVIE_GENRE = "genre_ids";
    public final static String MOVIE_ID = "id";
    public final static String MOVIE_ORIGINAL_LANGUAGE = "original_language";
    public final static String MOVIE_ORIGINAL_TITLE = "original_title";
    public final static String MOVIE_OVERVIEW = "overview";
    public final static String MOVIE_RELEASE_DATE = "release_date";
    public final static String MOVIE_POSTER_PATH = "poster_path";
    public final static String MOVIE_POPULARITY = "popularity";
    public final static String MOVIE_TITLE = "title";
    public final static String MOVIE_VIDEO = "video";
    public final static String MOVIE_VOTE_AVERAGE = "vote_average";
    public final static String MOVIE_VOTE_COUNT = "vote_count";

    @SerializedName(MOVIE_ADULT)
    @Expose
    private Boolean adult;
    @SerializedName(MOVIE_BACKDROP)
    @Expose
    private String backdropPath;
    @SerializedName(MOVIE_GENRE)
    @Expose
    private List<Long> genreIds = new ArrayList<Long>();
    @SerializedName(MOVIE_ID)
    @Expose
    private Long id;
    @SerializedName(MOVIE_ORIGINAL_LANGUAGE)
    @Expose
    private String originalLanguage;
    @SerializedName(MOVIE_ORIGINAL_TITLE)
    @Expose
    private String originalTitle;
    @SerializedName(MOVIE_OVERVIEW)
    @Expose
    private String overview;
    @SerializedName(MOVIE_RELEASE_DATE)
    @Expose
    private String releaseDate;
    @SerializedName(MOVIE_POSTER_PATH)
    @Expose
    private String posterPath;
    @SerializedName(MOVIE_POPULARITY)
    @Expose
    private Double popularity;
    @SerializedName(MOVIE_TITLE)
    @Expose
    private String title;
    @SerializedName(MOVIE_VIDEO)
    @Expose
    private Boolean video;
    @SerializedName(MOVIE_VOTE_AVERAGE)
    @Expose
    private Double voteAverage;
    @SerializedName(MOVIE_VOTE_COUNT)
    @Expose
    private Long voteCount;


    private boolean isFavourite;
    private List<MovieReview> reviews;
    private List<MovieVideo> trailers;

    /**
     * 
     * @return
     *     The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * 
     * @param adult
     *     The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     * 
     * @return
     *     The backdropPath
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * 
     * @param backdropPath
     *     The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * 
     * @return
     *     The genreIds
     */
    public List<Long> getGenreIds() {
        return genreIds;
    }

    /**
     * 
     * @param genreIds
     *     The genre_ids
     */
    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * 
     * @param originalLanguage
     *     The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * 
     * @return
     *     The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * 
     * @param originalTitle
     *     The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * 
     * @return
     *     The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * 
     * @param overview
     *     The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * 
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     * @param releaseDate
     *     The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 
     * @return
     *     The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * 
     * @param posterPath
     *     The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * 
     * @return
     *     The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * 
     * @param popularity
     *     The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     * 
     * @param video
     *     The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     * 
     * @return
     *     The voteAverage
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * 
     * @param voteAverage
     *     The vote_average
     */
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * 
     * @return
     *     The voteCount
     */
    public Long getVoteCount() {
        return voteCount;
    }

    /**
     * 
     * @param voteCount
     *     The vote_count
     */
    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isFavourite(){
        return isFavourite;
    }

    public void setFavourite(boolean fav){
        isFavourite = fav;
    }

    public List<MovieVideo> getTrailers() {
        return this.trailers;
    }

    public void setTrailers(List<MovieVideo> trailers) {
        this.trailers = trailers;
    }

    public List<MovieReview> getReviews() {
        return this.reviews;
    }

    public void setReviews(List<MovieReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeDouble(voteAverage);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dest.writeLong(simpleDateFormat.parse(releaseDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(android.os.Parcel source) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Movie movie = new Movie();
            movie.setId(source.readLong());
            movie.setTitle(source.readString());
            movie.setOverview(source.readString());
            movie.setPosterPath(source.readString());
            movie.setBackdropPath(source.readString());
            movie.setVoteAverage(source.readDouble());
            Date releaseDate = new Date();
            releaseDate.setTime(source.readLong());
            movie.setReleaseDate(simpleDateFormat.format(releaseDate));
            return movie;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
