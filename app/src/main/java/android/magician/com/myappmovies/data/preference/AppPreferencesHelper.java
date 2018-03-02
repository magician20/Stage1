package android.magician.com.myappmovies.data.preference;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.magician.com.myappmovies.AppExecutors;
import android.magician.com.myappmovies.R;
import android.magician.com.myappmovies.utilities.NetworkInfos;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by magic on 2/25/2018.
 * Only read happen here
 */

public class AppPreferencesHelper implements PreferencesHelper {
    private static final String LOG_TAG = AppPreferencesHelper.class.getSimpleName();

    private final Context mContext;
    private final Object mExecutors;
    private final SharedPreferences mSharedPreferences;

    // Get all of the values from shared preferences to set it up
    // using the default
    private AppPreferencesHelper(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        // Get all of the values from shared preferences to set it up
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    // For Singleton instantiation    used here because i only have 1 data preference (default)
    private static final Object LOCK = new Object();
    private static volatile AppPreferencesHelper sInstance;

    public static AppPreferencesHelper getsInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppPreferencesHelper(context, executors);
                Log.d(LOG_TAG, "Made new AppPreferencesHelper ");
            }
        }
        return sInstance;
    }

    @Override
    public String getSortValue() {//should i made this run by diskIO thread ?
        // pass the return data to Create the URl before start the connection
        String sortValue = mSharedPreferences.getString(mContext.getString(R.string.pref_sort_key)
                , mContext.getString(R.string.pref_sort_default_value));

        String sortType;//default value
        switch (sortValue) {
            case "popularityAsc":
                sortType = NetworkInfos.MOVIE_POPULARITY_ASC_SORT;// here show same as desc idk why until now
                break;
            case "popularityDesc":
                sortType = NetworkInfos.MOVIE_POPULARITY_DESC_SORT;//
                break;
            case "voteAsc":
                sortType = NetworkInfos.MOVIE_vote_ASC_SORT;
                break;
            case "voteDec":
                sortType = NetworkInfos.MOVIE_vote_DESC_SORT;
                break;
            case "dateAsc":
                sortType = NetworkInfos.MOVIE_RELEASE_DATE_ASC_SORT;
                break;
            case "dateDec":
                sortType = NetworkInfos.MOVIE_RELEASE_DATE_DESC_SORT;
                break;
            default:
                sortType = NetworkInfos.MOVIE_POPULARITY_DESC_SORT;//default value
        }

        return NetworkInfos.buildUrlWithSortType(sortType);
    }
}