package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

public class MovieSimilar {
    @SerializedName("id")
    private String similarId;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    public String getSimilarId() { return similarId; }

    public void setSimilarId(String similarId) { this.similarId = similarId; }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
