package com.pushpal.popularmoviesstage1.activity;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pushpal.popularmoviesstage1.R;
import com.pushpal.popularmoviesstage1.adapter.CastAdapter;
import com.pushpal.popularmoviesstage1.adapter.ReviewAdapter;
import com.pushpal.popularmoviesstage1.adapter.TvCastAdapter;
import com.pushpal.popularmoviesstage1.adapter.TvReviewAdapter;
import com.pushpal.popularmoviesstage1.database.AppExecutors;
import com.pushpal.popularmoviesstage1.database.TvDatabase;
import com.pushpal.popularmoviesstage1.model.Tv;
import com.pushpal.popularmoviesstage1.model.TvCast;
import com.pushpal.popularmoviesstage1.model.TvCreditResponse;
import com.pushpal.popularmoviesstage1.model.TvReview;
import com.pushpal.popularmoviesstage1.model.TvReviewResponse;
import com.pushpal.popularmoviesstage1.networking.RESTClient;
import com.pushpal.popularmoviesstage1.networking.RESTClientInterface;
import com.pushpal.popularmoviesstage1.utilities.Constants;
import com.pushpal.popularmoviesstage1.utilities.DateUtil;
import com.pushpal.popularmoviesstage1.utilities.TvUtils;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class TvDetailsActivity extends AppCompatActivity {

    private static final String TAG = TvDetailsActivity.class.getSimpleName();

    @BindView(R.id.iv_tv_poster)
    ImageView tvPoster;
    @BindView(R.id.tv_movie_title)
    TextView tvName;
    @BindView(R.id.tv_movie_release_date)
    TextView tvFirstAirDate;
    @BindView(R.id.tv_movie_language)
    TextView tvLanguage;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.tv_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_favourite)
    ShineButton likeButton;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.rv_cast)
    RecyclerView castRecyclerView;
    @BindView(R.id.rv_trailer)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.ll_trailers)
    LinearLayout trailerLayout;
    @BindView(R.id.rv_review)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.ll_reviews)
    LinearLayout reviewLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details);

        ButterKnife.bind(this);
        setUpActionBar();
        context = this;

        Bundle extras = getIntent().getExtras();
        Tv tv = null;
        if (extras != null)
            tv = extras.getParcelable(Constants.EXTRA_MOVIE_ITEM);

        if (tv != null) {
            fetchCredits(tv.getId());
            fetchReviews(tv.getId());

            tvName.setText(tv.getName());
            tvFirstAirDate.setText(DateUtil.getFormattedDate(tv.getFirstAirDate()));
            tvLanguage.setText(getLanguage(tv.getOriginalLanguage()));
            tvVoteAverage.setText(String.valueOf(tv.getVoteAverage()));
            String voteCount = String.valueOf(tv.getVoteCount()) + " " + getString(R.string.votes);
            tvVoteCount.setText(voteCount);
            tvOverview.setText(String.valueOf(tv.getOverview()));
            collapsingToolbarLayout.setTitle(tv.getName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String imageTransitionName = extras.getString(Constants.EXTRA_MOVIE_IMAGE_TRANSITION_NAME);
                tvPoster.setTransitionName(imageTransitionName);
            }

            final String imageURL = Constants.IMAGE_BASE_URL
                    + Constants.IMAGE_SIZE_342
                    + tv.getPosterPath();
            Picasso.with(this)
                    .load(imageURL)
                    .into(tvPoster, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportStartPostponedEnterTransition();
                        }
                    });

            if (TvUtils.isFavourite(tv))
                likeButton.setChecked(true);

            generateImageColor(imageURL);
            setUpLikeButton(tv);
        }
    }

    private void setUpActionBar() {
        supportPostponeEnterTransition();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void generateImageColor(final String imageURL) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageURL);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(@NonNull Palette palette) {
                            int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                            collapsingToolbarLayout.setContentScrimColor(mutedColor);
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    private String getLanguage(String languageAbbr) {
        return TvActivity.sLanguageMap.get(languageAbbr);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpLikeButton(final Tv finalTv) {
        likeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, final boolean checked) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String updateMessage;
                            if (checked) {
                                TvDatabase.getInstance(context)
                                        .tvDao()
                                        .insertTv(finalTv);
                                updateMessage = "Added to favourites";
                            } else {
                                TvDatabase.getInstance(context)
                                        .tvDao()
                                        .deleteTv(finalTv);
                                updateMessage = "Removed from favourites";
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, updateMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (SQLiteConstraintException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void fetchCredits(int tvId) {
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<TvCreditResponse> call = restClientInterface.getTvCredits(tvId, Constants.API_KEY);

        if (call != null) {
            call.enqueue(new retrofit2.Callback<TvCreditResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvCreditResponse> call,
                                       @NonNull Response<TvCreditResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            TvCreditResponse tvCreditResponse = response.body();
                            List<TvCast> casts = tvCreditResponse != null ? tvCreditResponse.getCast() : null;

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TvDetailsActivity.this,
                                    LinearLayoutManager.HORIZONTAL,
                                    false);

                            castRecyclerView.setLayoutManager(layoutManager);
                            castRecyclerView.setHasFixedSize(true);
                            castRecyclerView.setAdapter(new TvCastAdapter(casts));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TvCreditResponse> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e(TAG, throwable.toString());
                }
            });
        }
    }

    private void fetchReviews(int tvId) {
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<TvReviewResponse> call = restClientInterface.getTvReviews(tvId, Constants.API_KEY);

        if (call != null) {
            call.enqueue(new retrofit2.Callback<TvReviewResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvReviewResponse> call,
                                       @NonNull Response<TvReviewResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            TvReviewResponse tvReviewResponse = response.body();
                            List<TvReview> reviews = tvReviewResponse != null ? tvReviewResponse.getReviews() : null;

                            if (reviews != null && reviews.size() > 0) {
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TvDetailsActivity.this,
                                        LinearLayoutManager.VERTICAL,
                                        false);

                                reviewRecyclerView.setLayoutManager(layoutManager);
                                reviewRecyclerView.setHasFixedSize(true);
                                reviewRecyclerView.setAdapter(new TvReviewAdapter(reviews));
                            } else {
                                reviewLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TvReviewResponse> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e(TAG, throwable.toString());
                }
            });
        }
    }
}
