
package com.garzoli.project.popularmoviestage2.model.video;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDetailVideoResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieVideo> movieVideos = new ArrayList<MovieVideo>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The movieVideos
     */
    public List<MovieVideo> getMovieVideos() {
        return movieVideos;
    }

    /**
     * 
     * @param movieVideos
     *     The movieVideos
     */
    public void setMovieVideos(List<MovieVideo> movieVideos) {
        this.movieVideos = movieVideos;
    }

}
