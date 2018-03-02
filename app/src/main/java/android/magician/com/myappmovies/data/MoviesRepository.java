package android.magician.com.myappmovies.data;

import android.arch.lifecycle.LiveData;
import android.magician.com.myappmovies.AppExecutors;
import android.magician.com.myappmovies.data.model.Movie;
import android.magician.com.myappmovies.data.network.NetworkDataSource;
import android.util.Log;

import java.util.List;


/**
 * Handles data operations in Sunshine. Acts as a mediator between {@link }
 * and {@link }
 */
public class MoviesRepository {
    private static final String LOG_TAG = MoviesRepository.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesRepository sInstance;

//    private boolean mInitialized=false;//used to check if service used one time at app live

    private final NetworkDataSource mNetworkDataSource;
    private final AppExecutors mExecutors;
    private LiveData<List<Movie>> mMoviesList;//
       //without db
    private MoviesRepository(NetworkDataSource NetworkDataSource, AppExecutors executors) {
        mNetworkDataSource = NetworkDataSource;
        mExecutors = executors;
        /* get data to insert in DB*/
        mMoviesList = mNetworkDataSource.getCurrentDownloadedMovies();//error here?? should i add observe
        //  observe the data (mMoviesList) change

    }

    public synchronized static MoviesRepository getsInstance
            (NetworkDataSource networkDataSource, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MoviesRepository(networkDataSource, executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

/* ---------------------------------------------------------------------------------------------- */
                                   /* Define When Service is Started  */
/* ---------------------------------------------------------------------------------------------- */
    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     * >>used  with getData() methods(because if data from server mean it will be exhibited for changing)
     */
    private void initializeData() {
        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
//        if(mInitialized)return;
//
//        mInitialized = true;

       ////we can made check here inside the thread to add more control on the service
        mExecutors.diskIO().execute(this::startFetchMenuItemsService);
    }
    /**
     * Network related operation (Start the service that get the data from server)
     */
    private void startFetchMenuItemsService() {
        mNetworkDataSource.startFetchItemsMenuService();
    }

 /* ---------------------------------------------------------------------------------------------- */
                             /* Return Data Methods */
/* ---------------------------------------------------------------------------------------------- */
  //this data not persist in DB
    public LiveData<List<Movie>>  getMoviesList() {
        initializeData();
        return mMoviesList;
    }
}