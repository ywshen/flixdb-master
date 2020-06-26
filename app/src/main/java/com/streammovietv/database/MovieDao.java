package com.streammovietv.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.streammovietv.model.Movie;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> getMovie(int id);

    @Insert
    void insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}

