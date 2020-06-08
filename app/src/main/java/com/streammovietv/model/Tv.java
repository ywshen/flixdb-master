package com.streammovietv.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tvshows")
public class Tv implements Parcelable {
    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel in) {
            return new Tv(in);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
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
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    private String firstAirDate;
    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    private String originalName;
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String originalLanguage;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private Double popularity;
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private Integer voteCount;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double voteAverage;
    @ColumnInfo(name = "video")
    @SerializedName("video")
    private Boolean video;
    @ColumnInfo(name = "number_of_episodes")
    @SerializedName("number_of_episodes")
    private Integer numberOfEpisodes;
    @ColumnInfo(name = "number_of_seasons")
    @SerializedName("number_of_seasons")
    private Integer numberOfSeasons;

    public Tv(String posterPath, String overview, String firstAirDate, Integer id,
              String originalName, String originalLanguage, String name, String backdropPath, Double popularity,
              Integer voteCount, Double voteAverage, Integer numberOfSeasons, Integer numberOfEpisodes) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.id = id;
        this.originalName = originalName;
        this.originalLanguage = originalLanguage;
        this.name = name;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        //this.numberOfSeasons = numberOfSeasons;
        //this.numberOfEpisodes = numberOfEpisodes;
    }

    private Tv(Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        firstAirDate = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        originalName = in.readString();
        originalLanguage = in.readString();
        name = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            numberOfSeasons = null;
        } else {
            numberOfSeasons = in.readInt();
        }
        /*if (in.readByte() == 0) {
            numberOfEpisodes = null;
        } else {
            numberOfEpisodes = in.readInt();
        }*/
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(firstAirDate);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(originalName);
        parcel.writeString(originalLanguage);
        parcel.writeString(name);
        parcel.writeString(backdropPath);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        if (voteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
        parcel.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
       /* if (numberOfSeasons == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfSeasons);
        }
        if (numberOfEpisodes == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfEpisodes);
        }*/
    }
}
