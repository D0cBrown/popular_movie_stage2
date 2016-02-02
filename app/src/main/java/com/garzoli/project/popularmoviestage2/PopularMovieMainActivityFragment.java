package com.garzoli.project.popularmoviestage2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.garzoli.project.popularmoviestage2.api.MovieDbAPI;
import com.garzoli.project.popularmoviestage2.loader.MoviesAdapter;
import com.garzoli.project.popularmoviestage2.model.Movie;
import com.garzoli.project.popularmoviestage2.model.MovieResult;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * @author Francesco Garzoli
 * Date:   09-12-2015
 */
public class PopularMovieMainActivityFragment extends Fragment {

    private final String LOG_TAG = PopularMovieMainActivityFragment.class.getSimpleName();
    private static final String STATE_MOVIES = "state_movies";
    private static final String STATE_SORT_CRITERIA = "state_sort_criteria";
    private static final String STATE_START_PAGE = "state_start_page";


    private MoviesAdapter mMoviesAdapter;
    private ArrayList<Movie> mMovieList;
    private ProgressBar mProgressBar;

    private int mTotalPageNumber = 1000;
    private SortMovieCriteria mSortCriteria = SortMovieCriteria.POPULARITY;
    private int mStartPage = 0;


    public void setSortCriteria(SortMovieCriteria criteria) {
        if (mSortCriteria != criteria) {
            mSortCriteria = criteria;
            updateMovies(1);
        }
    }

    public PopularMovieMainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_MOVIES)) {
//            mMovieList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        } else {
            mMovieList = new ArrayList<>();
        }
        Log.v(LOG_TAG, "onCreate > mMovieList size=" + (mMovieList != null ? mMovieList.size() : 0));
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_SORT_CRITERIA)) {
            mSortCriteria = SortMovieCriteria.valueOf(savedInstanceState.getString(STATE_SORT_CRITERIA));
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_START_PAGE)) {
            mStartPage = savedInstanceState.getInt(STATE_START_PAGE);
        }
        Log.v(LOG_TAG, "onCreate > mStartPage=" + mStartPage + " mSortCriteria=" + mSortCriteria.toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_popular_movie_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_sort_popularity) {
            setSortCriteria(SortMovieCriteria.POPULARITY);
            return true;
        }
        if (id == R.id.action_sort_rating) {
            setSortCriteria(SortMovieCriteria.RATING);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_movie_main, container, false);
        mMoviesAdapter = new MoviesAdapter(getActivity(), mMovieList);
        Log.v(LOG_TAG, "onCreateView() mMoviesAdapter size: " + mMoviesAdapter.getCount() + " mStartPage=" + mStartPage);
        mProgressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_gridview);
        gridView.setAdapter(mMoviesAdapter);

        Log.d(LOG_TAG, "gridView.getNumColumns() = " + gridView.getNumColumns());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMoviesAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), PopularMovieDetailActivity.class);
                intent.putExtra(BundleKeys.MOVIE, movie);
                getActivity().startActivity(intent);
            }
        });
        int visibleThreshold = 5;
        gridView.setOnScrollListener(new EndlessScrollListener(visibleThreshold, mStartPage) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Log.v(LOG_TAG, "EndlessScrollListener.onLoadMore(" + page + ", " + totalItemsCount + ")");
                mStartPage = page - 1;
                loadMoreMoviesFromApi(page);
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, mMovieList);
        outState.putString(STATE_SORT_CRITERIA, mSortCriteria.name());
        outState.putInt(STATE_START_PAGE, mStartPage);
        Log.v(LOG_TAG, "onSaveInstanceState() > mStartPage: " + mStartPage);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v(LOG_TAG, "onViewStateRestored");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "onResume");
    }

    private void loadMoreMoviesFromApi(int offset) {
        Log.v(LOG_TAG, "loadMoreMoviesFromApi(" + offset + ")");
        updateMovies(offset);
    }

    private void updateMovies(int page) {
        Log.v(LOG_TAG, "updateMovies(" + page + ") > fetch more mTotalPageNumber: " + mTotalPageNumber);
        if (page <= 1 && !mMovieList.isEmpty()) {
            Log.v(LOG_TAG, "clear mMovieList, mMoviesAdapter size: " + mMoviesAdapter.getCount());
            mStartPage = 0;
            mMovieList.clear();
            mMoviesAdapter.notifyDataSetChanged();
        }
        if (mTotalPageNumber == 0 || page <= mTotalPageNumber) {
            FetchMoviesTask task = new FetchMoviesTask();
            task.execute(mSortCriteria.toString(), Integer.toString(page));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "onStart()");
        Log.v(LOG_TAG, "onStart() > mMovieList size: " + mMovieList.size() + " mStartPage=" + mStartPage);
        if (mMovieList.isEmpty()) {
            updateMovies(1);
        }
    }


    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        final String SORT_PARAM = "sort_by";
        final String PAGE_PARAM = "page";
        final String MINVOTECOUNT_PARAM = "vote_count.gte";
        final String VOTE_COUNTER = "150";
        final String APPID_PARAM = "api_key";
        final String BASE_URL = "http://api.themoviedb.org/3/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            //If there's no sort definition, there's nothing to loop up. Verify size of params.
            if (params.length < 2) {
                return null;
            }

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();


            MovieDbAPI themoviedbapi = restAdapter.create(MovieDbAPI.class);
            HashMap<String, String> options = new HashMap<>();
            options.put(SORT_PARAM, params[0]);
            options.put(PAGE_PARAM, params[1]);
            options.put(MINVOTECOUNT_PARAM,VOTE_COUNTER);
            options.put(APPID_PARAM,BuildConfig.THE_MOVIE_DB_API_KEY);

            MovieResult movies = themoviedbapi.getPopularMovies(options);
            return (ArrayList)movies.getMovies();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (movies != null && !movies.isEmpty()) {
                mMovieList.addAll(movies);
                mMoviesAdapter.notifyDataSetChanged();
                Log.v(LOG_TAG, "onPostExecute() mMovieList size: " + mMovieList.size() + " mMoviesAdapter size: " + mMoviesAdapter.getCount());
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
