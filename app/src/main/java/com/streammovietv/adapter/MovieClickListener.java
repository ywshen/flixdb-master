package com.streammovietv.adapter;

import android.widget.ImageView;

import com.streammovietv.model.Movie;

public interface MovieClickListener {
    void onMovieClick(int pos, Movie movie, ImageView shareImageView);
}