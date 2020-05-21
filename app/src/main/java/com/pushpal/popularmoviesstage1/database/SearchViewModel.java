package com.pushpal.popularmoviesstage1.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pushpal.popularmoviesstage1.model.Movie;
import com.pushpal.popularmoviesstage1.model.Search;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private List<Search> searches;

    public SearchViewModel(@NonNull Application application) {
        super(application);

        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }
}
