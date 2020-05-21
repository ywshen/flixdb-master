package com.pushpal.popularmoviesstage1.activity;

import android.annotation.SuppressLint;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.konifar.fab_transformation.FabTransformation;
import com.pushpal.popularmoviesstage1.R;
import com.pushpal.popularmoviesstage1.adapter.GridAutoFitLayoutManager;
import com.pushpal.popularmoviesstage1.adapter.MovieAdapter;
import com.pushpal.popularmoviesstage1.adapter.MovieClickListener;
import com.pushpal.popularmoviesstage1.adapter.SearchAdapter;
import com.pushpal.popularmoviesstage1.adapter.SearchClickListener;
import com.pushpal.popularmoviesstage1.database.MainViewModel;
import com.pushpal.popularmoviesstage1.database.SearchViewModel;
import com.pushpal.popularmoviesstage1.model.Movie;
import com.pushpal.popularmoviesstage1.model.MovieResponse;
import com.pushpal.popularmoviesstage1.model.Search;
import com.pushpal.popularmoviesstage1.model.SearchResponse;
import com.pushpal.popularmoviesstage1.networking.ConnectivityReceiver;
import com.pushpal.popularmoviesstage1.networking.RESTClient;
import com.pushpal.popularmoviesstage1.networking.RESTClientInterface;
import com.pushpal.popularmoviesstage1.utilities.Constants;
import com.pushpal.popularmoviesstage1.utilities.MovieApplication;
import com.pushpal.popularmoviesstage1.utilities.MovieUtils;
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

public class SearchActivity extends AppCompatActivity implements
        ConnectivityReceiver.ConnectivityReceiverListener,
        SearchClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();
    @BindView(R.id.poster_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.loader)
    NewtonCradleLoading loader;
    @BindView(R.id.loader_container)
    LinearLayout loaderContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.overlay)
    View overlayView;
    private SearchViewModel mSearchViewModel;
    private GridAutoFitLayoutManager mLayoutManager;
    private SearchAdapter mAdapter;
    private Snackbar mSnackBar;
    private int mCallPage, mCallPagePending, mAdapterPosition = 0;
    private String mSortCategory, mArrangementType, mResumeType = Constants.RESUME_NORMAL;
    private boolean mViewToggle = false;
    private List<Search> mSearchList;
    private SearchView editSearch;
    private String searchQuery;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setUpActionBar();
        resetData();
        setupRecyclerView();
        implementPagination();

        startLoader();

        editSearch = (SearchView) findViewById(R.id.searchView);
        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                resetData();
                fetchSearches(mCallPage, searchQuery);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
         });
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

        mSearchViewModel.setSearches(mSearchList);
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
            mResumeType = Constants.RESUME_AFTER_ROTATION;
        } else {
            mSortCategory = Constants.CATEGORY_TOP_RATED;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mResumeType.equals(Constants.RESUME_NORMAL)) {
            if (mResumeType.equals(Constants.RESUME_AFTER_ROTATION)) {
                if (mSearchList != null) {
                    mSearchList.clear();
                    mSearchList.addAll(mSearchViewModel.getSearches());
                }
                rearrangeRecyclerView(mArrangementType);

                dismissLoader();
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
    }
        private void fetchSearches ( final int page, final String query){
            RESTClientInterface restClientInterface = RESTClient.getClient().create(RESTClientInterface.class);
            Call<SearchResponse> call = null;


            switch (mSortCategory) {
                case Constants.CATEGORY_SEARCH:
                    call = restClientInterface.getMultiSearch(Constants.API_KEY, query, page);
                    break;
            }

            if (call != null) {
                call.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                        int statusCode = response.code();

                        if (statusCode == 200) {
                            if (response.body().getResults() != null) {
                                mSearchList.addAll(response.body().getResults());
                                mAdapter.notifyItemInserted(mSearchList.size() - 1);
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.scheduleLayoutAnimation();
                                if (mAdapterPosition != 0) {
                                    mRecyclerView.smoothScrollToPosition(mAdapterPosition);
                                    mAdapterPosition = 0;
                                }
                            }
                        }
                        dismissLoader();
                    }

                    @Override
                    public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable throwable) {
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
        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        if (mSearchList != null)
            mSearchList.clear();

        // By default sort order is set to top rated
        mSortCategory = Constants.CATEGORY_SEARCH;
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
                        fetchSearches(mCallPage, searchQuery);
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
                fetchSearches(mCallPagePending, searchQuery);
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
        mSearchList = new ArrayList<>();

        mAdapter = new SearchAdapter(mSearchList, Constants.ARRANGEMENT_COMPACT, this);
        mLayoutManager = new GridAutoFitLayoutManager(this, 300);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void rearrangeRecyclerView(String arrangementType) {

        if (!mResumeType.equals("rotated"))
            mAdapterPosition = mLayoutManager.findFirstVisibleItemPosition();

        mAdapter = new SearchAdapter(mSearchList, arrangementType, this);


        mLayoutManager = new GridAutoFitLayoutManager(this, 500);

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
            new MaterialTapTargetPrompt.Builder(SearchActivity.this)
                    .setTarget(targetView)
                    .setPrimaryText("Sort mMovieList")
                    .setSecondaryText("Tap the sort icon to select the order of mMovieList.")
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_type) {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            SearchActivity.this.startActivity(intent);
        }
        return true;
    }

    @Override
    public void onSearchClick(int pos, Search search, ImageView sharedImageView) {
        Intent intent;
        if(search.getTitle() != null){
            intent = new Intent(this, DetailsActivity.class);
        } else {
            intent = new Intent(this, TvDetailsActivity.class);
        }
        intent.putExtra(Constants.EXTRA_MOVIE_ITEM, search);
        intent.putExtra(Constants.EXTRA_MOVIE_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView));

        mSearchViewModel.setSearches(mSearchList);
        mResumeType = "intent";
        startActivity(intent, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: .");
    }
}