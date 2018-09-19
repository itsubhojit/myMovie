package com.example.android.moviequeryapp;

import android.util.Log;
import com.example.android.moviequeryapp.models.MovieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public final class ConvertJsonUnits {
    private static final String TAG = ConvertJsonUnits.class.getSimpleName();

    /*
     * In this below line an ArrayList variable called movieModelList should not be
     * declare here instead of doing this declare it inside getConvertedSimpleJsonData()
     * private static ArrayList<MovieModel> movieModelList = new ArrayList<>();
     * */


    public static List<MovieModel> getConvertedSimpleJsonData(String rawJsonData)
        throws JSONException {
        Log.d(TAG, " : PassedBy#Subhojit -> ConvertJsonUnits - START");

        JSONObject parentJO = new JSONObject(rawJsonData);
        JSONArray parentJA = parentJO.getJSONArray("results");
        List<MovieModel> movieModelList = new ArrayList<>();
//        Gson gson = new Gson();

        if (rawJsonData != null){
            try{
//                Type type = new TypeToken<ArrayList<MovieModel>>() {}.getType();
                for(int i = 0; i < parentJA.length(); i++){
//                    JSONObject finalJO = parentJA.getJSONObject(Integer.parseInt(String.valueOf(i)));
//                    MovieModel movieModel = gson.fromJson(finalJO.toString(), MovieModel.class);

                    JSONObject finalJO = parentJA.getJSONObject(i);
                    MovieModel movieModel = new MovieModel();

                    movieModel.setTitle(finalJO.getString("title"));
                    movieModel.setOriginal_title(finalJO.getString("original_title"));
                    movieModel.setRelease_date(finalJO.getString("release_date"));
                    movieModel.setPoster_path(finalJO.getString("poster_path"));
                    movieModel.setOverview(finalJO.getString("overview"));
                    movieModel.setVote_average(finalJO.getDouble("vote_average"));

                    movieModelList.add(movieModel);
                    Log.d(TAG, " : PassedBy#Subhojit -> ConvertJsonUnits - GSON END FOR LOOP");
                }
                return movieModelList;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Log.d(TAG, " : PassedBy#Subhojit -> ConvertJsonUnits - FINISH");
        return movieModelList;
    }
}