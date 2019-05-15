package com.felix.popmovies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.felix.popmovies.adapter.MovieAdapter;
import com.felix.popmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.felix.popmovies.utilities.Constant.API_KEY;
import static com.felix.popmovies.utilities.Constant.MOVIE_DB_BASE_URL;
import static com.felix.popmovies.utilities.Constant.POPULARITY;
import static com.felix.popmovies.utilities.Constant.TOP_RATED;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {

    public static final String MOVIE = "movie";
    public static final String ID = "id";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String KEY_TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";
    public static final String BACKDROP_PATH = "backdrop_path";


    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);

        mProgressBar = findViewById(R.id.pogressBar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMovieList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(MOVIE_DB_BASE_URL + POPULARITY + API_KEY);
    }

    private void parseJSON(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);

                                int id = result.getInt(ID);
                                double voteAverage = result.getDouble(VOTE_AVERAGE);
                                String title = result.getString(KEY_TITLE);
                                String overView = result.getString(OVERVIEW);
                                String releaseDate = result.getString(RELEASE_DATE);

                                String posterPath = result.getString(POSTER_PATH);
                                String imageUrl = "https://image.tmdb.org/t/p/w185" + posterPath;
                                String backdropPath = result.getString(BACKDROP_PATH);
                                String backdropImageUrl = "https://image.tmdb.org/t/p/w500" + backdropPath;

                                mMovieList.add(new Movie(id, voteAverage, title, overView, releaseDate, imageUrl, backdropImageUrl));
                            }

                            mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                            mRecyclerView.setAdapter(mMovieAdapter);
                            mMovieAdapter.notifyDataSetChanged();
                            mMovieAdapter.setOnItemClickListener(MainActivity.this);
                            mProgressBar.setVisibility(View.INVISIBLE);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_popular:
                    mMovieList.clear();
                    mProgressBar.setVisibility(View.VISIBLE);
                    parseJSON(MOVIE_DB_BASE_URL + POPULARITY + API_KEY);
                    mMovieAdapter.notifyDataSetChanged();
                    return true;
                case R.id.navigation_rating:
                    mMovieList.clear();
                    mProgressBar.setVisibility(View.VISIBLE);
                    parseJSON(MOVIE_DB_BASE_URL + TOP_RATED + API_KEY);
                    mMovieAdapter.notifyDataSetChanged();
                    return true;
                case R.id.navigation_favorite:
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Movie movie = mMovieList.get(position);

        intent.putExtra(MOVIE, movie);

        startActivity(intent);
    }
}
