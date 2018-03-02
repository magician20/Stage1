package android.magician.com.myappmovies.data.model;


import java.util.List;

/**
 * Created by Magician on 1/30/2018.
 * Hold items come from json object
 */

public class MoviesResponse{
    private final List<Movie> mMovies;

    public MoviesResponse(final List<Movie> items) {
        mMovies = items;

    }

    //error here data null
    public List<Movie> getItems() {//i'll add List<ListMovies>
        return mMovies;
    }

}
