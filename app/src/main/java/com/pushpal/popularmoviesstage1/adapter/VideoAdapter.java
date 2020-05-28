package com.pushpal.popularmoviesstage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pushpal.popularmoviesstage1.R;
import com.pushpal.popularmoviesstage1.model.TvVideos;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private final List<TvVideos> videos;

    public VideoAdapter(List<TvVideos> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new VideoAdapter.VideoViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        final TvVideos tvCast = videos.get(position);

        String imageURL = "http://img.youtube.com/vi/" + tvCast.getVideoKey() + "/mqdefault.jpg";
        Picasso.with(context)
                .load(imageURL)
                .placeholder(R.drawable.person)
                .into(holder.videoImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("vnd.youtube://" + tvCast.getVideoKey())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_trailer)
        ImageView videoImage;

        @BindView(R.id.card_view)
        CardView cardView;

        VideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
