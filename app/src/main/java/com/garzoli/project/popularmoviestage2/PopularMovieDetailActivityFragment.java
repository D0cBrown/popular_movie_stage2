package com.garzoli.project.popularmoviestage2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garzoli.project.popularmoviestage2.api.MovieDbAPI;
import com.garzoli.project.popularmoviestage2.model.MovieResult;
import com.garzoli.project.popularmoviestage2.model.video.MovieDetailVideoResult;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.garzoli.project.popularmoviestage2.model.Movie;
import com.garzoli.project.popularmoviestage2.util.Util;

import retrofit.RestAdapter;

/**
 * Fragment contains Movie details.
 *
 * @author Francesco Garzoli
 * Date:   09-12-2015
 */
public class PopularMovieDetailActivityFragment extends Fragment {

    public PopularMovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popular_movie_detail, container, false);
        // The detail Activity called via intent.  Inspect the intent for movie data.

//        int imageWidth = getActivity().getResources().getDimensionPixelSize(R.dimen.movie_thumb_width);
//        int imageHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.movie_thumb_height);

        Movie movie = null;
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(BundleKeys.MOVIE)) {
            movie = intent.getParcelableExtra(BundleKeys.MOVIE);
        }
        if (movie == null) {
            throw new IllegalStateException("no given movie!");
        }

        Picasso picasso = Picasso.with(getActivity());
        ImageView poster = (ImageView) rootView.findViewById(R.id.poster);
        ImageView backdrop = (ImageView) rootView.findViewById(R.id.backdrop);

        int posterWidth = getResources().getDimensionPixelSize(R.dimen.details_poster_width);
        int posterHeight = getResources().getDimensionPixelSize(R.dimen.details_poster_height);
        picasso.load(Util.buildPosterUrl(movie.getPosterPath(), posterWidth))
                .resize(posterWidth, posterHeight)
                .centerCrop()
                .noPlaceholder()
                .into(poster);

        int backdropWidth = Util.getScreenWidth(getActivity());
        int backdropHeight = getResources().getDimensionPixelSize(R.dimen.details_backdrop_height);
        picasso.load(Util.buildBackdropUrl(movie.getBackdropPath(), backdropWidth))
                .resize(backdropWidth, backdropHeight)
                .centerCrop()
                .noPlaceholder()
                .into(backdrop);

        getActivity().setTitle(movie.getTitle());

        ((TextView) rootView.findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView) rootView.findViewById(R.id.synopsis)).setText(movie.getOverview());

        String voteAverage = String.format("%1$2.1f", movie.getVoteAverage());

        ((TextView) rootView.findViewById(R.id.rating)).setText(voteAverage);
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getActivity());
        Calendar calendar = Calendar.getInstance();
       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            calendar.setTime(simpleDateFormat.parse(movie.getReleaseDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        ((TextView) rootView.findViewById(R.id.release_date)).setText(movie.getReleaseDate());
                //getString(R.string.released) + dateFormat.format(movie.getReleaseDate()));

        FetchDetailMovieTask fetchDetailMovieTask = new FetchDetailMovieTask();

        return rootView;
    }


    public class FetchDetailMovieTask extends AsyncTask<Integer, Void, MovieDetailVideoResult> {
        private final String LOG_TAG = FetchDetailMovieTask.class.getSimpleName();

        final String BASE_URL = "http://api.themoviedb.org/3/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MovieDetailVideoResult doInBackground(Integer... params) {

            //If there's no sort definition, there's nothing to loop up. Verify size of params.
            if (params.length < 1) {
                return null;
            }

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();


            MovieDbAPI themoviedbapi = restAdapter.create(MovieDbAPI.class);

            MovieDetailVideoResult movies = themoviedbapi.getMovieVideo(params[0]);

            return movies;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(MovieDetailVideoResult movies) {
            super.onPostExecute(movies);
        }
    }
}
