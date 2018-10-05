package com.example.android.moviequeryapp.api;

import android.util.Log;

import com.example.android.moviequeryapp.MainGridActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieAPI {
 private static final String TAG = MovieAPI.class.getSimpleName();
 public static final String BASE_URL = "http://api.themoviedb.org/3/";

 public static Retrofit retrofit = null;

 public static Retrofit getClient(){
  if (retrofit == null){
   retrofit = new Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build();
  }
  return retrofit;
 }
}
