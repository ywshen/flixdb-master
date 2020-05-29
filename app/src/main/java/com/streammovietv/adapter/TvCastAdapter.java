package com.streammovietv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.streammovietv.R;
import com.streammovietv.activity.TvCastActivity;
import com.streammovietv.model.TvCast;
import com.streammovietv.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvCastAdapter extends RecyclerView.Adapter<TvCastAdapter.CastViewHolder> {

    private Context context;
    private final List<TvCast> casts;

    public TvCastAdapter(List<TvCast> casts) {
        this.casts = casts;
    }

    @NonNull
    @Override
    public TvCastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new TvCastAdapter.CastViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvCastAdapter.CastViewHolder holder, int position) {
        final TvCast tvCast = casts.get(position);
        holder.castName.setText(tvCast.getActorName());
        holder.characterName.setText((tvCast.getCharacterName()));
        String imageURL = Constants.IMAGE_BASE_URL
                + Constants.IMAGE_SIZE_185
                + tvCast.getProfileImagePath();
        Picasso.with(context)
                .load(imageURL)
                .placeholder(R.drawable.person)
                .into(holder.castImage);

        holder.castLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personIntent = new Intent(context, TvCastActivity.class);
                personIntent.putExtra("PERSON_ID",tvCast.getId());
                context.startActivity(personIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cast_image)
        ImageView castImage;

        @BindView(R.id.tv_cast_name)
        TextView castName;

        @BindView(R.id.tv_character_name)
        TextView characterName;

        @BindView(R.id.ll_cast)
        LinearLayout castLayout;

        CastViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
