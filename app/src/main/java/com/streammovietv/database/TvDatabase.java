package com.streammovietv.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.streammovietv.model.Tv;

@Database(entities = {Tv.class}, version = 1, exportSchema = false)
public abstract class TvDatabase extends RoomDatabase {

    private static final String TAG = TvDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "tvDatabase";
    private static volatile TvDatabase sInstance;

    public static synchronized TvDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = createDatabase(context);
            }
        }
        return sInstance;
    }

    private static TvDatabase createDatabase(final Context context) {
        return Room.databaseBuilder(
                context,
                TvDatabase.class,
                DB_NAME).build();
    }

    public abstract TvDao tvDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {
    }
}

