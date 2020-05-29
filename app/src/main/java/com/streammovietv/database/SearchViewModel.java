package com.streammovietv.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.streammovietv.model.Search;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private List<Search> searches;

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }
}
