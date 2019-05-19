package com.felix.popmovies.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.felix.popmovies.model.Movie;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = MovieRoomDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favorite_movie_table";
    private static MovieRoomDatabase INSTANCE;

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    Log.d(LOG_TAG, "Creating new database instance");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return INSTANCE;
    }

    public abstract MovieDao movieDao();
}