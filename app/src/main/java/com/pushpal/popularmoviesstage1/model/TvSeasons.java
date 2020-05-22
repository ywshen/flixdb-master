package com.pushpal.popularmoviesstage1.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "tvseasons")

public class TvSeasons implements Parcelable {
    public static final Creator<TvSeasons> CREATOR = new Creator<TvSeasons>() {
        @Override
        public TvSeasons createFromParcel(Parcel in) {
            return new TvSeasons(in);
        }

        @Override
        public TvSeasons[] newArray(int size) {
            return new TvSeasons[size];
        }
    };
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "air_date")
    @SerializedName("air_date")
    private String airDate;
    @ColumnInfo(name = "episode_id")
    @SerializedName("episode_id")
    private Integer episodeId;
    @ColumnInfo(name = "episode_name")
    @SerializedName("episode_name")
    private String episodeName;
    @ColumnInfo(name = "episode_number")
    @SerializedName("episode_number")
    private Integer episodeNumber;
    @ColumnInfo(name = "episode_still_path")
    @SerializedName("episode_still_path")
    private String episodeStillPath;
    @ColumnInfo(name = "episode_vote_average")
    @SerializedName("episode_vote_average")
    private Double episodeVoteAveraage;
    @ColumnInfo(name = "episode_vote_count")
    @SerializedName("episode_vote_count")
    private Integer episodeVoteCount;

    public TvSeasons(String posterPath, String overview, String episodeName, String episodeStillPath, String airDate,
                     Double episodeVoteAveraage, Integer id, Integer episodeId, Integer episodeNumber, Integer episodeVoteCount){
        this.posterPath = posterPath;
        this.overview = overview;
        this.episodeName = episodeName;
        this.episodeStillPath = episodeStillPath;
        this.airDate = airDate;
        this.episodeVoteAveraage = episodeVoteAveraage;
        this.id = id;
        this.episodeId = episodeId;
        this.episodeNumber = episodeNumber;
        this.episodeVoteCount = episodeVoteCount;
    }

    private TvSeasons(Parcel in){
        posterPath = in.readString();
        overview = in.readString();
        episodeName = in.readString();
        episodeStillPath = in.readString();
        airDate = in.readString();
        if (in.readByte() == 0){
            episodeVoteAveraage = null;
        } else {
            episodeVoteAveraage = in.readDouble();
        }
        if (in.readByte() == 0){
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0){
            episodeId = null;
        } else {
            episodeId = in.readInt();
        }
        if (in.readByte() == 0){
            episodeNumber = null;
        } else {
            episodeNumber = in.readInt();
        }
        if (in.readByte() == 0){
            episodeVoteCount = 0;
        } else {
            episodeVoteCount = in.readInt();
        }
    }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getEpisodeName() { return episodeName; }

    public void setEpisodeName(String episodeName){ this.episodeName = episodeName; }

    public String getEpisodeStillPath() { return episodeStillPath; }

    public void setEpisodeStillPath(String episodeStillPath) { this.episodeStillPath = episodeStillPath; }

    public String getAirDate() { return airDate; }

    public void setAirDate(String airDate) { this.airDate = airDate; }

    public Double getEpisodeVoteAveraage() { return episodeVoteAveraage; }

    public void setEpisodeVoteAveraage(Double episodeVoteAveraage) { this.episodeVoteAveraage = episodeVoteAveraage; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getEpisodeId() { return episodeId; }

    public void setEpisodeId(Integer episodeId) { this.episodeId = episodeId; }

    public Integer getEpisodeNumber() { return episodeNumber; }

    public void setEpisodeNumber(Integer episodeNumber) { this.episodeNumber = episodeNumber; }

    public Integer getEpisodeVoteCount() { return episodeVoteCount; }

    public void setEpisodeVoteCount(Integer episodeVoteCount) { this.episodeVoteCount = episodeVoteCount; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(episodeName);
        parcel.writeString(episodeStillPath);
        parcel.writeString(airDate);
        if (episodeVoteAveraage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(episodeVoteAveraage);
        }
        if (id == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (episodeId == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(episodeId);
        }
        if (episodeNumber == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(episodeNumber);
        }
        if (episodeVoteCount == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeInt(episodeVoteCount);
        }
    }
}
