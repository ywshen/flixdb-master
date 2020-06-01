package com.streammovietv.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "search")
public class Search implements Parcelable{
    public static final Parcelable.Creator<Search> CREATOR = new Parcelable.Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    private boolean adult;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String originalTitle;
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String originalLanguage;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private Double popularity;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private Integer voteCount;
    @ColumnInfo(name = "video")
    @SerializedName("video")
    private Boolean video;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double voteAverage;

    public Search(String posterPath, boolean adult, String overview, String releaseDate, String title, String name, String originalTitle, String originalLanguage,
                  String backdropPath, Double popularity, Double voteAverage, Integer id, Integer voteCount, boolean video){
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.name = name;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
        this.id = id;
        this.voteCount = voteCount;
        this.video = video;
    }

    private Search(Parcel in){
        posterPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        name = in.readString();
        if (in.readByte() == 0){
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
        if (in.readByte() == 0){
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        if (in.readByte() == 0){
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
    }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public boolean getAdult() { return adult; }

    public void setAdult(boolean adult) { this.adult = adult; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getOriginalTitle() { return originalTitle; }

    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }

    public String getOriginalLanguage() { return originalLanguage; }

    public void setOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBackdropPath() { return backdropPath; }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public Double getPopularity() { return popularity; }

    public void setPopularity(Double popularity) { this.popularity = popularity; }

    public Double getVoteAverage() { return voteAverage; }

    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getVoteCount() { return voteCount; }

    public void setVoteCount(Integer voteCount) { this.voteCount = voteCount; }

    public boolean getVideo() { return video; }

    public void setVideo(boolean video) { this.video = video; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(title);
        parcel.writeString(name);
        parcel.writeString(backdropPath);
        if (voteAverage == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        if (id == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (voteCount == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
    }
}
