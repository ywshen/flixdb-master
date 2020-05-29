package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvVideoResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<TvVideos> videos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TvVideos> getVideos() {
        return videos;
    }

    public void setTrailers(List<TvVideos> videos) {
        this.videos = videos;
    }
}
