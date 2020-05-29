package com.streammovietv.adapter;

import android.widget.ImageView;

import com.streammovietv.model.Search;

public interface SearchClickListener {
    void onSearchClick(int pos, Search result, ImageView moviePosterImageView);
}