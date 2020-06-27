package com.streammovietv.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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

import com.streammovietv.R;
import com.streammovietv.adapter.TvCastAdapter;
import com.streammovietv.adapter.TvReviewAdapter;
import com.streammovietv.adapter.TvSimilarAdapter;
import com.streammovietv.adapter.VideoAdapter;
import com.streammovietv.database.AppExecutors;
import com.streammovietv.database.TvDatabase;
import com.streammovietv.model.MovieTrailer;
import com.streammovietv.model.MovieTrailerResponse;
import com.streammovietv.model.Tv;
import com.streammovietv.model.TvCast;
import com.streammovietv.model.TvCreditResponse;
import com.streammovietv.model.TvResponse;
import com.streammovietv.model.TvReview;
import com.streammovietv.model.TvReviewResponse;
import com.streammovietv.model.TvSimilar;
import com.streammovietv.model.TvSimilarResponse;
import com.streammovietv.model.TvVideo;
import com.streammovietv.model.TvVideoResponse;
import com.streammovietv.networking.RESTClient;
import com.streammovietv.networking.RESTClientInterface;
import com.streammovietv.utilities.AppBarStateChangedListener;
import com.streammovietv.utilities.Constants;
import com.streammovietv.utilities.DateUtil;
import com.streammovietv.utilities.TvUtils;
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

public class  TvDetailsActivity extends AppCompatActivity {

    private static final String TAG = TvDetailsActivity.class.getSimpleName();

