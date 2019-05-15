package com.felix.popmovies;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.felix.popmovies.model.Movie;
import com.squareup.picasso.Picasso;

import static com.felix.popmovies.MainActivity.MOVIE;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mYearTextView;
    private TextView mRatingTextView;
    private TextView mOverviewTextView;
    private FloatingActionButton fab;

    boolean isFavorite = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        Movie movie = intent.getParcelableExtra(MOVIE);

        mImageView = findViewById(R.id.image_view_detail);
        mYearTextView = findViewById(R.id.year_text_view_detail);
        mRatingTextView = findViewById(R.id.rating_text_view_detail);
        mOverviewTextView = findViewById(R.id.overview_text_view_detail);

        Picasso.get().load(movie.getBackDropImageUrl()).fit().centerInside().into(mImageView);

        this.setTitle(movie.getTitle());
        mYearTextView.setText(movie.getReleaseDate() );
        String ratingString = movie.getVoteAverage() + "/10";
        mRatingTextView.setText(ratingString);
        mOverviewTextView.setText(movie.getOverview());

        fab = findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_white_24dp));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white_24dp));
                    isFavorite = false;
                    Snackbar.make(v, "Movie saved as favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_white_24dp));
                    isFavorite = true;
                    Snackbar.make(v, "Remove movie from favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}
