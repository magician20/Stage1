package android.magician.com.myappmovies.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.magician.com.myappmovies.data.MoviesRepository;
import android.support.annotation.NonNull;


/**
 * Created by magic on 1/25/2018.
 * this factory is used to create ViewModel
 *
 * @link MainActivityViewModel and connect it with repository by passing the object
 * @link MoviesRepository
 */

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MoviesRepository mRepository;

    public MainViewModelFactory(MoviesRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
