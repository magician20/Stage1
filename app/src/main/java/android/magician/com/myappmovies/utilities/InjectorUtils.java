package android.magician.com.myappmovies.utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.magician.com.myappmovies.AppExecutors;
import android.magician.com.myappmovies.data.MoviesRepository;
import android.magician.com.myappmovies.data.network.NetworkDataSource;
import android.magician.com.myappmovies.data.preference.AppPreferencesHelper;
import android.magician.com.myappmovies.ui.main.MainViewModelFactory;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Magician on 2/24/2018.
 * Injection
 */

public class InjectorUtils {
    private static final String LOG_TAG = InjectorUtils.class.getSimpleName();

    public static MoviesRepository provideRepository(Context context) {
        Log.d(LOG_TAG, "provideRepository");
      //Db if exist add here
        AppExecutors executors = AppExecutors.getInstance();
        AppPreferencesHelper appPreferencesHelper = AppPreferencesHelper.getsInstance(context, executors);
        NetworkDataSource networkDataSource =
                NetworkDataSource.getsInstance(context.getApplicationContext(), executors,appPreferencesHelper);//

        return MoviesRepository.getsInstance(networkDataSource, executors);
    }

    public static NetworkDataSource provideNetworkDataSource(Context context) {
        Log.d(LOG_TAG, "provideNetworkDataSource");
        AppExecutors executors = AppExecutors.getInstance();
        AppPreferencesHelper appPreferencesHelper = providePreferencesData(context);//
        return NetworkDataSource.getsInstance(context.getApplicationContext(), executors,appPreferencesHelper);//
    }

//        // Get all of the values from shared preferences to set it up// should i pass SharedPreferences rather than context
//       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    public static AppPreferencesHelper providePreferencesData(Context context) {//
        Log.d(LOG_TAG, "AppPreferencesHelper");
        AppExecutors executors = AppExecutors.getInstance();
        return AppPreferencesHelper.getsInstance(context, executors);
    }


    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        Log.d(LOG_TAG, "provideMainActivityViewModelFactory");
        MoviesRepository repository = provideRepository(context);
        return new MainViewModelFactory(repository);
    }

}
