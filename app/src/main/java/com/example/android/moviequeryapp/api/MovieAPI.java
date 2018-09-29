package com.example.android.moviequeryapp.api;

import android.util.Log;

import com.example.android.moviequeryapp.MainGridActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieAPI {
 private static final String TAG = MovieAPI.class.getSimpleName();
 public static final String BASE_URL = "http://api.themoviedb.org";

 private static Retrofit retrofit = null;

 public Retrofit getClient() {
  Log.d(TAG, " : MSG#Subhojit -> MovieAPI.getClient()");

  HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
  interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

  OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

  retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .client(client)
          .build();


  return retrofit;
 }

}
