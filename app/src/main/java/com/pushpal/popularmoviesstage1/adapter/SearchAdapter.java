package com.pushpal.popularmoviesstage1.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pushpal.popularmoviesstage1.R;
import com.pushpal.popularmoviesstage1.activity.MainActivity;
import com.pushpal.popularmoviesstage1.database.AppExecutors;
import com.pushpal.popularmoviesstage1.database.MovieDatabase;
import com.pushpal.popularmoviesstage1.model.Movie;
import com.pushpal.popularmoviesstage1.model.Search;
import com.pushpal.popularmoviesstage1.utilities.Constants;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SearchAdapter.class.getSimpleName();
    private final SearchClickListener searchClickListener;
    private final List<Search> results;
    private final String arrangementType;
    private Context context;

    public SearchAdapter(List<Search> results, String arrangementType, SearchClickListener searchClickListener) {
        this.results = results;
        this.arrangementType = arrangementType;
        this.searchClickListener = searchClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.context = parent.getContext();

        if (arrangementType.equals(Constants.ARRANGEMENT_COMPACT))
            return new MovieCompactViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie_compact, parent, false));
        else if (arrangementType.equals(Constants.ARRANGEMENT_COZY))
            return new MovieCozyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie_cozy, parent, false));

        return null;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Search result = results.get(position);
        if (arrangementType.equals(Constants.ARRANGEMENT_COMPACT)) {
            String imageURL = Constants.IMAGE_BASE_URL
                    + Constants.IMAGE_SIZE_185
                    + result.getPosterPath();
            Picasso.with(((MovieCompactViewHolder) holder).itemView.getContext())
                    .load(imageURL)
                    .fit().centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.pop_mov_plain_logo)
                    .into(((MovieCompactViewHolder) holder).moviePosterImageView);

            ((MovieCompactViewHolder) holder).voteCount
                    .setText(String.valueOf(result.getVoteAverage()));

            if (result.getTitle() != null){
                ViewCompat.setTransitionName(((MovieCompactViewHolder) holder)
                        .moviePosterImageView, result.getTitle());
            }
            if (result.getName() != null){
                ViewCompat.setTransitionName(((MovieCompactViewHolder) holder)
                        .moviePosterImageView, result.getName());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchClickListener.onSearchClick(holder.getAdapterPosition(),  result,
                            ((MovieCompactViewHolder) holder).moviePosterImageView);
                }
            });
        }
    }

    private String getLanguage(String languageAbbr) {
        return MainActivity.sLanguageMap.get(languageAbbr);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public List<Search> getResults() {
        return results;
    }

    public String getArrangementType() {
        return arrangementType;
    }

    // Compact View Holder
    static class MovieCompactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView moviePosterImageView;

        @BindView(R.id.tv_vote_count)
        TextView voteCount;

        MovieCompactViewHolder(View itemView) {
            super(itemView);

            // ButterKnife Binding
            ButterKnife.bind(this, itemView);
        }
    }

    // Cozy View Holder
    static class MovieCozyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView moviePosterImageView;
        @BindView(R.id.tv_movie_title)
        TextView movieTitle;
        @BindView(R.id.tv_movie_release_year)
        TextView movieReleaseYear;
        @BindView(R.id.btn_like)
        ShineButton likeButton;
        @BindView(R.id.tv_vote_count)
        TextView voteCount;
        @BindView(R.id.tv_movie_language)
        TextView movieLanguage;

        MovieCozyViewHolder(View itemView) {
            super(itemView);

            // ButterKnife Binding
            ButterKnife.bind(this, itemView);
        }
    }
}