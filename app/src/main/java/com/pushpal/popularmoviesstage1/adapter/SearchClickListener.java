package com.pushpal.popularmoviesstage1.adapter;

import android.widget.ImageView;

import com.pushpal.popularmoviesstage1.model.Search;

import java.util.List;

public interface SearchClickListener {
    void onSearchClick(int pos, Search result, ImageView moviePosterImageView);
}