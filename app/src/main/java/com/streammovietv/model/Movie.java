package com.streammovietv.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movies")
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @PrimaryKey
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
    @Ignore
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @ColumnInfo(name = "genre_name")
    @SerializedName("genre_name")
    private List<String> genreName;
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
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private Integer voteCount;
    @ColumnInfo(name = "video")
    @SerializedName("video")
    private Boolean video;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double voteAverage;
    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    private Integer runtime;

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, Integer id, List<String> genreName,
                 String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity,
                 Integer voteCount, Boolean video, Double voteAverage, Integer runtime) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.genreName = genreName;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.runtime = runtime;
    }

    @Ignore
    public Movie(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, List<String> genreName,
                 String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity,
                 Integer voteCount, Boolean video, Double voteAverage, Integer runtime) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.genreName = genreName;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.runtime = runtime;
    }

    public Movie() {
        this.posterPath = null;
        this.adult = false;
        this.overview = null;
        this.releaseDate = null;
        this.genreIds = null;
        this.id = null;
        this.originalTitle = null;
        this.originalLanguage = null;
        this.title = null;
        this.backdropPath = null;
        this.popularity = null;
        this.voteCount = null;
        this.video = null;
        this.voteAverage = null;
        this.runtime = null;
    }

    // Tell Room to ignore this constructor
    @Ignore
    private Movie(Parcel in) {
        posterPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        in.readList(genreName, Movie.class.getClassLoader());
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
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
            runtime = null;
        } else {
            runtime = in.readInt();
        }
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<String> getGenreName() { return genreName; }

    public void setGenreName (List<String> genreName) { this.genreName = genreName; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getRuntime() { return runtime;}

    public void setRuntime(Integer runtime) { this.runtime = runtime; }

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
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeList(genreName);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(title);
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
        if (runtime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(runtime);
        }
    }
}