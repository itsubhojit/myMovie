package com.example.android.moviequeryapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefConfig {

    private SharedPreferences sharedPreferences;
    private Context mContext;

    public SharedPrefConfig(Context context) {
        this.mContext = context;
        /*sharedPreferences = mContext.getSharedPreferences(mContext.getResources()
                .getString(R.string.pref_popular_movie_label), mo);*/
    }

    public void loadPopularMoviesStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(mContext.getResources().getString(R.string.pref_popular_movie_key), status);
        editor.apply();
    }

    public void loadTopRatedMoviesStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(mContext.getResources().getString(R.string.pref_top_rated_movie_key), status);
        editor.apply();
    }

    public boolean readPopularMovieStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(mContext.getResources()
                .getString(R.string.pref_popular_movie_key),
                mContext.getResources().getBoolean(R.bool.is_loaded_popular));
        return status;
    }

    public boolean readTopRatedMovieStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(mContext.getResources()
                        .getString(R.string.pref_top_rated_movie_key),
                mContext.getResources().getBoolean(R.bool.is_loaded_top_rated));
        return status;
    }
}
