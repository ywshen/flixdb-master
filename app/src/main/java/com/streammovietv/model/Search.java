package com.streammovietv.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @ColumnInfo(name = "genre_name")
    @SerializedName("genre_name")
    private List<String> genreName;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    private boolean adult;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    private String firstAirDate;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String originalTitle;
    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    private String originalName;
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
    @ColumnInfo(name = "number_of_episodes")
    @SerializedName("number_of_episodes")
    private Integer numberOfEpisodes;
    @ColumnInfo(name = "number_of_seasons")
    @SerializedName("number_of_seasons")
    private Integer numberOfSeasons;
    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    private Integer runtime;

    public Search(String posterPath, boolean adult, String overview, String firstAirDate, String releaseDate, String title, String name, String originalTitle, String originalName,
                  String originalLanguage, String backdropPath, List<String> genre_name, Double popularity, Double voteAverage, Integer id, Integer voteCount, boolean video, Integer numberOfEpisodes, Integer numberOfSeasons, Integer runtime){
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.releaseDate = releaseDate;
        this.title = title;
        this.name = name;
        this.backdropPath = backdropPath;
        this.genreName = genre_name;
        this.popularity = popularity;
        this.originalTitle = originalTitle;
        this.originalName = originalName;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
        this.id = id;
        this.voteCount = voteCount;
        this.video = video;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.runtime = runtime;
    }

    private Search(Parcel in){
        posterPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        firstAirDate = in.readString();
        releaseDate = in.readString();
        originalTitle = in.readString();
        originalName = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        in.readList(genreName, Search.class.getClassLoader());
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
        if (in.readByte() == 0){
            numberOfEpisodes = null;
        } else {
            numberOfEpisodes = in.readInt();
        }
        if (in.readByte() == 0){
            numberOfSeasons = null;
        } else {
            numberOfSeasons = in.readInt();
        }
        if (in.readByte() == 0){
            runtime = null;
        } else {
            runtime = in.readInt();
        }
    }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public boolean getAdult() { return adult; }

    public void setAdult(boolean adult) { this.adult = adult; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getFirstAirDate() { return firstAirDate; }

    public void setFirstAirDate(String firstAirDate) { this.firstAirDate = firstAirDate; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getOriginalTitle() { return originalTitle; }

    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }

    public String getOriginalName() { return originalName; }

    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public String getOriginalLanguage() { return originalLanguage; }

    public void setOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<String> getGenreName() { return genreName; }

    public void setGenreName(List<String> genreName) { this.genreName = genreName;}

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

    public Integer getNumberOfEpisodes() { return numberOfEpisodes; }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) { this.numberOfEpisodes = numberOfEpisodes; }

    public Integer getNumberOfSeasons() { return numberOfSeasons; }

    public void setNumberOfSeasons(Integer numberOfSeasons) { this.numberOfSeasons = numberOfSeasons; }

    public Integer getRuntime() { return runtime; }

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
        parcel.writeString(firstAirDate);
        parcel.writeString(releaseDate);
        parcel.writeString(originalTitle);
        parcel.writeString(originalName);
        parcel.writeString(originalLanguage);
        parcel.writeString(title);
        parcel.writeString(name);
        parcel.writeString(backdropPath);
        parcel.writeList(genreName);
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
        if (numberOfEpisodes == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfEpisodes);
        }
        if (numberOfSeasons == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfSeasons);
        }
        if (runtime == null){
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(runtime);
        }
    }
}
