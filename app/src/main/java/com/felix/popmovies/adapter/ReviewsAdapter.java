package com.felix.popmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felix.popmovies.R;
import com.felix.popmovies.model.Review;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {

    private Context mContext;
    private List<Review> reviewList;

    public ReviewsAdapter(Context mContext, List<Review> reviewList) {
        this.mContext = mContext;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_review_item, viewGroup, false);
        return new ReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsHolder reviewsHolder, int i) {
        Review review = reviewList.get(i);

        String author = review.getAuthor();
        String content = review.getContent();

        reviewsHolder.author.setText(author);
        reviewsHolder.content.setText(content);
    }

    @Override
    public int getItemCount() {
        if (null == reviewList) return 0;
        return reviewList.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView content;

        public ReviewsHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
        }
    }
}
