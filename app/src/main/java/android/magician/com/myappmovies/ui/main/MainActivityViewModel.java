package android.magician.com.myappmovies.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.magician.com.myappmovies.data.MoviesRepository;
import android.magician.com.myappmovies.data.model.Movie;

import java.util.List;

/**
 * Created by Magician on 2/23/2018.
 *  @link MainActivityViewModel   Hold data until Activity is destroyed
 */

public class MainActivityViewModel extends ViewModel {
    private final LiveData<List<Movie>> mMoviesList;// better if i can change Movie to ListMovies for memory leak
    private final MoviesRepository mRepository;

    MainActivityViewModel(MoviesRepository repository) {
        mRepository = repository;
        mMoviesList = mRepository.getMoviesList();
    }

    public LiveData<List<Movie>> getMoviesList() {
        return mMoviesList;
    }

    //here we can cancel Volley request by using Repository
}
