package android.magician.com.myappmovies.utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Magician on 2/20/2018.
 * this contain all constants I need to use inside network.
 */

public class NetworkInfos {
    //url example
    public static final String EXAMPLE_STRING_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=37d25f9c8d43a71ca3d93a7d16e4700c";

    public static final String AUTHORITY_WITH_SCHEME = "https://api.themoviedb.org";
    public static final String AUTHORITY = "api.themoviedb.org";
    public static final String BASIC_ADD_DISCOVER = "3/discover/movie";//this worked with path() method
    //tis used for appendPath() method
    public static final String ADD_3 = "3";
    public static final String ADD_DISCOVER = "discover";
    public static final String ADD_MOVIE = "movie";

    /* Basic component of url */
    // chose page not used here ,each page return 20 movie if i want reload more,
    // add  swipe refresh layout and request new page
    public static final String MOVIE_PAGE = "page";

    //sorted type(sort_by=)
    public static final String MOVIE_SORTED_BY = "sort_by";
    //type of sort
    public static final String MOVIE_POPULARITY_DESC_SORT = "popularity.desc";
    public static final String MOVIE_POPULARITY_ASC_SORT = "popularity.asc";

    public static final String MOVIE_vote_DESC_SORT = "vote_average.desc";
    public static final String MOVIE_vote_ASC_SORT = "vote_average.asc";

    public static final String MOVIE_RELEASE_DATE_DESC_SORT = "primary_release_date.desc";
    public static final String MOVIE_RELEASE_DATE_ASC_SORT = "primary_release_date.asc";

    // add on url Key and the Value of my key
    public static final String MOVIE_KEY = "api_key";
    /* The values of key */
    public static final String MY_KEY = "37d25f9c8d43a71ca3d93a7d16e4700c";

    /***********************************************************************************************/
    //default url sort type
    public static String buildUrlDefaultSort() {
        Uri.Builder builder = Uri.parse(AUTHORITY_WITH_SCHEME).buildUpon();
        builder.appendPath(ADD_3)
                .appendPath(ADD_DISCOVER)
                .appendPath(ADD_MOVIE)
                .appendQueryParameter(MOVIE_SORTED_BY, MOVIE_vote_DESC_SORT)
                .appendQueryParameter(MOVIE_KEY, MY_KEY);

        return builder.toString();
    }

    //take type at running time from setting
    public static String buildUrlWithSortType(String sortType) {
        Uri.Builder builder = Uri.parse(AUTHORITY_WITH_SCHEME).buildUpon();
        builder.appendPath(ADD_3)
                .appendPath(ADD_DISCOVER)
                .appendPath(ADD_MOVIE)
                .appendQueryParameter(MOVIE_SORTED_BY, sortType)
                .appendQueryParameter(MOVIE_KEY, MY_KEY);

        return builder.toString();
    }

    //with page ,this for swipe refresh layout if i want to reload more data
    public static String buildUrlWithSortAndPage(int page, String sortType) {//without define page
        Uri.Builder builder = Uri.parse(AUTHORITY_WITH_SCHEME).buildUpon();
        builder.appendPath(ADD_3)
                .appendPath(ADD_DISCOVER)
                .appendPath(ADD_MOVIE)
                .appendQueryParameter(MOVIE_PAGE, String.valueOf(page))
                .appendQueryParameter(MOVIE_SORTED_BY, sortType)
                .appendQueryParameter(MOVIE_KEY, MY_KEY);

        return builder.toString();
    }

    /***********************************************************************************************/
    //Base url of image
    //example of URL:https://image.tmdb.org/t/p/w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
    public static final String BASE_IMAGE_URL_WITH_SCHEME = "http://image.tmdb.org";
    public static final String SECURE_BASE_IMAGE_URL_WITH_SCHEME = "https://image.tmdb.org";
    public static final String BASE_IMAGE_URL = "image.tmdb.org";
    public static final String EXTRA_URL_Path_T = "t";
    public static final String EXTRA_URL_Path_P = "p";
    //size of image
    //"poster_sizes" : w92,w154,w185,w342,w500 , w780,original, "backdrop_sizes":w300,w780,w1280,original
    public static final String POSTER_SIZE = "w500";
    public static final String BACKDROP_SIZE = "w780";

    // used to get the URL String of Image for each one's
    public static String getImageUrlString(String filePath) {
        Uri.Builder builder = Uri.parse(SECURE_BASE_IMAGE_URL_WITH_SCHEME).buildUpon();
        builder.appendPath(EXTRA_URL_Path_T)
                .appendPath(EXTRA_URL_Path_P)
                .appendPath(POSTER_SIZE)
                .appendPath(filePath);
        return builder.build().toString();
    }

    /***********************************************************************************************/
    //method to determine whether the device has internet connection
    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
