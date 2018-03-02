package android.magician.com.myappmovies.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.magician.com.myappmovies.R;
import android.magician.com.myappmovies.ui.settings.SettingsActivity;
import android.magician.com.myappmovies.utilities.NetworkInfos;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    //        implements SharedPreferences.OnSharedPreferenceChangeListener
    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container_list) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            //remeber if u pass values to the fragment u need to check before create
            // Create a new Fragment to be placed in the activity layout
            MainActivityFragment listFragment = new MainActivityFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_list, listFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    //TODO:(2) move this inside Fragment?? or inside data and use repository to get the values when create URL
//    // and start connection
//    // Updates the screen if the shared preferences change. This method is required when you make a
//    // class implement OnSharedPreferenceChangedListener
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        Log.d(LOG_TAG, "onSharedPreferenceChanged");
//        if (key.equals(getString(R.string.pref_sort_key))) {
//            // TODO:(3) pass the new value to the network or db to generate new List order
//            MainActivityFragment newListFragment = new MainActivityFragment();
//            // Add the fragment to the 'fragment_container' FrameLayout
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container_list, newListFragment)
//                    .commit();
//
//        }
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
//        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

}
