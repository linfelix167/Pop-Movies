package com.felix.popmovies.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.felix.popmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Query("SELECT * FROM favorite_movie_table")
    LiveData<List<Movie>> getFavoriteMovies();

    @Delete
    void deleteMovie(Movie movie);
}
