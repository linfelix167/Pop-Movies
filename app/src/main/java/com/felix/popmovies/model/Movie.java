package com.felix.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private double voteAverage;
    private String title;
    private String overview;
    private String releaseDate;
    private String imageUrl;

    public Movie(double voteAverage, String title, String overview, String releaseDate, String imageUrl) {
        this.voteAverage = voteAverage;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
    }

    protected Movie(Parcel in) {
        voteAverage = in.readDouble();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(imageUrl);
    }
}
