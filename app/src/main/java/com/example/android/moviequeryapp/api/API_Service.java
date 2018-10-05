package com.example.android.moviequeryapp.api;


import com.example.android.moviequeryapp.models.MovieResponse;
import com.example.android.moviequeryapp.models.TrailerList;
import com.example.android.moviequeryapp.models.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(@Path("movie_id") int id, @Query("api_key") String apiKey);

}
