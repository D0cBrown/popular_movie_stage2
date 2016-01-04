package com.garzoli.project.popularmoviestage2.api;

import com.garzoli.project.popularmoviestage2.model.Movie;
import com.garzoli.project.popularmoviestage2.model.MovieResult;
import com.garzoli.project.popularmoviestage2.model.review.MovieDetailReviewResult;
import com.garzoli.project.popularmoviestage2.model.video.MovieDetailVideoResult;
import com.garzoli.project.popularmoviestage2.model.video.MovieVideo;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface MovieDbAPI {
    /**
     * query for popular movies (sort by popular or rating)
     *
     * @param options contains parameter query (sort_by, api_key, vote_count.gte, page)
     */
    @GET("/discover/movie")
    public MovieResult getPopularMovies(@QueryMap Map<String, String> options);

    /**
     *
     * @param id movie ID
     * @return
     */
    @GET("/movie/{id}/videos")
    public MovieDetailVideoResult getMovieVideo(@Path("id") int id);

    /**
     *
     * @param id movie ID
     * @return
     */
    @GET("/movie/{id}/reviews")
    public MovieDetailReviewResult getMovieReviews(@Path("id") int id);
}