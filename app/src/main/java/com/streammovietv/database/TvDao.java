package com.streammovietv.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.streammovietv.model.Tv;

import java.util.List;

@Dao
public interface TvDao {
    @Query("SELECT * FROM tvshows")
    LiveData<List<Tv>> getAllTvs();

    @Query("SELECT * FROM tvshows WHERE id = :id")
    LiveData<Tv> getTv(int id);

    @Insert
    void insertTv(Tv tv);

    @Update
    void updateTv(Tv tv);

    @Delete
    void deleteTv(Tv tv);
}
