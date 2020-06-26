package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvSimilarResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<TvSimilar> similar;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public List<TvSimilar> getSimilar() { return similar; }

    public void setSimilar(List<TvSimilar>similar) { this.similar = similar; }
}
