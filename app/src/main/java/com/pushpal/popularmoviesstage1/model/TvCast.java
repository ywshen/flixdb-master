package com.pushpal.popularmoviesstage1.model;

import com.google.gson.annotations.SerializedName;

public class TvCast {
    @SerializedName("character")
    private String character;

    @SerializedName("credit_it")
    private String creditId;

    @SerializedName("gender")
    private int gender;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String actorName;

    @SerializedName("order")
    private int order;

    @SerializedName("profile_path")
    private String profilePath;


    public String getCharacter() { return character; }

    public void setCharacter(String character) { this.character = character; }

    public String getCreditId() { return creditId; }

    public void setCreditId(String creditId) { this.creditId = creditId; }

    public int getGender() { return gender; }

    public void setGender(int gender) { this.gender = gender; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getActorName() { return actorName; }

    public void setActorName(String actorName) { this.actorName = actorName; }

    public int getOrder() { return order; }

    public void setOrder(int order) { this.order = order; }

    public String getProfilePath() { return profilePath; }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
