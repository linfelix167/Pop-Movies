package com.felix.popmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.felix.popmovies.adapter.ReviewsAdapter;
import com.felix.popmovies.adapter.TrailersAdapter;
import com.felix.popmovies.model.Movie;
import com.felix.popmovies.model.Review;
import com.felix.popmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.felix.popmovies.MainActivity.MOVIE;
import static com.felix.popmovies.utilities.Constant.API_KEY;
import static com.felix.popmovies.utilities.Constant.MOVIE_DB_BASE_URL;
import static com.felix.popmovies.utilities.Constant.YOUTUBE_TUMBNAIL_URL;
import static com.felix.popmovies.utilities.Constant.YOUTUBE_URL;

public class MovieDetailActivity extends AppCompatActivity implements TrailersAdapter.OnItemClickListener {

    public static final String REVIEWS = "reviews";
    public static final String VIDEOS = "videos";
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";
    public static final String ID = "id";
    public static final String KEY = "key";
    public static final String NAME = "name";
    public static final String SITE = "site";
    public static final String TYPE = "type";

    private ImageView mImageView;
    private TextView mYearTextView;
    private TextView mRatingTextView;
    private TextView mOverviewTextView;
    private CheckBox mFavoriteCheckBox;
    private FloatingActionButton fab;
    private RecyclerView recyclerViewReviews;
    private RecyclerView recyclerViewTrailers;
    private ReviewsAdapter reviewsAdapter;
    private TrailersAdapter trailersAdapter;
    private ArrayList<Review> reviews;
    private ArrayList<Trailer> trailers;
    private RequestQueue mRequestQueue;

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
        mFavoriteCheckBox = findViewById(R.id.favorite_checkbox);

        Picasso.get().load(movie.getBackDropImageUrl()).fit().centerInside().into(mImageView);

        this.setTitle(movie.getTitle());
        mYearTextView.setText(movie.getReleaseDate() );
        String ratingString = movie.getVoteAverage() + "/10";
        mRatingTextView.setText(ratingString);
        mOverviewTextView.setText(movie.getOverview());

        recyclerViewReviews = findViewById(R.id.recycler_view_reviews);
        recyclerViewReviews.setHasFixedSize(true);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewTrailers = findViewById(R.id.recycler_view_trailers);
        recyclerViewTrailers.setHasFixedSize(true);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        reviews = new ArrayList<>();
        trailers = new ArrayList<>();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = createShareMovieIntent();
                startActivity(shareIntent);
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);

        String reviewsUrl = MOVIE_DB_BASE_URL + movie.getId() + "/" + REVIEWS + "?" + API_KEY;
        String trailersUrl = MOVIE_DB_BASE_URL + movie.getId() + "/" + VIDEOS + "?" + API_KEY;
        parseReviewsJSON(reviewsUrl);
        parseTrailersJSON(trailersUrl);
    }

    private void parseReviewsJSON(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);

                                String author = result.getString(AUTHOR);
                                String content = result.getString(CONTENT);

                                reviews.add(new Review(author, content));
                            }

                            reviewsAdapter = new ReviewsAdapter(MovieDetailActivity.this, reviews);
                            recyclerViewReviews.setAdapter(reviewsAdapter);
                            reviewsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    private void parseTrailersJSON(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);

                                String id = result.getString(ID);
                                String key = result.getString(KEY);
                                String name = result.getString(NAME);
                                String site = result.getString(SITE);
                                String type = result.getString(TYPE);

                                trailers.add(new Trailer(id, key, name, site, type));
                            }

                            trailersAdapter = new TrailersAdapter(MovieDetailActivity.this, trailers);
                            recyclerViewTrailers.setAdapter(trailersAdapter);
                            trailersAdapter.setOnItemClickListener(MovieDetailActivity.this);
                            trailersAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        Trailer trailer = trailers.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://"+ trailer.getKey()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(YOUTUBE_URL + trailer.getKey()));
            if (webIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webIntent);
            } else {
                Toast.makeText(this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Intent createShareMovieIntent() {
        Trailer trailer = trailers.get(0);
        String shareMessage = "Hey! I have a new movie trailer want ot share with you!\n"
                + "https://www.youtube.com/watch?v=" + trailer.getKey();
        Intent shareIntent =ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(shareMessage)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }
}
