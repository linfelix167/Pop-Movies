package com.felix.popmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.felix.popmovies.persistence.MovieViewModel;

public class FavoriteActivity extends AppCompatActivity {

    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

    }
}
