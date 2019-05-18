package com.felix.popmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite_movie_table")
public class Movie implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "back_drop_image_url")
    private String backDropImageUrl;

    private boolean isFavorite;

    public Movie(@NonNull int id, double voteAverage, String title, String overview, String releaseDate, String imageUrl, String backDropImageUrl) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
        this.backDropImageUrl = backDropImageUrl;
    }


    protected Movie(Parcel in) {
        id = in.readInt();
        voteAverage = in.readDouble();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        imageUrl = in.readString();
        backDropImageUrl = in.readString();
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

    public int getId() {
        return id;
    }

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

    public String getBackDropImageUrl() {
        return backDropImageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(imageUrl);
        dest.writeString(backDropImageUrl);
    }
}
