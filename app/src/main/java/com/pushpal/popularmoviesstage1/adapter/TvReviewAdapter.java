package com.pushpal.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pushpal.popularmoviesstage1.R;
import com.pushpal.popularmoviesstage1.model.TvReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvReviewAdapter extends RecyclerView.Adapter<TvReviewAdapter.ReviewViewHolder> {

    private final List<TvReview> reviews;

    public TvReviewAdapter(List<TvReview> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public TvReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        return new TvReviewAdapter.ReviewViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvReviewAdapter.ReviewViewHolder holder, int position) {
        TvReview tvReview = reviews.get(position);
        holder.reviewer.setText(tvReview.getReviewer());
        holder.review.setText(tvReview.getReviewContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_reviewer)
        TextView reviewer;

        @BindView(R.id.tv_review)
        TextView review;

        ReviewViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
