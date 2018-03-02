package android.magician.com.myappmovies.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.magician.com.myappmovies.R;
import android.magician.com.myappmovies.utilities.InjectorUtils;
import android.magician.com.myappmovies.utilities.NetworkInfos;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final static String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public static final String KEY_MOVIES_LIST = "moviesList";
    private final int NUMBER_OF_COLUMNS = 2;

    private int mPosition = RecyclerView.NO_POSITION;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private MoviesListAdapter mMoviesAdapter;
    private MainActivityViewModel mMainViewModel;
//    //define a callback to make communicate between fragment to activity
//    private OnImageClickListener mOnImageClickListener;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    //TODO:(2) move this inside Fragment?? or inside data and use repository to get the values when create URL
    // and start connection
    // Updates the screen if the shared preferences change. This method is required when you make a
    // class implement OnSharedPreferenceChangedListener
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {//worked
        Log.i(LOG_TAG, "Settings key changed: " + key);
        if (key.equals(getString(R.string.pref_sort_key))) {//still can't update data on fragment liast
            Log.i(LOG_TAG, "True : " + key);
            // TODO:(3) pass the new value to the network or db to generate new List order
            subscribeUi();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listener on changed sort order preference: //worked
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

    }


    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        //try and catach for check
//        try {
//            mOnImageClickListener = (OnImageClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + "must Implement OnImageClickListener");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);

        mRecyclerView = view.findViewById(R.id.recycler_movies_list);
        //first 4 line: help to load image fast and to make them fit recycler view  good
        mRecyclerView.setHasFixedSize(true);//
        mRecyclerView.setItemViewCacheSize(20);//
        mRecyclerView.setDrawingCacheEnabled(true);//
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_OF_COLUMNS));
        //  mRecyclerView.addItemDecoration();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMoviesAdapter = new MoviesListAdapter(getContext());
        mRecyclerView.setAdapter(mMoviesAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO: this code move to inside the fragment??or better to pass dataList to fragment??
        // first,because we can use communication between ViewModels and callback
        if (NetworkInfos.isNetworkConnectionAvailable(this.getContext())) {
               /* get MoviesList object by  create ModelView by using Factory & use repository*/
            MainViewModelFactory mainFactory = InjectorUtils.provideMainActivityViewModelFactory(getActivity());
            mMainViewModel = ViewModelProviders.of(this.getActivity(), mainFactory).get(MainActivityViewModel.class);
              /* observe the data & pass it to the adapter*/
            mMainViewModel.getMoviesList().observe(this, movies -> {
                mMoviesAdapter.swapList(movies);
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            });
        } else {
            Toast.makeText(getContext(), "No Connection.", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }

    /* observe the data & pass it to the adapter*/
    private void subscribeUi() {
        if (NetworkInfos.isNetworkConnectionAvailable(this.getContext())) {
         /* observe the data & pass it to the adapter*/
            mMainViewModel.getMoviesList().observe(this, movies -> {
                mMoviesAdapter.swapList(movies);
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            });
        } else {
            Toast.makeText(getContext(), "No Connection.", Toast.LENGTH_LONG).show();
        }
    }


}
