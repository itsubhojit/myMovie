package com.example.android.moviequeryapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviequeryapp.adapters.TrailerAdapter;
import com.example.android.moviequeryapp.api.API_Service;
import com.example.android.moviequeryapp.api.MovieAPI;
import com.example.android.moviequeryapp.db.FavoriteMovieDbHelper;
import com.example.android.moviequeryapp.db.FavoriteTaskDB;
import com.example.android.moviequeryapp.db.FavoriteTaskEntry;
import com.example.android.moviequeryapp.models.MovieList;
import com.example.android.moviequeryapp.models.ReviewResponse;
import com.example.android.moviequeryapp.models.ReviewResult;
import com.example.android.moviequeryapp.models.TrailerList;
import com.example.android.moviequeryapp.models.TrailerResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.*;

public class MovieDetails extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String movieTitle, movieReleaseDate, movieOverview, moviePosterPath;
    private Double movieRating;
    private static final String TAG = MovieDetails.class.getSimpleName();
    private TextView nameOfMovie, releaseDate, userRating, plotSynopsis;
    private ImageView moviePoster;
    private String BaseImageUrl = "http://image.tmdb.org/t/p/w342//";
    private RecyclerView recyclerViewTrailer;
    private RecyclerView.LayoutManager layoutManager;
    private TrailerAdapter trailerAdapter;
    private static boolean favStatus = true;
    private int Movie_ID;
    private FavoriteMovieDbHelper favoriteDbHelper;
    private MovieList favorite;
    private final AppCompatActivity activity = MovieDetails.this;


    private FavoriteTaskDB mDB;
    private CheckBox favoriteCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
         favoriteCheckbox = findViewById(R.id.checkBoxFavorite);

        mDB = FavoriteTaskDB.getInstance(getApplicationContext());

        //checkFavoriteMovieStatus();

        addOrRemoveMovieInSqlite();

        getIncomingIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIncomingIntent();
    }

    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void getIncomingIntent(){

        if (getIntent().hasExtra("title") && getIntent().hasExtra("release_date")){
//            Intent intentThatStartedThisActivity = getIntent();
            Movie_ID = getIntent().getIntExtra("id", 0);
            movieTitle = getIntent().getStringExtra("title");
            movieReleaseDate = getIntent().getStringExtra("release_date");
            movieRating = getIntent().getDoubleExtra("vote_average", 0.0);
            movieOverview = getIntent().getStringExtra("overview");
            moviePosterPath = getIntent().getStringExtra("poster_path");
            Log.d(TAG, " : PassedBy#Subhojit -> MovieDetails - getIncomingIntent and set details");

            // Setting Details Content
            setMovieDetails(Movie_ID, movieTitle, movieReleaseDate, movieRating, movieOverview, moviePosterPath);
            Log.d(TAG, " : PassedBy#Subhojit -> MovieDetails - loading trailer");
            loadingVideoTrailer(Movie_ID);
            setReviewData(Movie_ID);
        }
    }

    private void setMovieDetails(int ID, String Title, String ReleaseDate, Double Rating, String Overview, String PosterPath){
        Log.d(TAG, " : PassedBy#Subhojit -> MovieDetails - setMovieDetails");

        nameOfMovie = findViewById(R.id.originalTitle);
        releaseDate = findViewById(R.id.releaseDate);
        userRating =findViewById(R.id.voteAverage);
        plotSynopsis =findViewById(R.id.overView);
        moviePoster = findViewById(R.id.moviePoster);
        recyclerViewTrailer = findViewById(R.id.recyclerViewTrailer);

        nameOfMovie.setText(Title + ID);
        releaseDate.setText(ReleaseDate);
        userRating.setText(Rating.toString());
        plotSynopsis.setText("Overview : \n" + Overview);
        plotSynopsis.setMovementMethod(new ScrollingMovementMethod());
        String url = BaseImageUrl + PosterPath;
        Picasso.get().load(url).into(moviePoster);
        makeText(this,"Loading..", LENGTH_SHORT).show();


    }

    private void addOrRemoveMovieInSqlite(){

        favoriteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                int id = getIntent().getIntExtra("id",0);
                String title = getIntent().getStringExtra("title");
                String posterPath = getIntent().getStringExtra("poster_path");

                if(isChecked){
                    favoriteCheckbox.setButtonDrawable(R.drawable.sharp_favorite_black_18dp);
                    toast("Added to Favorite.");
                    // Shared Pref
                    /*SharedPreferences.Editor editor = getSharedPreferences("FAVORITE_CHECK_BOX_FILE", MODE_PRIVATE).edit();
                    editor.putBoolean("ADDED", (compoundButton.isChecked());
                    favoriteCheckbox.setChecked(sharedPreferences.getBoolean("ADDED", true));
                    editor.apply();*/

                    favoriteCheckbox.setChecked(true);

                    FavoriteTaskEntry taskEntry = new FavoriteTaskEntry();
                    taskEntry.setId(id);
                    taskEntry.setTitle(title);
                    taskEntry.setPosterPath(posterPath);
                    mDB.favoriteTaskDAO().insertTask(taskEntry);
                    finish();
                }else{
                    favoriteCheckbox.setButtonDrawable(R.drawable.sharp_favorite_border_black_18dp);
                    toast("Removed.");

                   /* SharedPreferences.Editor editor = getSharedPreferences("FAVORITE_CHECK_BOX_FILE", MODE_PRIVATE).edit();
                    editor.putBoolean("REMOVED", !((CheckBox)view).isChecked());
                    editor.apply();*/

//                    favoriteCheckbox.setChecked(false);
                    RemoveMovieFromFaveriteDB();
                }
            }
        });

    }

