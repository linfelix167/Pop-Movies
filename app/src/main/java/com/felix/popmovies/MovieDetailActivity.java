package com.felix.popmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.felix.popmovies.model.Movie;
import com.squareup.picasso.Picasso;

import static com.felix.popmovies.MainActivity.MOVIE;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mYearTextView;
    private TextView mRatingTextView;
    private TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();

        Movie movie = intent.getParcelableExtra(MOVIE);

        mTitleTextView = findViewById(R.id.title_text_view_detail);
        mImageView = findViewById(R.id.image_view_detail);
        mYearTextView = findViewById(R.id.year_text_view_detail);
        mRatingTextView = findViewById(R.id.rating_text_view_detail);
        mOverviewTextView = findViewById(R.id.overview_text_view_detail);

        Picasso.get().load(movie.getImageUrl()).fit().centerInside().into(mImageView);
        mTitleTextView.setText(movie.getTitle());
        mYearTextView.setText(movie.getReleaseDate() );
        String ratingString = movie.getVoteAverage() + "/10";
        mRatingTextView.setText(ratingString);
        mOverviewTextView.setText(movie.getOverview());
    }
}
