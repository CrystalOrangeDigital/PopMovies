package com.corangedigital.popmovies;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ebal on 26/07/15.
 */
public class TMDBMovie implements Parcelable {

    //Member variables
    public String movieID;
    public String title;
    public String thumbnailPath;
    public String releaseDate;
    public Image poster;
    public String BaseUrl = "http://image.tmdb.org/t/p/w185";
    public String voteAverage;
    public String overview;

    // Parcelable interface
    public static final Creator CREATOR = new Creator() {
        public TMDBMovie createFromParcel(Parcel in) {
            return new TMDBMovie(in);
        }

        public TMDBMovie[] newArray(int size) {
            return new TMDBMovie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(movieID);
        dest.writeString(title);
        dest.writeString(thumbnailPath);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(overview);

    }

    // Constructors
    public TMDBMovie(String title, String thumbnailPath, Image poster) {
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.poster = poster;
    }

    public TMDBMovie(String thumbnailPath) {
        title = null;
        this.thumbnailPath = "http://image.tmdb.org/t/p/w185" + thumbnailPath;
        poster = null;
    }

    public TMDBMovie() {
        title = null;
        thumbnailPath = null;
    }

    public TMDBMovie(String[] movieInfo) {
        this.title = movieInfo[0];
        this.thumbnailPath = movieInfo[1];
        this.releaseDate = movieInfo[2];
        this.voteAverage = movieInfo[3];
        this.overview = movieInfo[4];
    }

    public TMDBMovie(Parcel in) {

//        this.movieID = in.readString();
        this.title = in.readString();
        this.thumbnailPath = BaseUrl + in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readString();
        this.overview = in.readString();
    }

    // Getters and Setter
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getThumbnailPath() {
        return BaseUrl + thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }


}
