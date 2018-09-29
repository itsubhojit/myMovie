package com.example.android.moviequeryapp.api;


import com.example.android.moviequeryapp.models.MovieList;
import com.example.android.moviequeryapp.models.MovieResponse;
import com.example.android.moviequeryapp.models.TrailerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Service {

    @GET("/3/movie/popular")
    Call<MovieList> getPopularMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/top_rated")
    Call<MovieList> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/videos")
    Call<MovieList> getTrailers(@Path("movie_id") int id, @Query("api_key") String apiKey);

}
