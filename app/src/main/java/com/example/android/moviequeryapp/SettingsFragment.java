package com.example.android.moviequeryapp;


import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat{
    private CheckBoxPreference checkBoxPopularMovies, checkBoxTopRatedMovies;
    private SharedPrefConfig prefConfig;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movie_sort);
        prefConfig = new SharedPrefConfig(getContext());

    }
}
