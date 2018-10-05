package com.example.android.moviequeryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.moviequeryapp.adapters.FavoriteMovieAdapter;
import com.example.android.moviequeryapp.adapters.PopularMovieAdapter;
import com.example.android.moviequeryapp.api.API_Service;
import com.example.android.moviequeryapp.api.MovieAPI;
import com.example.android.moviequeryapp.db.FavoriteMovieDbHelper;
import com.example.android.moviequeryapp.db.FavoriteTaskDB;
import com.example.android.moviequeryapp.db.FavoriteTaskEntry;
import com.example.android.moviequeryapp.models.MovieList;
import com.example.android.moviequeryapp.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainGridActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainGridActivity.class.getSimpleName();
    public TextView textErrorMessage;
    private ProgressBar loading;
    private RecyclerView mRecyclerView;
    private PopularMovieAdapter movieAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://api.themoviedb.org";
    private List<MovieList> movieLists;
    private String API_Key = null;
//    List<MovieList> movieList = new ArrayList<>();
    private FavoriteMovieDbHelper favoriteDbHelper;
    private AppCompatActivity activity = MainGridActivity.this;
    private FavoriteTaskDB mDb;
    private FavoriteMovieAdapter favoriteMovieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TAG is here..
        System.out.println("TAG = " + TAG);
        Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - onCreate - START");
        setContentView(R.layout.activity_main_grid);
        mRecyclerView = findViewById(R.id.rv_movieItems);
        textErrorMessage = findViewById(R.id.tv_error_message);
        loading = findViewById(R.id.loadingIndecator);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);

        API_Key = getString(R.string.API_KEY);
        if(API_Key.isEmpty()){
            showAPIErrorMessage();
            return;
        }
