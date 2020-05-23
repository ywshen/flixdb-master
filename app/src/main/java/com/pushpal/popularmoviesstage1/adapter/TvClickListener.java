package com.pushpal.popularmoviesstage1.adapter;

import android.widget.ImageView;

import com.pushpal.popularmoviesstage1.model.Tv;

public interface TvClickListener {
    void onTvClick(int pos, Tv movie, ImageView shareImageView);
}
