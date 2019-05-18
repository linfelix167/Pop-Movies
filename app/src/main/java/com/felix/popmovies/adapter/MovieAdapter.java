package com.felix.popmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.felix.popmovies.R;
import com.felix.popmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MovieAdapter(Context mContext, List<Movie> mMovieList) {
        this.mContext = mContext;
        this.mMovieList = mMovieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie movie = mMovieList.get(i);

        String imageUrl = movie.getImageUrl();
        String title = movie.getTitle();

        movieViewHolder.titleTextView.setText(title);
        Picasso.get().load(imageUrl).fit().centerInside().into(movieViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieList) return 0;
        return mMovieList.size();
    }

    public void setFavoriteMovies(List<Movie> movies) {
        mMovieList = movies;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
            this.titleTextView = itemView.findViewById(R.id.title_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

