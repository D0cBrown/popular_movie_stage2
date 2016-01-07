package com.garzoli.project.popularmoviestage2.loader;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.garzoli.project.popularmoviestage2.R;
import com.garzoli.project.popularmoviestage2.model.Movie;
import com.garzoli.project.popularmoviestage2.model.video.MovieVideo;
import com.garzoli.project.popularmoviestage2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

/**
 * @author Francesco Garzoli
 * Date:   09-12-2015
 */
public class VideosAdapter extends BaseAdapter {

    private static final String LOG_TAG = VideosAdapter.class.getSimpleName();
    private final Context mContext;
    private final List<MovieVideo> mMovieVideos;
    //private final int mWidth;
    //    private final int mHeight;


    public VideosAdapter(Activity context,
                                 List<MovieVideo> videos) {
        mContext = context;
        mMovieVideos = videos;
    }

    @Override
    public int getCount() {
        return mMovieVideos.size();
    }

    @Override
    public MovieVideo getItem(int position) {
        if (position >= 0 && position < mMovieVideos.size()) {
            return mMovieVideos.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_movie_video, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.textViewName);
            viewHolder.number = (TextView)convertView.findViewById(R.id.textViewNumber);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MovieVideo video = getItem(position);
        viewHolder.name.setText(video.getName());
        viewHolder.number.setText(video.getSite());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView number;
    }

    public void clearData() {
        mMovieVideos.clear();
    }

    public List<MovieVideo> getItems() {
        return mMovieVideos;
    }
}
