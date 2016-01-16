package com.garzoli.project.popularmoviestage2.loader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.garzoli.project.popularmoviestage2.R;
import com.garzoli.project.popularmoviestage2.model.video.MovieVideo;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by Francesco Garzoli on 15/01/2016.
 */
public class TrailerAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final MovieVideo mLock = new MovieVideo();

    private List<MovieVideo> mObjects;

    public TrailerAdapter(Context context, List<MovieVideo> objects) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObjects = objects;
    }

    public Context getContext() {
        return mContext;
    }

    public void add(MovieVideo object) {
        synchronized (mLock) {
            mObjects.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public MovieVideo getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_movie_trailer, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final MovieVideo trailer = getItem(position);

        viewHolder = (ViewHolder) view.getTag();

        String yt_thumbnail_url = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        Picasso.with(getContext()).load(yt_thumbnail_url).into(viewHolder.imageView);

        viewHolder.nameView.setText(trailer.getName());

        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView nameView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.trailer_image);
            nameView = (TextView) view.findViewById(R.id.trailer_name);
        }
    }

}
