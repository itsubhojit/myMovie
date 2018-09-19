package com.example.android.moviequeryapp;

import android.app.LauncherActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.moviequeryapp.models.MovieModel;
import com.squareup.picasso.Picasso;
import java.util.List;


public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder> {

    private static final String TAG = PopularMovieAdapter.class.getSimpleName();
    private List<MovieModel> movieList;
    private String imageUrl = "http://image.tmdb.org/t/p/w342//";


    public PopularMovieAdapter(List<MovieModel> itemList){
        this.movieList = itemList;
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - Contructor");

    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View newView = inflater.inflate(R.layout.movie_album_layout, parent, false);
      Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - onCreate");

        return new MovieViewHolder(newView);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        holder.txtTitle.setText(movieList.get(position).getTitle());
        Picasso.get().load(imageUrl + movieList.get(position).getPoster_path()).into(holder.imgPoster);
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - onBindViewHolder");

    }


    @Override

    public int getItemCount() {
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - getItemCount");

            return movieList.size();

    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        ImageView imgPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.movieTitle);
            imgPoster = itemView.findViewById(R.id.moviePoster);
            Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - MovieViewHolder");
        }
    }


    public void updateMovies(List<MovieModel> items){
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - updateMovies");

        this.movieList = items;
    }

    public void removeItem(int position){
        movieList.remove(position);
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - removeItem");

    }

}
