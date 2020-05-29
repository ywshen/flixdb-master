package com.streammovietv.adapter;

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

import com.streammovietv.R;
import com.streammovietv.activity.TvActivity;
import com.streammovietv.database.AppExecutors;
import com.streammovietv.database.TvDatabase;
import com.streammovietv.model.Tv;
import com.streammovietv.utilities.Constants;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = TvAdapter.class.getSimpleName();
    private final TvClickListener tvClickListener;
    private final List<Tv> tvs;
    private final String arrangementType;
    private Context context;

    public TvAdapter(List<Tv> tv, String arrangementType, TvClickListener tvClickListener) {
        this.tvs = tv;
        this.arrangementType = arrangementType;
        this.tvClickListener = tvClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.context = parent.getContext();

        if (arrangementType.equals(Constants.ARRANGEMENT_COMPACT))
            return new TvAdapter.TvCompactViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie_compact, parent, false));
        else if (arrangementType.equals(Constants.ARRANGEMENT_COZY))
            return new TvAdapter.TvCozyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie_cozy, parent, false));

        return null;
    }

    @Override
    public int getItemCount() {
        return tvs.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Tv tv = tvs.get(position);
        if (arrangementType.equals(Constants.ARRANGEMENT_COMPACT)) {
            String imageURL = Constants.IMAGE_BASE_URL
                    + Constants.IMAGE_SIZE_185
                    + tv.getPosterPath();
            Picasso.with(((TvAdapter.TvCompactViewHolder) holder).itemView.getContext())
                    .load(imageURL)
                    .fit().centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.pop_mov_plain_logo)
                    .into(((TvAdapter.TvCompactViewHolder) holder).tvPosterImageView);

            ((TvAdapter.TvCompactViewHolder) holder).voteCount
                    .setText(String.valueOf(tv.getVoteAverage()));

            ViewCompat.setTransitionName(((TvAdapter.TvCompactViewHolder) holder)
                    .tvPosterImageView, tv.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvClickListener.onTvClick(holder.getAdapterPosition(), tv,
                            ((TvAdapter.TvCompactViewHolder) holder).tvPosterImageView);
                }
            });
        } else if (arrangementType.equals(Constants.ARRANGEMENT_COZY)) {
            ((TvAdapter.TvCozyViewHolder) holder).tvName.setText(tv.getName());
            String date[] = tv.getFirstAirDate().split("-");
            String releaseYear = date[0];
            ((TvCozyViewHolder) holder).tvFirstAirDate.setText(String.valueOf(releaseYear));

            String imageURL = Constants.IMAGE_BASE_URL
                    + Constants.IMAGE_SIZE_342
                    + tv.getPosterPath();
            Picasso.with(((TvAdapter.TvCozyViewHolder) holder).itemView.getContext())
                    .load(imageURL)
                    .fit().centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.pop_mov_plain_logo)
                    .into(((TvAdapter.TvCozyViewHolder) holder).tvPosterImageView);

            ViewCompat.setTransitionName(((TvAdapter.TvCozyViewHolder) holder)
                    .tvPosterImageView, tv.getName());

            ((TvAdapter.TvCozyViewHolder) holder).voteCount
                    .setText(String.valueOf(tv.getVoteAverage()));

            ((TvAdapter.TvCozyViewHolder) holder).tvLanguage
                    .setText(getLanguage(tv.getOriginalLanguage()));

            if (isFavourite(tv))
                ((TvAdapter.TvCozyViewHolder) holder).likeButton.setChecked(true);
            else
                ((TvAdapter.TvCozyViewHolder) holder).likeButton.setChecked(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvClickListener.onTvClick(holder.getAdapterPosition(), tv,
                            ((TvAdapter.TvCozyViewHolder) holder).tvPosterImageView);
                }
            });

            ((TvAdapter.TvCozyViewHolder) holder).likeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
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
                                            .insertTv(tv);
                                    updateMessage = "Added to favourites";
                                } else {
                                    TvDatabase.getInstance(context)
                                            .tvDao()
                                            .deleteTv(tv);
                                    updateMessage = "Removed from favourites";
                                }

                                ((Activity) context).runOnUiThread(new Runnable() {
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
    }

    private String getLanguage(String languageAbbr) {
        return TvActivity.sLanguageMap.get(languageAbbr);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public List<Tv> getTvs() {
        return tvs;
    }

    private boolean isFavourite(Tv tv) {
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

    public String getArrangementType() {
        return arrangementType;
    }

    // Compact View Holder
    static class TvCompactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView tvPosterImageView;

        @BindView(R.id.tv_vote_count)
        TextView voteCount;

        TvCompactViewHolder(View itemView) {
            super(itemView);

            // ButterKnife Binding
            ButterKnife.bind(this, itemView);
        }
    }

    static class TvCozyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView tvPosterImageView;
        @BindView(R.id.tv_movie_title)
        TextView tvName;
        @BindView(R.id.tv_movie_release_year)
        TextView tvFirstAirDate;
        @BindView(R.id.btn_like)
        ShineButton likeButton;
        @BindView(R.id.tv_vote_count)
        TextView voteCount;
        @BindView(R.id.tv_movie_language)
        TextView tvLanguage;

        TvCozyViewHolder(View itemView) {
            super(itemView);

            // ButterKnife Binding
            ButterKnife.bind(this, itemView);
        }
    }
}
