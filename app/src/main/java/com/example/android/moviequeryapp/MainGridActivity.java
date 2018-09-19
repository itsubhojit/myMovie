package com.example.android.moviequeryapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;
import com.example.android.moviequeryapp.models.MovieModel;

import org.json.JSONException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainGridActivity extends AppCompatActivity {

    private static final String TAG = MainGridActivity.class.getSimpleName();
//        private GridView movieItems;
//        private SingleViewAdapter singleViewAdapterInstance;
        public  TextView textErrorMessage;
        private ProgressBar loading;
        private RecyclerView mRecyclerView;
        private PopularMovieAdapter pMovieAdapter;
        private List<MovieModel> movieModels;

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

//            singleViewAdapterInstance = new SingleViewAdapter(getApplicationContext(), new ArrayList<MovieModel>());
//            movieItems.setAdapter(recyclerView);

            


/*

                    Log.d(TAG, " : PassedBy#Subhojit -> onCreate - START setOnItemClickListener");

                    MovieModel movieList = (MovieModel) movieGridView.getAdapter().getItemId(position);
                    Gson gson = new Gson();
                    String json = gson.toJson(movieList);

                    Intent intentThatPassingTheData = new Intent(MainGridActivity.this, MovieDetails.class);
                    intentThatPassingTheData.putExtra("JSON", json);
                    startActivity(intentThatPassingTheData);

                    Log.d(TAG, " : PassedBy#Subhojit -> onCreate - END setOnItemClickListener");
*/
            loadPopularMovies();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - onCreateOptionsMenu");

            getMenuInflater().inflate(R.menu.settings, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            switch(id){
                case R.id.popular_movies:
                    Log.d(TAG, " : PassedBy#Subhojit -> onCreate - Calling PopularMovie");
                    loadPopularMovies();
                    return true;

                case R.id.top_rated_movies:
                    Log.d(TAG, " : PassedBy#Subhojit -> onCreate - Calling TopRatedMovie");
                    loadTopRatedMovies();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        public void loadPopularMovies(){
            Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - loadPopularMovies()");

            String x = "084c79c7722ce9496963780c61fa46a1";
            URL url = NetworkUtils.buildPopularMovieUrl(x);
            new BackgroundTask_AsyncTask().execute(url);
            showMovie();
        }

        public void loadTopRatedMovies(){
            String x = "084c79c7722ce9496963780c61fa46a1";
            URL url = NetworkUtils.buildTopRatedMovieUrl(x);
//            textShowUrl.setText(url.toString());
            new BackgroundTask_AsyncTask().execute(url);
            showMovie();

        }

        public void loadDiscoveredMovies(){
            String x = "084c79c7722ce9496963780c61fa46a1";
            Log.d(TAG, " : PassedBy#Subhojit -> loadDiscoveredMovies - buildDiscoverMovieUrl");
            URL url = NetworkUtils.buildDiscoverMovieUrl(x);
            Log.d(TAG, " : PassedBy#Subhojit -> BackgroundTask_AsyncTask() - START");
            new BackgroundTask_AsyncTask().execute(url);
            Log.d(TAG, " : PassedBy#Subhojit -> BackgroundTask_AsyncTask() - FINISH");
            showMovie();
        }

        public void showMovie(){
            textErrorMessage.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            Log.d(TAG, " : PassedBy#Subhojit -> showMovie(){}");
        }

        public void showErrorMessage(){
            textErrorMessage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            Log.d(TAG, " : PassedBy#Subhojit -> showErrorMessage(){}");
        }

        // ********************************************************************** //

public class BackgroundTask_AsyncTask extends AsyncTask<URL, String, List<MovieModel>> {

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
            List<MovieModel> convertedJsonToGsonData = ConvertJsonUnits.getConvertedSimpleJsonData(rawJsonStringDataFromServer);
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
//        if(results != null){
//            singleViewAdapterInstance.updateMovies(results);
//            singleViewAdapterInstance.notifyDataSetChanged();
//        }else{
//            showErrorMessage();
//            Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - ELSE");
//        }
//        Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - END");
        if(results != null){
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            pMovieAdapter = new PopularMovieAdapter(results);
            Log.d(TAG, " : PassedBy#Subhojit -> MainGridActivity - onCreate - LoadBegins");
            mRecyclerView.setAdapter(pMovieAdapter);
            Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - SET_ADAPTER");

            pMovieAdapter.updateMovies(results);
            Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - ADAPTER_UPDATED");

            pMovieAdapter.notifyDataSetChanged();

        }else{
            showErrorMessage();
            Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - ELSE");
        }
        Log.d(TAG, " : PassedBy#Subhojit -> onPostExecute - END");
    }
}
}