//    private void checkFavoriteMovieStatus(){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        boolean checkStatus = sharedPreferences.getBoolean("ADDED", false);
//        if(checkStatus){
//            favoriteCheckbox.setChecked(checkStatus);
//        }else{
//            favoriteCheckbox.setChecked(checkStatus);
//        }
//    }

    public void RemoveMovieFromFaveriteDB(){
        int id = getIntent().getIntExtra("id",0);
        FavoriteTaskEntry favoriteTaskEntry = new FavoriteTaskEntry();
        favoriteTaskEntry.setId(id);
        mDB.favoriteTaskDAO().deleteTask(favoriteTaskEntry);
        finish();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        addOrRemoveMovieInSqlite();
    }

    private void setReviewData(int movie_id){
        final TextView txtAuthor, txtContent;
        txtAuthor = findViewById(R.id.author_name);
        txtContent = findViewById(R.id.content);

        Log.d(TAG, "Subhojit --> setiingup the review data");
        API_Service apiService = MovieAPI.getClient().create(API_Service.class);
        Call<ReviewResponse> call = apiService.getReviews(movie_id, getString(R.string.API_KEY));
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                List<ReviewResult> reviewResults = response.body().getResults();
                if(!response.isSuccessful()){
                    return;
                }

                txtAuthor.setText(reviewResults.get(0).getAuthor());
                txtContent.setText(reviewResults.get(0).getContent());
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });


    }

    private boolean isThisMovieExist(int id){
        mDB.favoriteTaskDAO().checkMovieById(id);
        return true;
    }

    public void loadingVideoTrailer(int movie_id){
        Log.d(TAG, " : #Subhojit -> loadingVideoTrailer movie_id" + movie_id);

        API_Service apiService = MovieAPI.getClient().create(API_Service.class);
        toast(apiService.toString());

        Call<TrailerResponse> call = apiService.getTrailers(movie_id, getString(R.string.API_KEY));
        Log.d(TAG, " : #Subhojit -> After Fetch Trailers");

        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                Log.d(TAG, " : #Subhojit -> Trailers_OnResponse..");

                List<TrailerList> trailerList = response.body().getResults();
                if(trailerList == null){
                    toast("List is empty!");
                    return;
                }

                toast(trailerList.toString());
                if (!response.isSuccessful()) {
                    Log.d(TAG, " : #Subhojit -> No_Trailer_Response!!");
                    return;
                }

                Log.d(TAG, " : #Subhojit -> Trailer_Responding..");

                recyclerViewTrailer.setLayoutManager(layoutManager);
                recyclerViewTrailer.setHasFixedSize(true);
                trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerList);
                recyclerViewTrailer.setAdapter(trailerAdapter);
                recyclerViewTrailer.smoothScrollToPosition(0);
                trailerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                makeText(MovieDetails.this,
                        "Error fetching trailer data --> " + call.toString() + throwable.toString(),
                        LENGTH_SHORT).show();
            }
        });

    }
}


       /*private void SaveMovieToFaveriteDB(){
        int id = getIntent().getIntExtra("id",0);
        String title = getIntent().getStringExtra("title");
        String posterPath = getIntent().getStringExtra("poster_path");

//        Double voteAverage = getIntent().getDoubleExtra("vote_average",0);
//        String overView = getIntent().getStringExtra("overview");
//        String releaseDate =  getIntent().getStringExtra("release_date");

        if(isThisMovieExist(id)){
            favoriteCheckbox.setButtonDrawable(R.drawable.sharp_favorite_black_18dp);
            return;
        }else{
            favoriteCheckbox.setButtonDrawable(R.drawable.sharp_favorite_border_black_18dp);
            FavoriteTaskEntry taskEntry = new FavoriteTaskEntry();
            taskEntry.setId(id);
            taskEntry.setTitle(title);
            taskEntry.setPosterPath(posterPath);

            mDB.favoriteTaskDAO().insertTask(taskEntry);
            finish();
        }
    }*/


  /*  private void saveFavoriteMovie(){
        makeText(this,"Movie added to Favorite", LENGTH_SHORT).show();
        favoriteDbHelper = new FavoriteMovieDbHelper(activity);
        favorite = new MovieList();
        int movieid = getIntent().getExtras().getInt("Id");
        Double rate = getIntent().getDoubleExtra("Rating",0);

        favorite.setId(movieid);
        favorite.setOriginalTitle(movieTitle);
        favorite.setPosterPath(moviePosterPath);
        favorite.setVoteAverage(rate);
        favorite.setOverview(movieOverview);
        Log.d(TAG, " : PassedBy#Subhojit -> MovieDetails - addToFavorite");

        favoriteDbHelper.addToFavorite(favorite);
    }*/



