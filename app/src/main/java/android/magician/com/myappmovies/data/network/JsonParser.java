package android.magician.com.myappmovies.data.network;

import android.magician.com.myappmovies.data.model.Movie;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magician on 2/20/2018.
 */

final class JsonParser {
    private static final String LOG_TAG = JsonParser.class.getSimpleName();
    private static final String MOVIE_RESULTS = "results";
    //inside results:
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_Poster = "poster_path";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_RELEASE_DATE = "release_date";


    /**
     * This method parses JSON from a web response and returns an array of Strings
     *
     * @param jsonStr JSON response from server
     * @return List of Strings describing items data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static List<Movie> parseMovies(@NonNull final String jsonStr) throws JSONException {
        ArrayList<Movie> listMovies = new ArrayList<>();
        JSONObject root = new JSONObject(jsonStr);
        JSONArray resultMovies = root.getJSONArray(MOVIE_RESULTS);
        //Movie[] listMovies = new Movie[resultMovies.length()];

        for (int i = 0; i < resultMovies.length(); i++) {
            // Get the JSON object representing movie
            JSONObject movieJsonObject = resultMovies.getJSONObject(i);
            //get id
            int movieId = movieJsonObject.getInt(MOVIE_ID);
            //get title
            String movieTitle = movieJsonObject.getString(MOVIE_TITLE);
            //get vote average
            String movieVoteAverage = movieJsonObject.getString(MOVIE_VOTE_AVERAGE);
            // get poster
            String moviePoster = movieJsonObject.getString(MOVIE_Poster);
            //get overview
            String movieOverview = movieJsonObject.getString(MOVIE_OVERVIEW);
            // get release date
            String movieReleaseDate = movieJsonObject.getString(MOVIE_RELEASE_DATE);

            Movie movie = new Movie(movieId, movieTitle, movieReleaseDate, moviePoster, movieVoteAverage, movieOverview);
            listMovies.add(movie);
        }

        return  listMovies;
    }

}