//        mDb = FavoriteTaskDB.getInstance(getApplicationContext());
        checkSelectedMovieLists();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, " : PassedBy#Subhojit -> onResume");
        //favoriteMovieAdapter.setTasks();

        checkSelectedMovieLists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, " : PassedBy#Subhojit -> onCreate - Loading Settings..");

        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                Log.d(TAG, " : PassedBy#Subhojit -> onCreate - Loading PopularMovie");
                Intent intent = new Intent(this, SettiingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadTopRatedMovies() {
        Log.d(TAG, " : #Subhojit -> loadTopRatedMovies");
        /*if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        apiService = retrofit.create(API_Service.class);*/
        loading.setVisibility(View.VISIBLE);

        API_Service apiService = MovieAPI.getClient().create(API_Service.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(getString(R.string.API_KEY));

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(TAG, " : #Subhojit -> LoadTopRated_OnResponse..");

                movieLists = response.body().getResults();

                if (!response.isSuccessful()) {
                    Log.d(TAG, " : #Subhojit -> No_Response!!");
                    return;
                }

                Log.d(TAG, " : #Subhojit -> Responding..");
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
                movieAdapter = new PopularMovieAdapter(getApplicationContext(), movieLists);
                mRecyclerView.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();
                loading.setVisibility(View.INVISIBLE);
                showMovie();
                Log.d(TAG, "Number of movies received: " + movieLists.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    public void loadPopularMovies() {
        loading.setVisibility(View.VISIBLE);
        /*if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();}*/

        API_Service apiService = MovieAPI.getClient().create(API_Service.class);
        Call<MovieResponse> call = apiService.getPopularMovies(getString(R.string.API_KEY));
        Log.d(TAG, " : #Subhojit -> API SERVICE");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieList> movies = response.body().getResults();
                Log.d(TAG, " : #Subhojit -> onResponse.." + movies.size());

                if (!response.isSuccessful()) {
                    Log.d(TAG, " : #Subhojit -> No Response!!");
                    return;
                }

                Log.d(TAG, " : #Subhojit -> Responding..");
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
                movieAdapter = new PopularMovieAdapter(getApplicationContext(), movies);
                mRecyclerView.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();
                loading.setVisibility(View.INVISIBLE);
                showMovie();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                showErrorMessage();
            }
        });
    }

    private void loadFavoriteLists() {
        List<FavoriteTaskEntry> lists = new ArrayList<>();

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        favoriteMovieAdapter = new FavoriteMovieAdapter(this, lists);

       /* if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }*/

        mRecyclerView.setAdapter(favoriteMovieAdapter);
        FetchFavoriteMovie();
        favoriteMovieAdapter.notifyDataSetChanged();

    }

    private void checkSelectedMovieLists() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(this.getString(R.string.pref_movie_key),
                this.getString(R.string.pref_popular_movie_key));

        if(sortOrder.equals(this.getString(R.string.pref_favorite_movie_key))) {
            loadFavoriteLists();
        }else if(sortOrder.equals(this.getString(R.string.pref_top_rated_movie_key))){
            loadTopRatedMovies();
        }else{
            loadPopularMovies();
        }
    }

    public void showMovie() {
        textErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        Log.d(TAG, " : PassedBy#Subhojit -> showMovie(){}");
    }

    public void showErrorMessage() {
        Log.d(TAG, " : PassedBy#Subhojit -> showErrorMessage(){}");
        loading.setVisibility(View.INVISIBLE);
        textErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public void showAPIErrorMessage() {
        toastMessage("No API Key found!");
        textErrorMessage.setVisibility(View.VISIBLE);
        textErrorMessage.setText("API Key is empty!");
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(TAG, " : PassedBy#Subhojit -> onSharedPreferenceChanged");
        checkSelectedMovieLists();
    }

    public void toastMessage(String anyText) {
        Toast.makeText(getApplicationContext(), anyText, Toast.LENGTH_SHORT).show();
    }

    private void FetchFavoriteMovie(){
        mDb = FavoriteTaskDB.getInstance(getApplicationContext());
        favoriteMovieAdapter.setTasks(mDb.favoriteTaskDAO().loadAllTasks());
        favoriteMovieAdapter.notifyDataSetChanged();


        /*movieLists.clear();
        movieLists.addAll(favoriteDbHelper.getAllFavorite());
        movieAdapter.notifyDataSetChanged();*/

      /*  new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                movieLists.clear();
                movieLists.addAll(favoriteDbHelper.getAllFavorite());
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                movieAdapter.notifyDataSetChanged();
            }
        }.execute();*/
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////

/*        public void loadPopularMovies(){
            Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - loadPopularMovies()");
            String x = getString(R.string.API_KEY);
            URL url = NetworkUtils.buildPopularMovieUrl(x);
            new BackgroundTask_AsyncTask().execute(url);
            showMovie();
        }

        public void loadTopRatedMovies(){
            Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - loadTopRatedMovies");
            String x = getString(R.string.API_KEY);
            URL url = NetworkUtils.buildTopRatedMovieUrl(x);
            new BackgroundTask_AsyncTask().execute(url);
            showMovie();
        }*/

    // ********************************************************************** //

/*public class BackgroundTask_AsyncTask extends AsyncTask<URL, String, List<MovieModel>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading.setVisibility(View.VISIBLE);
        Log.d(TAG, " : PassedBy#Subhojit -> onPreExecute(){}");
    }

    protected List<MovieModel> doInBackground(URL... urls) {
        Log.d(TAG, " : PassedBy#Subhojit -> doInBackground - START");

        if (urls.length == 0) {
            return null;
        }

        URL searchUrl = urls[0];
        URL url;

        try {
            url = searchUrl;
            String rawJsonStringDataFromServer = NetworkUtils.getResponseFromHttpUrl(url);
            List<MovieModel> convertedJsonToGsonData = ConvertJsonUnits
                    .getConvertedSimpleJsonData(rawJsonStringDataFromServer);
            Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - BackgroundTask_AsyncTask() DATA Converted.");

            return convertedJsonToGsonData;

        } catch (JSONException x){
            x.printStackTrace();
            Log.d(TAG, " : PassedBy#Subhojit -> doInBackground - JSON EXCEPTION CATCHES");

            return null;
        } catch (Exception e) {
            Log.d(TAG, " : PassedBy#Subhojit -> doInBackground - EXCEPTION CATCHED");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<MovieModel> results) {
        Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - START");
        super.onPostExecute(results);
        loading.setVisibility(View.INVISIBLE);
        movieModels = results;
        if(results != null){
            layoutManager = new GridLayoutManager(getApplicationContext(),2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            pMovieAdapter = new PopularMovieAdapter(MainGridActivity.this, results);

            Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - SET_ADAPTER");
            mRecyclerView.setAdapter(pMovieAdapter);
            Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - ADAPTER_UPDATED");
            pMovieAdapter.updateMovies(results);
            pMovieAdapter.notifyDataSetChanged();

        }else{
            showErrorMessage();
            Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - ELSE Show Error Message");
        }
        Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - Done.");
    }
}*/
