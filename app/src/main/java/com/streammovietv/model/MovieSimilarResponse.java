package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSimilarResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("similar")
    private List<MovieSimilar> similar;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public List<MovieSimilar> getSimilar() { return similar; }

    public void setSimilar(List<MovieSimilar>similar) { this.similar = similar; } 
}
