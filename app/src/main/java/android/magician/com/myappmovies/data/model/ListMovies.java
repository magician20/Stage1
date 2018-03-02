package android.magician.com.myappmovies.data.model;

/**
 * Created by Magician on 2/23/2018.
 *
 * this class used to minimize the memory taken by recycle item depend on here layout {movie_card} .
 */

public class ListMovies {
    private long id;
    private String moviePoster;
    private String voteAverage;


    public ListMovies(long id, String moviePoster, String voteAverage) {
        this.id = id;
        this.moviePoster = moviePoster;
        this.voteAverage = voteAverage;
    }

    public long getId() {
        return id;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getVoteAverage() {
        return voteAverage;
    }


}
