package com.example.android.moviequeryapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviequeryapp.internet.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = MovieDetails.class.getSimpleName();
    private TextView nameOfMovie, releaseDate, userRating, plotSynopsis;
    private ImageView moviePoster;
    private String BaseImageUrl = "http://image.tmdb.org/t/p/w342//";
    private CheckBox favoriteCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getIncomingIntent();
        Log.d(TAG, " : PassedBy#Subhojit -> onCreate-MovieDetails - getIncomingIntent");
    }


    private void getIncomingIntent(){
        if (getIntent().hasExtra("Title") && getIntent().hasExtra("ReleaseDate")){
//            Intent intentThatStartedThisActivity = getIntent();
            int id = getIntent().getIntExtra("Id", 0);
            String movieTitle = getIntent().getStringExtra("Title");
            String movieReleaseDate = getIntent().getStringExtra("ReleaseDate");
            double movieRating = getIntent().getDoubleExtra("Rating", 0.0);
            String movieOverview = getIntent().getStringExtra("Overview");
            String moviePosterPath = getIntent().getStringExtra("PosterPath");

            // Setting Details Content
            setMovieDetails(id, movieTitle, movieReleaseDate, movieRating, movieOverview, moviePosterPath);
        }
    }

    private void setMovieDetails(int ID, String Title, String ReleaseDate, Double Rating, String Overview, String PosterPath){
        nameOfMovie = findViewById(R.id.originalTitle);
        releaseDate = findViewById(R.id.releaseDate);
        userRating =findViewById(R.id.voteAverage);
        plotSynopsis =findViewById(R.id.overView);
        moviePoster = findViewById(R.id.moviePoster);

        nameOfMovie.setText(Title+ " id: " + ID);
        releaseDate.setText(ReleaseDate);
        userRating.setText(Rating.toString());
        plotSynopsis.setText("Overview : \n" + Overview);
        plotSynopsis.setMovementMethod(new ScrollingMovementMethod());
        String url = BaseImageUrl + PosterPath;
        Picasso.get().load(url).into(moviePoster);
        loadingVideoTrailer(ID);
//        Toast.makeText(this,url, Toast.LENGTH_SHORT).show();
    }

    public void OnClickedFavorite(View view) {
        favoriteCheckbox = findViewById(R.id.checkBoxFavorite);
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBoxFavorite:
                if (checked){
                    addFavoriteMovies();
                }
                else
                    break;

        }
    }


    private void addFavoriteMovies(){
        Toast.makeText(this,"Movie added to Favorite", Toast.LENGTH_SHORT).show();
        favoriteCheckbox.setButtonDrawable(R.drawable.sharp_favorite_black_18dp);
    }

    private void isThisMovieAddedToFavoriteDB(){
    }

    public void loadingVideoTrailer(int id){
        String x = getString(R.string.API_KEY);
        URL url = NetworkUtils.buildVideoUrl(id, x);
        Toast.makeText(this,url.toString(), Toast.LENGTH_SHORT).show();

//            new BackgroundTask_AsyncTask().execute(url);
//            showMovie();
    }

}


