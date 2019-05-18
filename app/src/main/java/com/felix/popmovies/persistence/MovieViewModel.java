package com.felix.popmovies.persistence;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.felix.popmovies.model.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    private LiveData<List<Movie>> favoriteMovies;

    public MovieViewModel(Application application) {
        super(application);
        repository = new MovieRepository(application);
        favoriteMovies = repository.getFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void insert(Movie movie) {
        repository.insert(movie);
    }

    public void delete(Movie movie) {
        repository.delete(movie);
    }
}
