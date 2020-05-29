package com.streammovietv.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.streammovietv.model.Tv;

import java.util.List;

public class TvViewModel extends AndroidViewModel {

    private static final String TAG = TvViewModel.class.getSimpleName();
    private final LiveData<List<Tv>> favTvs;
    private List<Tv> tvs;

    public TvViewModel(@NonNull Application application) {
        super(application);

        TvDatabase tvDatabase = TvDatabase.getInstance(this.getApplication());
        Log.e(TAG, "Actively retrieving the favMovies from the database");
        favTvs = tvDatabase.tvDao().getAllTvs();
    }

    public LiveData<List<Tv>> getFavTvs() {
        return favTvs;
    }

    public List<Tv> getTvs() {
        return tvs;
    }

    public void setTvs(List<Tv> tvs) {
        this.tvs = tvs;
    }
}
