package com.felix.popmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.felix.popmovies.R;
import com.felix.popmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.felix.popmovies.utilities.Constant.YOUTUBE_TUMBNAIL_URL;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private Context context;
    private List<Trailer> trailerList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TrailersAdapter(Context context, List<Trailer> trailerList) {
        this.context = context;
        this.trailerList = trailerList;
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_trailer_item, viewGroup, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder trailersViewHolder, int i) {
        Trailer trailer = trailerList.get(i);
        String imagePath = YOUTUBE_TUMBNAIL_URL + trailer.getKey() + "/0.jpg";
        Picasso.get().load(imagePath).into(trailersViewHolder.trailerImage);
    }

    @Override
    public int getItemCount() {
        if (null == trailerList) return 0;
        return trailerList.size();
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder {

        ImageView trailerImage;

        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            this.trailerImage = itemView.findViewById(R.id.trailerImage);

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
