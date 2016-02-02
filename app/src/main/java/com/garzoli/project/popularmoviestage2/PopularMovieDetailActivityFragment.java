package com.garzoli.project.popularmoviestage2;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.garzoli.project.popularmoviestage2.api.MovieDbAPI;
import com.garzoli.project.popularmoviestage2.data.MovieColumns;
import com.garzoli.project.popularmoviestage2.data.MovieReviewColumns;
import com.garzoli.project.popularmoviestage2.data.MovieTrailerColumns;
import com.garzoli.project.popularmoviestage2.data.MoviesProvider;
import com.garzoli.project.popularmoviestage2.loader.ReviewAdapter;
import com.garzoli.project.popularmoviestage2.loader.TrailerAdapter;
import com.garzoli.project.popularmoviestage2.model.review.MovieDetailReviewResult;
import com.garzoli.project.popularmoviestage2.model.review.MovieReview;
import com.garzoli.project.popularmoviestage2.model.video.MovieDetailVideoResult;
import com.garzoli.project.popularmoviestage2.model.video.MovieVideo;
import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private TextView mTitleView;
    private TextView mOverviewView;
    private TextView mDateView;
    private TextView mVoteAverageView;

    private Movie mMovie;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

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
        mMovie = movie;


        TextView markAsFavouriteButtom = (TextView) rootView.findViewById(R.id.buttonFavourite);

        markAsFavouriteButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (null != mMovie) {
                    if (mMovie.isFavourite()) {
                        // removeFavou
                        getContext().getContentResolver().delete(
                                MoviesProvider.MovieReviews.withId(Long.toString(mMovie.getId())), null, null);
                        getContext().getContentResolver().delete(
                                MoviesProvider.MovieTrailers.withId(Long.toString(mMovie.getId())), null, null);
                        getContext().getContentResolver().delete(MoviesProvider.Movies.withId(Long.toString(mMovie.getId())),
                                null, null);
                        mMovie.setFavourite(false);

                        //v.setBackgroundResource(R.drawable.favourite_background_unselected);

                        Toast.makeText(getContext(), getString(R.string.removed_from_favourite), Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        addToFavouriteMovie();
                        mMovie.setFavourite(true);
                       // v.setBackgroundResource(R.drawable.favourite_background_selected);
                        Toast.makeText(getContext(), getString(R.string.mark_as_favourite), Toast.LENGTH_SHORT).show();
                    }
                    // MoviesActivityFragment fragment = (MoviesActivityFragment)
                    // getActivity().getSupportFragmentManager().
                }
            }
        });



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

        LinearListView mTrailersView = (LinearListView) rootView.findViewById(R.id.detail_trailers);
        LinearListView mReviewsView = (LinearListView) rootView.findViewById(R.id.detail_reviews);


        mTrailerAdapter = new TrailerAdapter(getActivity(), new ArrayList<MovieVideo>());
        mTrailersView.setAdapter(mTrailerAdapter);

        mTrailersView.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView linearListView, View view,
                                    int position, long id) {
                MovieVideo trailer = mTrailerAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                startActivity(intent);
            }
        });

        mReviewAdapter = new ReviewAdapter(getActivity(), new ArrayList<MovieReview>());
        mReviewsView.setAdapter(mReviewAdapter);

        return rootView;
    }


    private void addToFavouriteMovie()
    {
        ContentValues values = new ContentValues();
        values.put(MovieColumns._ID, mMovie.getId());
        values.put(MovieColumns.ORIGINAL_TITLE, mMovie.getTitle());
        values.put(MovieColumns.OVERVIEW, mMovie.getOverview());
        values.put(MovieColumns.POSTER_PATH, mMovie.getPosterPath());
        values.put(MovieColumns.RELEASE_DATE, mMovie.getReleaseDate());
        values.put(MovieColumns.VOTE_COUNT, mMovie.getVoteCount());
        values.put(MovieColumns.VOTE_AVERAGE, mMovie.getVoteAverage());
        getContext().getContentResolver().insert(MoviesProvider.Movies.withId(Long.toString(mMovie.getId())), values);
        saveMovieReviews();
        saveMovieTrailers();
    }

    private void saveMovieTrailers()
    {
        List<MovieVideo> movieTrailers = mMovie.getTrailers();
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(movieTrailers.size());

        for (MovieVideo movieTrailer : movieTrailers) {
            ContentProviderOperation.Builder builder = ContentProviderOperation
                    .newInsert(MoviesProvider.MovieTrailers.CONTENT_URI);
            builder.withValue(MovieTrailerColumns.MOVIE_ID, mMovie.getId());
            builder.withValue(MovieTrailerColumns.KEY, movieTrailer.getKey());
            builder.withValue(MovieTrailerColumns.NAME, movieTrailer.getName());
            builder.withValue(MovieTrailerColumns.SITE, movieTrailer.getSite());
            builder.withValue(MovieTrailerColumns.SIZE, movieTrailer.getSize());
            builder.withValue(MovieTrailerColumns.TYPE, movieTrailer.getType());
            batchOperations.add(builder.build());
        }
        try {
            getContext().getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        }
        catch (RemoteException | OperationApplicationException e) {
            Log.e("", "Error applying batch insert", e);
        }
    }

    private void saveMovieReviews()
    {
        List<MovieReview> movieReviews = mMovie.getReviews();
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(movieReviews.size());

        for (MovieReview movieReview : movieReviews) {
            ContentProviderOperation.Builder builder = ContentProviderOperation
                    .newInsert(MoviesProvider.MovieReviews.CONTENT_URI);
            builder.withValue(MovieReviewColumns.MOVIE_ID, mMovie.getId());
            builder.withValue(MovieReviewColumns.AUTHOR, movieReview.getAuthor());
            builder.withValue(MovieReviewColumns.CONTENT, movieReview.getContent());
            builder.withValue(MovieReviewColumns.URL, movieReview.getUrl());
            batchOperations.add(builder.build());
        }
        try {
            getContext().getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        }
        catch (RemoteException | OperationApplicationException e) {
            Log.e("", "Error applying batch insert", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMovie != null) {
            new FetchTrailersTask().execute(Long.toString(mMovie.getId()));
            new FetchReviewsTask().execute(Long.toString(mMovie.getId()));
        }
    }


    public class FetchTrailersTask extends AsyncTask<String, Void, List<MovieVideo>> {

        private final String LOG_TAG = FetchTrailersTask.class.getSimpleName();

        final String BASE_URL = "http://api.themoviedb.org/3/";
        @Override
        protected List<MovieVideo> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();


            MovieDbAPI themoviedbapi = restAdapter.create(MovieDbAPI.class);

            MovieDetailVideoResult movies = themoviedbapi.getMovieVideo(Integer.parseInt(params[0]),BuildConfig.THE_MOVIE_DB_API_KEY);
           return movies.getMovieVideos();
        }

        @Override
        protected void onPostExecute(List<MovieVideo> trailers) {
            if (trailers != null) {
                if (trailers.size() > 0) {
                    if (mTrailerAdapter != null) {
                        mTrailerAdapter.clear();
                        mMovie.setTrailers(trailers);
                        for (MovieVideo trailer : trailers) {
                            mTrailerAdapter.add(trailer);
                        }
                    }
                }
            }
        }
    }

    public class FetchReviewsTask extends AsyncTask<String, Void, List<MovieReview>> {

        private final String LOG_TAG = FetchReviewsTask.class.getSimpleName();

        final String BASE_URL = "http://api.themoviedb.org/3/";

        @Override
        protected List<MovieReview> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();


            MovieDbAPI themoviedbapi = restAdapter.create(MovieDbAPI.class);

            MovieDetailReviewResult reviews = themoviedbapi.getMovieReviews(Integer.parseInt(params[0]),BuildConfig.THE_MOVIE_DB_API_KEY);
            return reviews.getMovieReviews();
        }

        @Override
        protected void onPostExecute(List<MovieReview> reviews) {
            if (reviews != null) {
                if (reviews.size() > 0) {
                    if (mReviewAdapter != null) {
                        mReviewAdapter.clear();
                        mMovie.setReviews(reviews);
                        for (MovieReview review : reviews) {
                            mReviewAdapter.add(review);
                        }
                    }
                }
            }
        }
    }
}
