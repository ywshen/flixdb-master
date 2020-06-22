package com.streammovietv.adapter;

import android.widget.ImageView;

import com.streammovietv.model.MovieSimilar;

public interface SimilarClickListener {
    void onMovieClick(int pos, MovieSimilar similar, ImageView shareImageView);
}
