package com.garzoli.project.popularmoviestage2.api;

import com.garzoli.project.popularmoviestage2.model.MovieResult;

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
    @GET("/3/discover/movie")
    public MovieResult getPopularMovies(@QueryMap Map<String, String> options);



}