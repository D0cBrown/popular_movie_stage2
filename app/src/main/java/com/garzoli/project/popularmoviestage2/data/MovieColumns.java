package com.garzoli.project.popularmoviestage2.data;


import com.garzoli.project.popularmoviestage2.model.Movie;
import com.garzoli.project.popularmoviestage2.model.video.MovieVideo;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by ojha on 13/01/16.
 */
public interface MovieColumns
{
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String ORIGINAL_TITLE = "title";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String POSTER_PATH = Movie.MOVIE_POSTER_PATH;

    @DataType(DataType.Type.TEXT)
    @NotNull
    String OVERVIEW = Movie.MOVIE_OVERVIEW;

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String RELEASE_DATE = Movie.MOVIE_RELEASE_DATE;

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String VOTE_AVERAGE = Movie.MOVIE_VOTE_AVERAGE;

    @DataType(DataType.Type.INTEGER)
    String VOTE_COUNT = Movie.MOVIE_VOTE_COUNT;

}
