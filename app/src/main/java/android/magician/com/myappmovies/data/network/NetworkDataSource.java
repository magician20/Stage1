package android.magician.com.myappmovies.data.network;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.magician.com.myappmovies.AppExecutors;
import android.magician.com.myappmovies.data.model.Movie;
import android.magician.com.myappmovies.data.preference.AppPreferencesHelper;
import android.magician.com.myappmovies.utilities.NetworkInfos;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Magician on 2/20/2018.
 */

public class NetworkDataSource {
    private static final String LOG_TAG = NetworkDataSource.class.getSimpleName();

    //created to store data hold inside LiveData
    private MutableLiveData<List<Movie>> mListMovies;
    private final Context mContext;
    private final AppPreferencesHelper mAppPreferencesHelper;//
    private final AppExecutors mExecutors;

    private NetworkDataSource(Context context, AppExecutors executors,AppPreferencesHelper appPreferencesHelper) {
        mContext = context;
        mExecutors = executors;
        mAppPreferencesHelper = appPreferencesHelper;//
        mListMovies = new MutableLiveData<>();
    }

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile NetworkDataSource sInstance;

    public static NetworkDataSource getsInstance(Context context, AppExecutors executors,AppPreferencesHelper appPreferencesHelper) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkDataSource(context.getApplicationContext(), executors,appPreferencesHelper);//
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }
/* ---------------------------------------------------------------------------------------------- */
                                    /* get Data from holder*/
/* ---------------------------------------------------------------------------------------------- */

    /**
     * @return List<ItemEntry> that we need to show it(remember it will be observed).
     **/
    public MutableLiveData<List<Movie>> getCurrentDownloadedMovies() {//error here data not passed
        return mListMovies;
    }

/* ---------------------------------------------------------------------------------------------- */
                                 /* Start Service */
/* ---------------------------------------------------------------------------------------------- */

    /**
     * Starts an intent service to fetch the weather.
     */
    public void startFetchItemsMenuService() {
        Intent intentToFetch = new Intent(mContext, MovieListSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.i(LOG_TAG, "Service created");
    }

    /* get data from server (this Method run inside the server) */
    void fetchMoviesList() {
        Log.d(LOG_TAG, "Fetch Menu started");
        //old way
        oldWayPrepareMovies();
    }

/* ---------------------------------------------------------------------------------------------- */
                                  /* HttpURLConnection Way to get data from NetWork code */
/* ---------------------------------------------------------------------------------------------- */
    private void oldWayPrepareMovies() {
            try {
                Log.d(LOG_TAG, "string url: "+NetworkUtils.CreateURL(mAppPreferencesHelper.getSortValue()));//test
                //getUrl method will return the sURL that we need to get the data
                // Use the URL to retrieve the JSON      //removed > NetworkInfos.buildUrlDefaultSort()
                String jsonStringResponse =
                        NetworkUtils.getResponseFromHttpUrl(NetworkUtils.CreateURL(mAppPreferencesHelper.getSortValue()));
                if (jsonStringResponse == null) {
                    Log.d(LOG_TAG, "JSON String is Null");
                    return;
                }
                // Parse the JSON into a list of weather forecasts
                List<Movie> response = JsonParser.parseMovies(jsonStringResponse);
                Log.d(LOG_TAG, "JSON Parsing finished");

                if (response.size() != 0) {
                    //check code
                    Log.d(LOG_TAG, "JSON not null and has " + response.size() + " values");
                    Log.d(LOG_TAG, "First value" + response.toString());

                    // Will eventually do something with the downloaded data
                    mListMovies.postValue(response);//
                }

            } catch (IOException e) {
                // Server probably invalid
                Log.e(LOG_TAG, "Error IOException");
            } catch (JSONException e) {
                //mean error in fetching jsonString
                Log.e(LOG_TAG, "Error JSONException");
            }

    }
/* ---------------------------------------------------------------------------------------------- */

}
