package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvCreditResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<TvCast> cast;

    @SerializedName("crew")
    private List<TvCrew> crew;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<TvCast> getCast() { return cast; }
    public void setCast(List<TvCast> cast){ this.cast = cast; }

    public List<TvCrew> getCrew() { return crew; }
    public void setCrew(List<TvCrew> crew) { this.crew = crew; }
}
