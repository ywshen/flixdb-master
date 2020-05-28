package com.pushpal.popularmoviesstage1.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konifar.fab_transformation.FabTransformation;
import com.pushpal.popularmoviesstage1.R;
import com.pushpal.popularmoviesstage1.adapter.GridAutoFitLayoutManager;
import com.pushpal.popularmoviesstage1.adapter.TvClickListener;
import com.pushpal.popularmoviesstage1.adapter.TvAdapter;
import com.pushpal.popularmoviesstage1.database.TvViewModel;
import com.pushpal.popularmoviesstage1.model.Tv;
import com.pushpal.popularmoviesstage1.model.TvResponse;
import com.pushpal.popularmoviesstage1.networking.ConnectivityReceiver;
import com.pushpal.popularmoviesstage1.networking.RESTClient;
import com.pushpal.popularmoviesstage1.networking.RESTClientInterface;
import com.pushpal.popularmoviesstage1.utilities.Constants;
import com.pushpal.popularmoviesstage1.utilities.MovieApplication;
import com.pushpal.popularmoviesstage1.utilities.TvUtils;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class TvActivity extends AppCompatActivity implements
        ConnectivityReceiver.ConnectivityReceiverListener,
        TvClickListener {

    private static final String TAG = TvActivity.class.getSimpleName();
    public static Map<String, String> sLanguageMap;
    public static List<Tv> sFavouriteTvs;
    @BindView(R.id.poster_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.loader)
    NewtonCradleLoading loader;
    @BindView(R.id.loader_container)
    LinearLayout loaderContainer;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sort_layout)
    CardView sortLayout;
    @BindView(R.id.overlay)
    View overlayView;
    @BindView(R.id.toolbarExtended)
    TextView toolbarExt;
    @BindView(R.id.iv_popular_icon)
    ImageView popularIcon;
    @BindView(R.id.iv_top_rated_icon)
    ImageView topRatedIcon;
    @BindView(R.id.iv_favourite_icon)
    ImageView favouriteIcon;
    @BindView(R.id.iv_search_icon)
    ImageView searchIcon;
    private TvViewModel mTvViewModel;
    private GridAutoFitLayoutManager mLayoutManager;
    private TvAdapter mAdapter;
    private Snackbar mSnackBar;
    private int mCallPage, mCallPagePending, mAdapterPosition = 0;
    private String mSortCategory, mArrangementType, mResumeType = Constants.RESUME_NORMAL;
    private boolean mViewToggle = false;
    private List<Tv> mTvList;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        ButterKnife.bind(this);

        setUpActionBar();
        resetData();
        setupRecyclerView();
        implementPagination();

        startLoader();
        TvUtils.fetchLanguages();
        retrieveFavTvs();

        // Fetching page 1, top rated
        topRatedIcon.setColorFilter(getResources()
                .getColor(R.color.colorAccent));
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_flixdb, null));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.SORT_TYPE, mSortCategory);
        outState.putInt(Constants.CALL_PAGE, mCallPage);
        outState.putInt(Constants.CALL_PAGE_PENDING, mCallPagePending);
        outState.putInt(Constants.ADAPTER_POSITION, mLayoutManager.findFirstCompletelyVisibleItemPosition());
        outState.putString(Constants.ARRANGEMENT_TYPE, mAdapter.getArrangementType());

        mTvViewModel.setTvs(mTvList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCallPage = savedInstanceState.getInt(Constants.CALL_PAGE);
        mCallPagePending = savedInstanceState.getInt(Constants.CALL_PAGE_PENDING);

        if (savedInstanceState.containsKey(Constants.SORT_TYPE) &&
                savedInstanceState.containsKey(Constants.ADAPTER_POSITION)) {
            mSortCategory = savedInstanceState.getString(Constants.SORT_TYPE);
            mAdapterPosition = savedInstanceState.getInt(Constants.ADAPTER_POSITION);
            mArrangementType = savedInstanceState.getString(Constants.ARRANGEMENT_TYPE);
            resetAndSetIconFilters(mSortCategory);
            mResumeType = Constants.RESUME_AFTER_ROTATION;
        } else {
            mSortCategory = Constants.CATEGORY_TOP_RATED;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mResumeType.equals(Constants.RESUME_NORMAL)) {
            if (!mSortCategory.equals(Constants.CATEGORY_FAVOURITE))
                fetchTvs(mCallPage);
            else {
                setFavourite();
                dismissLoader();
                fab.setVisibility(View.VISIBLE);
            }
        } else if (mResumeType.equals(Constants.RESUME_AFTER_ROTATION)) {
            if (mTvList != null) {
                mTvList.clear();
                mTvList.addAll(mTvViewModel.getTvs());
            }

            toolbarExt.setText(mSortCategory);
            rearrangeRecyclerView(mArrangementType);

            dismissLoader();
            fab.setVisibility(View.VISIBLE);
            mResumeType = Constants.RESUME_NORMAL;
        }

        // Register connection status listener
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

        /* Register connection status listener */
        MovieApplication.getInstance().setConnectivityListener(this);
    }

    private void fetchTvs(final int page) {
        RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
        Call<TvResponse> call = null;

        switch (mSortCategory) {
            case Constants.CATEGORY_TOP_RATED:
                toolbarExt.setText(getString(R.string.action_top_rated));
                call = restClientInterface.getTopRatedTvs(Constants.API_KEY, page);
                break;
            case Constants.CATEGORY_MOST_POPULAR:
                toolbarExt.setText(getString(R.string.action_most_popular));
                call = restClientInterface.getPopularTvs(Constants.API_KEY, page);
                break;
        }

        if (call != null) {
            call.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvResponse> call, @NonNull Response<TvResponse> response) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        if (response.body().getResults() != null) {
                            mTvList.addAll(response.body().getResults());
                            mAdapter.notifyItemInserted(mTvList.size() - 1);
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.scheduleLayoutAnimation();
                            if (mAdapterPosition != 0) {
                                mRecyclerView.smoothScrollToPosition(mAdapterPosition);
                                mAdapterPosition = 0;
                            }
                        }
                    }
                    dismissLoader();
                    fab.setVisibility(View.VISIBLE);
                    showTapTargetView(fab);
                }

                @Override
                public void onFailure(@NonNull Call<TvResponse> call, @NonNull Throwable throwable) {
                    // Log error here since request failed
                    Log.e(TAG, throwable.toString());
                    dismissLoader();
                    mCallPagePending = mCallPage;
                    mCallPage--;
                }
            });
        }
    }

    private void resetData() {
        mTvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);

        if (mTvList != null)
            mTvList.clear();

        // By default sort order is set to top rated
        mSortCategory = Constants.CATEGORY_TOP_RATED;
        mCallPagePending = 0;
        mCallPage = 1;
    }

    private void implementPagination() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int totalItemCount = mLayoutManager.getItemCount();

                // mCallPage: Has value 1 at first, increments by 1
                // dy > 0: Checks if scroll direction is downwards
                // ((totalItemCount / 20) == mCallPage)): Checks if page needs to be incremented,
                // here the API returns 20 items per page

                if ((dy > 0) && ((totalItemCount / 20) == mCallPage)) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    // Checks whether reached near the end of recycler view, 10 items less than total items
                    if (pastVisibleItems + visibleItemCount >= (totalItemCount - 10)) {
                        mCallPage++;
                        fetchTvs(mCallPage);
                    }
                }
            }
        });
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            if (mSnackBar != null)
                mSnackBar.dismiss();
            if (mCallPagePending != 0) {
                fetchTvs(mCallPagePending);
                mCallPage = mCallPagePending;
                mCallPagePending = 0;
            }
        } else {
            showSnack();
        }
    }

    // Showing the status in Snack bar
    private void showSnack() {
        mSnackBar = Snackbar
                .make(mCoordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSnackBar.dismiss();
                    }
                });

        // Changing message text color
        mSnackBar.setActionTextColor(Color.GRAY);

        // Changing action button text color
        View snackBarView = mSnackBar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.LTGRAY);

        if (!mSnackBar.isShownOrQueued()) {
            mSnackBar.show();
        }
    }

    // Start loader
    private void startLoader() {
        loader.setLoadingColor(R.color.colorAccent);
        loaderContainer.setVisibility(View.VISIBLE);
        loader.start();
    }

    // Dismiss loader
    private void dismissLoader() {
        if (loader.isStart()) {
            loaderContainer.setVisibility(View.INVISIBLE);
            loader.stop();
        }
    }

    private void setupRecyclerView() {
        mTvList = new ArrayList<>();

        mAdapter = new TvAdapter(mTvList, Constants.ARRANGEMENT_COMPACT, this);
        mLayoutManager = new GridAutoFitLayoutManager(this, 300);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void rearrangeRecyclerView(String arrangementType) {

        if (!mResumeType.equals("rotated"))
            mAdapterPosition = mLayoutManager.findFirstVisibleItemPosition();

        mAdapter = new TvAdapter(mTvList, arrangementType, this);

        if (arrangementType.equals(Constants.ARRANGEMENT_COZY))
            mLayoutManager = new GridAutoFitLayoutManager(this, 500);
        else mLayoutManager = new GridAutoFitLayoutManager(this, 300);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();

        if (mAdapterPosition != 0) {
            mRecyclerView.scrollToPosition(mAdapterPosition);
            mAdapterPosition = 0;
        }
    }

    private void showTapTargetView(View targetView) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean(getString(R.string.isTapToTargetShown), false)) {
            // Show tap target view for FAB
            new MaterialTapTargetPrompt.Builder(TvActivity.this)
                    .setTarget(targetView)
                    .setPrimaryText("Sort mTvList")
                    .setSecondaryText("Tap the sort icon to select the order of mTvList.")
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                // User has pressed the prompt target
                            }
                        }
                    })
                    .show();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.isTapToTargetShown), true);
            editor.apply();
        }
    }

    @OnClick(R.id.fab)
    public void transformFabToLayout() {
        mRecyclerView.stopScroll();
        if (fab.getVisibility() == View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlayView).transformTo(sortLayout);
        }
    }

    @Override
    public void onBackPressed() {
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlayView).transformFrom(sortLayout);
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.overlay)
    void onClickOverlay() {
        if (fab.getVisibility() != View.VISIBLE) {
            try {
                FabTransformation.with(fab).setOverlay(overlayView).transformFrom(sortLayout);
            } catch (IllegalStateException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @OnClick(R.id.ll_most_popular)
    public void onMostPopularSelected() {
        if (!(mSortCategory.equals(Constants.CATEGORY_MOST_POPULAR))) {
            resetData();
            mSortCategory = Constants.CATEGORY_MOST_POPULAR;
            resetAndSetIconFilters(mSortCategory);
            fetchTvs(mCallPage);
        }
        onClickOverlay();
    }

    @OnClick(R.id.ll_top_rated)
    public void onTopRatedSelected() {
        if (!(mSortCategory.equals(Constants.CATEGORY_TOP_RATED))) {
            resetData();
            mSortCategory = Constants.CATEGORY_TOP_RATED;
            resetAndSetIconFilters(mSortCategory);
            fetchTvs(mCallPage);
        }
        onClickOverlay();
    }

    @OnClick(R.id.ll_favourite)
    public void onFavouriteSelected() {
        if (!(mSortCategory.equals(Constants.CATEGORY_FAVOURITE))) {
            resetData();
            mSortCategory = Constants.CATEGORY_FAVOURITE;
            resetAndSetIconFilters(mSortCategory);
            setFavourite();
        }
        onClickOverlay();
    }

    private void setFavourite() {
        toolbarExt.setText(getString(R.string.action_favourite));
        setFavList(sFavouriteTvs);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }

    @OnClick(R.id.ll_search)
    public void onSearchSelected() {
        Intent intent = new Intent(TvActivity.this, SearchActivity.class);
        TvActivity.this.startActivity(intent);
    }

    private void resetAndSetIconFilters(String sortCategory) {
        popularIcon.setColorFilter(getResources().getColor(R.color.colorIconGrey));
        topRatedIcon.setColorFilter(getResources().getColor(R.color.colorIconGrey));
        favouriteIcon.setColorFilter(getResources().getColor(R.color.colorIconGrey));

        switch (sortCategory) {
            case Constants.CATEGORY_FAVOURITE:
                favouriteIcon.setColorFilter(getResources().getColor(R.color.colorAccent));
                break;
            case Constants.CATEGORY_MOST_POPULAR:
                popularIcon.setColorFilter(getResources().getColor(R.color.colorAccent));
                break;
            case Constants.CATEGORY_TOP_RATED:
                topRatedIcon.setColorFilter(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tv, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_type) {
            Intent intent = new Intent(TvActivity.this, MainActivity.class);
            TvActivity.this.startActivity(intent);
        }
        return true;
    }

    @Override
    public void onTvClick(int pos, Tv tv, ImageView sharedImageView) {
        Intent intent = new Intent(this, TvDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE_ITEM, tv);
        intent.putExtra(Constants.EXTRA_MOVIE_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView));

        mTvViewModel.setTvs(mTvList);
        mResumeType = "intent";
        TvActivity.this.startActivity(intent, options.toBundle());
    }

    private void retrieveFavTvs() {
        if (sFavouriteTvs == null)
            sFavouriteTvs = new ArrayList<>();

        mTvViewModel.getFavTvs().observe(this, new Observer<List<Tv>>() {
            @Override
            public void onChanged(@Nullable List<Tv> tvs) {
                if (tvs != null) {
                    Log.e(TAG, "Tv Live Data changed in View Model.");

                    sFavouriteTvs.clear();
                    sFavouriteTvs.addAll(tvs);

                    if (mSortCategory.equals(Constants.CATEGORY_FAVOURITE))
                        setFavList(sFavouriteTvs);

                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.scheduleLayoutAnimation();
                }
            }
        });
    }

    private void setFavList(List<Tv> tvs) {
        this.mTvList.clear();
        this.mTvList.addAll(tvs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: .");
    }
}