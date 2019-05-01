package com.felix.popmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE = "movie";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String KEY_TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";


    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

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

                                double voteAverage = result.getDouble(VOTE_AVERAGE);
                                String title = result.getString(KEY_TITLE);
                                String overView = result.getString(OVERVIEW);
                                String releaseDate = result.getString(RELEASE_DATE);

                                String posterPath = result.getString(POSTER_PATH);
                                String imageUrl = "https://image.tmdb.org/t/p/w185" + posterPath;

                                mMovieList.add(new Movie(voteAverage, title, overView, releaseDate, imageUrl));
                            }

                            mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                            mRecyclerView.setAdapter(mMovieAdapter);
                            mMovieAdapter.notifyDataSetChanged();
                            mMovieAdapter.setOnItemClickListener(MainActivity.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_popular:
                mMovieList.clear();
                parseJSON(MOVIE_DB_BASE_URL + POPULARITY + API_KEY);
                mMovieAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_rating:
                mMovieList.clear();
                parseJSON(MOVIE_DB_BASE_URL + TOP_RATED + API_KEY);
                mMovieAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Movie movie = mMovieList.get(position);

        intent.putExtra(MOVIE, movie);

        startActivity(intent);
    }
}
