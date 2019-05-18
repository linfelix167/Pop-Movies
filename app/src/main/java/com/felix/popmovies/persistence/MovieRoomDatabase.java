package com.felix.popmovies.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.felix.popmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieRoomDatabase INSTANCE;

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "favorite_movie_table")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}