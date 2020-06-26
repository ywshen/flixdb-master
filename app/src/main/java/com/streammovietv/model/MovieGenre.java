package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

public class MovieGenre {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String  name;

    public MovieGenre(int id,String name )
    {
        this.id = id;
        this.name = name;
    }

    public String getNname()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
