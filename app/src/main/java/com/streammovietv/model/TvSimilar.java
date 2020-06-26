package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

public class TvSimilar {
    @SerializedName("id")
    private String similarId;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("name")
    private String name;

    public String getSimilarId() { return similarId; }

    public void setSimilarId(String similarId) { this.similarId = similarId; }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
