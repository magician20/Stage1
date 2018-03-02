package android.magician.com.myappmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Magician on 2/20/2018.
 */

public class Movie {
    private long id;          //id
    private String movieName;          //movieName or original_title
    private String releaseDate;      //release_date
    private String moviePoster;   //poster_path
    private String voteAverage;  // vote_average
    private String overview;    //overview

    public Movie(long id, String title, String releaseDate, String moviePoster, String voteAverage, String overview) {
        this.id = id;
        this.movieName = title;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public long getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    //for test
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", moviePoster='" + moviePoster + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }

}

