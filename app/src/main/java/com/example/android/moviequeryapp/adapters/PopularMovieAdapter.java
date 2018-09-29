package com.example.android.moviequeryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.moviequeryapp.MovieDetails;
import com.example.android.moviequeryapp.R;
import com.example.android.moviequeryapp.models.MovieList;
import com.example.android.moviequeryapp.models.MovieResponse;
import com.squareup.picasso.Picasso;
import java.util.List;


public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder> {

    private static final String TAG = PopularMovieAdapter.class.getSimpleName();
    private List<MovieResponse> movieList;
    private Context mContext;
    private String imageUrl = "http://image.tmdb.org/t/p/w342//";


    public PopularMovieAdapter(Context context, List<MovieResponse> itemList){
        this.movieList = itemList;
        this.mContext = context;
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - Constructor");
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - onCreateViewHolder");

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View newView = inflater.inflate(R.layout.single_image_text_view, parent, false);
        return new MovieViewHolder(newView);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        MovieResponse item = movieList.get(position);

        holder.txtTitle.setText(item.getTitle());
        String url = imageUrl + item.getPosterPath();
        Picasso.get().load(url).into(holder.imgPoster);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, " : PassedBy#Subhojit -> onCreate - START setOnItemClickListener");
                Intent intentThatPassingTheData = new Intent(mContext, MovieDetails.class);
                intentThatPassingTheData.putExtra("Id", movieList.get(position).getId());
                intentThatPassingTheData.putExtra("Title", movieList.get(position).getOriginalTitle());
                intentThatPassingTheData.putExtra("ReleaseDate", movieList.get(position).getReleaseDate());
                intentThatPassingTheData.putExtra("Rating", movieList.get(position).getVoteAverage());
                intentThatPassingTheData.putExtra("Overview", movieList.get(position).getOverview());
                intentThatPassingTheData.putExtra("PosterPath", movieList.get(position).getPosterPath());
                mContext.startActivity(intentThatPassingTheData);
                Log.d(TAG, " : PassedBy#Subhojit -> onCreate - END setOnItemClickListener");
            }
        });


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
        LinearLayout linearLayout;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.gridItem_movieTitle);
            imgPoster = itemView.findViewById(R.id.gridItem_moviePoster);
            linearLayout = itemView.findViewById(R.id.parentLinearLayout);
            Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - MovieViewHolder");
        }
    }


    public void updateMovies(List<MovieResponse> items){
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - updateMovies");

        this.movieList = items;
    }

    public void removeItem(int position){
        movieList.remove(position);
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - removeItem");

    }

}