    @BindView(R.id.iv_tv_poster)
    ImageView tvPoster;
    @BindView(R.id.iv_backdrop)
    ImageView tvBackdrop;
    @BindView(R.id.tv_movie_title)
    TextView tvName;
    @BindView(R.id.tv_movie_release_date)
    TextView tvFirstAirDate;
    @BindView(R.id.tv_movie_language)
    TextView tvLanguage;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView((R.id.tv_overview))
    TextView tvOverview;
    @BindView(R.id.playTrailer)
    TextView playTrailer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_favourite)
    ShineButton likeButton;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.rv_cast)
    RecyclerView castRecyclerView;
    //@BindView(R.id.rv_trailer)
    //RecyclerView trailerRecyclerView;
    // @BindView(R.id.ll_trailers)
    //LinearLayout trailerLayout;
    @BindView(R.id.rv_similar)
    RecyclerView similarRecyclerView;
    @BindView(R.id.ll_similar)
    LinearLayout similarLayout;
    @BindView(R.id.rv_review)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.ll_reviews)
    LinearLayout reviewLayout;
    private Context context;
    @BindView(R.id.star1)
    ImageView star1;
    @BindView(R.id.star2)
    ImageView star2;
    @BindView(R.id.star3)
    ImageView star3;
    @BindView(R.id.star4)
    ImageView star4;
    @BindView(R.id.star5)
    ImageView star5;
    @BindView(R.id.PlayButton)
    FloatingActionButton PlayButton;
    @BindView(R.id.tv_rating)
    ConstraintLayout tvRating;

    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details_new);

        ButterKnife.bind(this);
        setUpActionBar();
        context = this;

        Bundle extras = getIntent().getExtras();
        Tv tv = null;
        if (extras != null)
            tv = extras.getParcelable(Constants.EXTRA_MOVIE_ITEM);

        if (tv != null) {
            fetchTvCredits(tv.getId());
            //fetchTrailers(tv.getId());
            fetchReviews(tv.getId());
            fetchFirstTrailer(tv.getId());
            fetchSimilar(tv.getId());

            tvName.setText(tv.getName());
            tvFirstAirDate.setText(DateUtil.getFormattedDate(tv.getFirstAirDate()));
            tvLanguage.setText(getLanguage(tv.getOriginalLanguage()));
            tvVoteAverage.setText(String.valueOf(tv.getVoteAverage()));
            tvOverview.setText(String.valueOf(tv.getOverview()));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String imageTransitionName = extras.getString(Constants.EXTRA_MOVIE_IMAGE_TRANSITION_NAME);
                tvPoster.setTransitionName(imageTransitionName);
            }

            final String posterURL = Constants.IMAGE_BASE_URL
                    + Constants.IMAGE_SIZE_342
                    + tv.getPosterPath();
            Picasso.with(this)
                    .load(posterURL)
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


            final String backdropURL = Constants.IMAGE_BASE_URL
                    + Constants.IMAGE_SIZE_342
                    + tv.getBackdropPath();
            Picasso.with(this)
                    .load(backdropURL)
                    .into(tvBackdrop, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportStartPostponedEnterTransition();
                        }
                    });

            if ( TvUtils.isFavourite(tv))
                likeButton.setChecked(true);

            generateImageColor(backdropURL);
            setUpLikeButton(tv);

            appBarLayout = findViewById(R.id.appBarLayout);
            final Tv finalTv = tv;
            appBarLayout.addOnOffsetChangedListener(new AppBarStateChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    float percentage = (appBarLayout.getTotalScrollRange() - (float) Math.abs(verticalOffset)) / appBarLayout.getTotalScrollRange();

                    if (percentage < 0.2) {
                        tvPoster.setVisibility(View.GONE);
                        likeButton.setVisibility(View.GONE);
                        tvRating.setVisibility(View.GONE);
                        collapsingToolbarLayout.setTitle(finalTv.getName());
                    } else if (percentage > 0.2) {
                        tvPoster.setVisibility(View.VISIBLE);
                        likeButton.setVisibility(View.VISIBLE);
                        tvRating.setVisibility(View.VISIBLE);
                        collapsingToolbarLayout.setTitle(" ");
                    }
                }
            });
            setStarRating(tv.getVoteAverage());
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

    private void fetchTvCredits(int tvId) {
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
                            List<TvCast> tvCasts = tvCreditResponse != null ? tvCreditResponse.getCast() : null;

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TvDetailsActivity.this,
                                    LinearLayoutManager.HORIZONTAL,
                                    false);

                            castRecyclerView.setLayoutManager(layoutManager);
                            castRecyclerView.setHasFixedSize(true);
                            castRecyclerView.setAdapter(new TvCastAdapter(tvCasts));
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

  /*  private void fetchTrailers(int tvId) {
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<TvVideoResponse> call = restClientInterface.getVideos(tvId, Constants.API_KEY);

        if (call != null) {
            call.enqueue(new retrofit2.Callback<TvVideoResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvVideoResponse> call,
                                       @NonNull Response<TvVideoResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            TvVideoResponse tvVideoResponse = response.body();
                            List<TvVideos> videos = tvVideoResponse != null ? tvVideoResponse.getVideos() : null;

                            if (videos != null && videos.size() > 0) {
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TvDetailsActivity.this,
                                        LinearLayoutManager.HORIZONTAL,
                                        false);

                                trailerRecyclerView.setLayoutManager(layoutManager);
                                trailerRecyclerView.setHasFixedSize(true);
                                trailerRecyclerView.setAdapter(new VideoAdapter(videos));
                            } else {
                                trailerLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TvVideoResponse> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e(TAG, throwable.toString());
                }
            });
        }
    } */

    private void fetchFirstTrailer(int movieId){
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<TvVideoResponse> call = restClientInterface.getVideos(movieId, Constants.API_KEY);

        if (call != null) {
            call.enqueue(new retrofit2.Callback<TvVideoResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvVideoResponse> call,
                                       @NonNull Response<TvVideoResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            TvVideoResponse tvVideoResponse = response.body();
                            final List<TvVideo> videos = tvVideoResponse != null ? tvVideoResponse.getVideo() : null;

                            if (videos != null && videos.size() > 0) {
                                playTrailer.setText("Play Trailer");
                                PlayButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        context.startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("vnd.youtube://" + videos.get(0).getVideoKey())));
                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TvVideoResponse> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e(TAG, throwable.toString());
                }
            });
        }
    }

    private  void fetchSimilar(int tvId) {
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<TvSimilarResponse> call = restClientInterface.getTvSimilar(tvId, Constants.API_KEY, 1);

        if (call != null) {
            call.enqueue(new retrofit2.Callback<TvSimilarResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvSimilarResponse> call,
                                       @NonNull Response<TvSimilarResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body() != null) {
                            TvSimilarResponse tvSimilarResponse = response.body();
                            final List<TvSimilar> similar = tvSimilarResponse != null ?tvSimilarResponse.getSimilar() : null;

                            if (similar != null && similar.size() > 0) {
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TvDetailsActivity.this,
                                        LinearLayoutManager.HORIZONTAL,
                                        false);

                                similarRecyclerView.setLayoutManager(layoutManager);
                                similarRecyclerView.setHasFixedSize(true);
                                similarRecyclerView.setAdapter(new TvSimilarAdapter(similar));
                            } else {
                                similarLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TvSimilarResponse> call, @NonNull Throwable throwable) {
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

    private void setStarRating(double voteAverage) {
        if (voteAverage <= 0.5) {
            star1.setImageResource(R.drawable.star_half);
        } else if (voteAverage > 0.5 && voteAverage <= 1.0 ) {
            star1.setImageResource(R.drawable.star);
        } else if (voteAverage > 1.0 && voteAverage <= 1.5) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star_half);
        } else if (voteAverage > 1.5 && voteAverage <= 2.0) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
        }  else if (voteAverage > 2.0 && voteAverage <= 2.5) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star_half);
        } else if (voteAverage > 2.5 && voteAverage <= 3.0 || voteAverage > 5.0 && voteAverage <= 6.0) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
        } else if (voteAverage > 3.0 && voteAverage <= 3.5 || voteAverage > 6.0 && voteAverage <= 7.0) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star_half);
        } else if (voteAverage > 3.5 && voteAverage <= 4.0 || voteAverage > 7.0 && voteAverage <= 8.0 ) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
        } else if (voteAverage > 4.0 && voteAverage <= 4.5 || voteAverage > 8.0 && voteAverage <= 9.0) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
            star5.setImageResource(R.drawable.star_half);
        } else if (voteAverage > 4.5 && voteAverage <= 5.0 || voteAverage > 9 && voteAverage <= 10) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
            star5.setImageResource(R.drawable.star);
        }
    }
}