package com.example.android.moviequeryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.moviequeryapp.models.MovieModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = MovieDetails.class.getSimpleName();
    TextView nameOfMovie, releaseDate, userRating, plotSynopsis;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Log.d(TAG, " : PassedBy#Subhojit -> onCreate-MovieDetails - START");

        Gson gson = new Gson();
        Intent intentThatStartedThisActivity = getIntent();
        String json = intentThatStartedThisActivity.getExtras().getString("JSON");
        MovieModel movieList = gson.fromJson(json, MovieModel.class);

        moviePoster = findViewById(R.id.moviePoster);
        nameOfMovie = findViewById(R.id.originalTitle);
        releaseDate = findViewById(R.id.releaseDate);
        userRating =findViewById(R.id.voteAverage);
        plotSynopsis =findViewById(R.id.overView);

            nameOfMovie.setText(movieList.getOriginal_title());
            releaseDate.setText(movieList.getRelease_date());
            userRating.setText(String.valueOf(movieList.getVote_average()));

            plotSynopsis.setText("Overview : \n" + movieList.getOverview());
            plotSynopsis.setMovementMethod(new ScrollingMovementMethod());

            Picasso.get().load("http://image.tmdb.org/t/p/w342//" + movieList.getPoster_path()).into(moviePoster);
            Toast.makeText(this,"Success...", Toast.LENGTH_SHORT).show();
    }
}
