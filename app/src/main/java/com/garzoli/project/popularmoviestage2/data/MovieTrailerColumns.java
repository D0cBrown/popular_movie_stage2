package com.garzoli.project.popularmoviestage2.data;

import com.garzoli.project.popularmoviestage2.model.video.MovieVideo;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by ojha on 14/01/16.
 */
public interface MovieTrailerColumns
{
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @References(table = MovieDatabase.MOVIES, column = MovieColumns._ID)
    @NotNull
    String MOVIE_ID = "movie_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String KEY = MovieVideo.MOVIE_VIDEO_KEY;

    @DataType(DataType.Type.TEXT)
    @NotNull
    String NAME = MovieVideo.MOVIE_VIDEO_NAME;

    @DataType(DataType.Type.TEXT)
    @NotNull
    String SITE = MovieVideo.MOVIE_VIDEO_SITE;

    @DataType(DataType.Type.TEXT)
    @NotNull
    String SIZE = MovieVideo.MOVIE_VIDEO_SIZE;

    @DataType(DataType.Type.TEXT)
    @NotNull
    String TYPE = MovieVideo.MOVIE_VIDEO_TYPE;
}
