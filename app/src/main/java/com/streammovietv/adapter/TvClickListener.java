package com.streammovietv.adapter;

import android.widget.ImageView;

import com.streammovietv.model.Tv;

public interface TvClickListener {
    void onTvClick(int pos, Tv movie, ImageView shareImageView);
}
