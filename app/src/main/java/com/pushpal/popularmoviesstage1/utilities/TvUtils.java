package com.pushpal.popularmoviesstage1.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pushpal.popularmoviesstage1.activity.TvActivity;
import com.pushpal.popularmoviesstage1.model.Tv;
import com.pushpal.popularmoviesstage1.model.TvLang;
import com.pushpal.popularmoviesstage1.networking.RESTClient;
import com.pushpal.popularmoviesstage1.networking.RESTClientInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvUtils {
    private static final String TAG = MovieUtils.class.getSimpleName();

    public static void fetchLanguages() {
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<List<TvLang>> call;
        final List<TvLang> finalTvLanguages = new ArrayList<>();

        call = restClientInterface.getTvLanguages(Constants.API_KEY);

        if (call != null) {
            call.enqueue(new Callback<List<TvLang>>() {
                @Override
                public void onResponse(@NonNull Call<List<TvLang>> call, @NonNull Response<List<TvLang>> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            finalTvLanguages.addAll(response.body());
                            TvActivity.sLanguageMap = new HashMap<>();
                            for (TvLang movieLang : finalTvLanguages)
                                TvActivity.sLanguageMap.put(movieLang.getAbbreviation(), movieLang.getEnglishName());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<TvLang>> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e(TAG, throwable.toString());
                }
            });
        }
    }

    public static boolean isFavourite(Tv tv) {
        boolean isFav = false;
        if (TvActivity.sFavouriteTvs != null) {
            for (Tv favTv : TvActivity.sFavouriteTvs) {
                if (tv.getId().equals(favTv.getId())) {
                    isFav = true;
                    break;
                }
            }
        }
        return isFav;
    }
}
