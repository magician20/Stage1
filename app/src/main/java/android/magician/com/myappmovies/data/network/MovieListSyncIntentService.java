package android.magician.com.myappmovies.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.magician.com.myappmovies.AppExecutors;
import android.magician.com.myappmovies.utilities.InjectorUtils;
import android.support.annotation.Nullable;
import android.util.Log;


/*
 * Created by Magician on 2/20/2018.
 * run in the background
 */

/**
 * An {@link IntentService} subclass for immediately scheduling a sync with the server off of the
 * main thread.
 */
public class MovieListSyncIntentService extends IntentService {
    private static final String LOG_TAG = MovieListSyncIntentService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * ItemsMenuSyncIntentService Used to name the worker thread, important only for debugging.
     */
    public MovieListSyncIntentService() {
        super("ItemsMenuSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(LOG_TAG, "Intent service started");

        NetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchMoviesList();
    }
}
