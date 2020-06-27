package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvVideoResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<TvVideo> video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TvVideo> getVideo() { return video; }

    public void setTrailers(List<TvVideo> videos) {
        this.video = videos;
    }
}
