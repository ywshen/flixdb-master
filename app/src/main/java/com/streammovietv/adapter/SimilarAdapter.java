package com.streammovietv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.streammovietv.R;
import com.streammovietv.activity.MainActivity;
import com.streammovietv.model.MovieSimilar;
import com.streammovietv.utilities.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder> {

    private final List<MovieSimilar> similar;
    private Context context;

    public SimilarAdapter(List<MovieSimilar> similar) {
        this.similar = similar;
    }

    @NonNull
    @Override
    public SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new SimilarViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_similar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SimilarViewHolder holder, final int position) {
        final MovieSimilar movie = similar.get(position);
        String imageURL = Constants.IMAGE_BASE_URL
                + Constants.IMAGE_SIZE_185
                + movie.getPosterPath();
        Picasso.with(((SimilarViewHolder) holder).itemView.getContext())
                .load(imageURL)
                .fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.pop_mov_plain_logo)
                .into(holder.similarPosterImageView);
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

    public List<MovieSimilar> getMovies() {
        return similar;
    }

    static class SimilarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_similar)
        ImageView similarPosterImageView;

        @BindView(R.id.card_view)
        CardView cardView;

        SimilarViewHolder(View itemView) {
            super(itemView);

            // ButterKnife Binding
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return similar.size();
    }

}
