package com.pushpal.popularmoviesstage1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvEpisodes implements Parcelable{
    public static final Parcelable.Creator<TvEpisodes> CREATOR = new Parcelable.Creator<TvEpisodes>() {
        @Override
        public TvEpisodes createFromParcel(Parcel in) {
            return new TvEpisodes(in);
        }

        @Override
        public TvEpisodes[] newArray(int size) {
            return new TvEpisodes[size];
        }
    };
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id;
    @ColumnInfo(name = "still_path")
    @SerializedName("still_path")
    private String stillPath;
    @ColumnInfo(name = "air_date")
    @SerializedName("air_date")
    private String airDate;
    @ColumnInfo(name = "season_number")
    @SerializedName("season_number")
    private Integer seasonNumber;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double voteAverage;
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private Integer voteCount;

    public TvEpisodes(String stillPath, String airDate, String overview, String name,
                      Double voteAverage, Integer id, Integer seasonNumber, Integer voteCount) {
        this.stillPath = stillPath;
        this.airDate = airDate;
        this.overview = overview;
        this.name = name;
        this.voteAverage = voteAverage;
        this.id = id;
        this.seasonNumber = seasonNumber;
        this.voteCount = voteCount;
    }
    private TvEpisodes(Parcel in) {
        stillPath = in.readString();
        airDate = in.readString();
        overview = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
    }

    public String getStillPath() { return stillPath; }

    public void setStillPath(String stillPath) { this.stillPath = stillPath; }

    public String getAirDate() { return airDate; }

    public void setAirDate(String airDate) { this.airDate = airDate; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Double getVoteAverage() { return voteAverage; }

    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getVoteCount() { return voteCount; }

    public void setVoteCount(Integer voteCount) { this.voteCount = voteCount; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(stillPath);
        parcel.writeString(airDate);
        parcel.writeString(overview);
        parcel.writeString(name);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (voteCount == null) {
           parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
    }
}
