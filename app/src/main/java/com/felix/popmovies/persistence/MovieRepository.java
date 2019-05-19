package com.felix.popmovies.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.felix.popmovies.model.Movie;

import java.util.List;

public class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> favoriteMovies;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        movieDao = db.movieDao();
        favoriteMovies = movieDao.getFavoriteMovies();
    }

    LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void insert(Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    public void delete(Movie movie) {
        new deleteAsyncTask(movieDao).execute(movie);
    }

    public void check(Movie movie) {
        new checkMovieAsyncTask(movieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao asyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {
            asyncTaskDao.insert(movies[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao asyncTaskDao;

        deleteAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {
            asyncTaskDao.deleteMovie(movies[0]);
            return null;
        }
    }

    private static class checkMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao asyncTaskDao;

        checkMovieAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.checkMovie(movies[0].getId());
            return null;
        }
    }
}
