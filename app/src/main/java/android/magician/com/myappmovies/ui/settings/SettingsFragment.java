package android.magician.com.myappmovies.ui.settings;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.magician.com.myappmovies.R;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

public class SettingsFragment extends PreferenceFragmentCompat implements
        OnSharedPreferenceChangeListener {
    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // defined in the XML file in res->xml->pref_settings
        addPreferencesFromResource(R.xml.pref_settings);
        Log.i(LOG_TAG, "pref Created");
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        //int count = prefScreen.getPreferenceCount();

        //List preference          >>  this work with PreferenceCategory
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.pref_sort_key));
        String value = sharedPreferences.getString(listPreference.getKey(), getString(R.string.pref_sort_default_value));
        int indexOfValue = listPreference.findIndexOfValue(value);

        // value and indexOfValue are used to get the labelString
        //I got error here before (java.lang.ArrayIndexOutOfBoundsException) idk why but i just add indexOfValue+1 worked
        //while i was using <array xml then i used <string-array i go same error but for the last value so i removed it worked. lol
        listPreference.setSummary(listPreference.getEntries()[indexOfValue]);

//        // Go through all of the preferences, and set up their preference summary.
//        // (used only with dynamic summary) have problem with PreferenceCategory
//        for (int i = 0; i < count; i++) {
//            Preference p = prefScreen.getPreference(i);
//            // You don't need to set up preference summaries for checkbox preferences because
//            // they are already set up in xml using summaryOff and summary On
//            Log.i(LOG_TAG, "Value:" );
//           //problem > still not work ?? when I add PreferenceCategory
//            if (p instanceof ListPreference) {
//                String value = sharedPreferences.getString(p.getKey(),getString(R.string.pref_sort_default));
//                Log.i(LOG_TAG, "Value: "+value );
//                // setPreferenceSummary(p, value);
//                setListPreferenceSummary((ListPreference) p, value);
//            }
//        }

    }

    //get the start value when  pref created
    private void setListPreferenceSummary(ListPreference p, String value) {
        int prefIndex = p.findIndexOfValue(value);
        if (prefIndex >= 0) {
            // Set the summary to that label
            p.setSummary(p.getEntries()[prefIndex]);
        }
    }

    //update the ui at running time when the change happen
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Figure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (preference instanceof ListPreference) {
                String value = sharedPreferences.getString(preference.getKey(), getString(R.string.pref_sort_default_value));
                setPreferenceSummary(preference, value);
            }
        }
    }

    /**
     * Updates the summary for the preference that have dynamic summary( ex: ListPreference )
     *
     * @param preference The preference to be updated
     * @param value      The value that the preference was updated to
     */
    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}