package com.example.android.moviequeryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviequeryapp.MovieDetails;
import com.example.android.moviequeryapp.R;
import com.example.android.moviequeryapp.db.FavoriteTaskEntry;
import com.example.android.moviequeryapp.models.MovieList;
import com.squareup.picasso.Picasso;

import java.util.AbstractSequentialList;
import java.util.List;


public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder> {
    private static final String TAG = PopularMovieAdapter.class.getSimpleName();
    private List<MovieList> movieList;
    private Context mContext;
    private String imageUrl = "http://image.tmdb.org/t/p/w342//";
    private List<FavoriteTaskEntry> mTaskEntries;

    public PopularMovieAdapter(Context context, List<MovieList> itemList){
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
        MovieList item = movieList.get(position);
        holder.txtTitle.setText(item.getTitle());
        String url = imageUrl + item.getPosterPath();
        Picasso.get().load(url).into(holder.imgPoster);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, " : PassedBy#Subhojit -> onBindViewHolder - START setOnItemClickListener");
                Intent intentThatPassingTheData = new Intent(mContext, MovieDetails.class);
                intentThatPassingTheData.putExtra("id", movieList.get(position).getId());
                intentThatPassingTheData.putExtra("title", movieList.get(position).getOriginalTitle());
                intentThatPassingTheData.putExtra("release_date", movieList.get(position).getReleaseDate());
                intentThatPassingTheData.putExtra("vote_average", movieList.get(position).getVoteAverage());
                intentThatPassingTheData.putExtra("overview", movieList.get(position).getOverview());
                intentThatPassingTheData.putExtra("poster_path", movieList.get(position).getPosterPath());
                intentThatPassingTheData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d(TAG, " : PassedBy#Subhojit -> onBindViewHolder to Details Activity.");

                mContext.startActivity(intentThatPassingTheData);
            }
        });

        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - onBindViewHolder");

    }

    @Override

    public int getItemCount() {

        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - getItemCount");
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        ImageView imgPoster;
        LinearLayout linearLayout;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.gridItem_movieTitle);
            imgPoster = itemView.findViewById(R.id.gridItem_moviePoster);
            linearLayout = itemView.findViewById(R.id.parentLinearLayout);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        MovieList clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(mContext, MovieDetails.class);
                        intent.putExtra("movies", String.valueOf(clickedDataItem));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
            Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - MovieViewHolder");
        }
    }

    public void updateMovies(List<MovieList> items){
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - updateMovies");

        this.movieList = items;
    }

    public void removeItem(int position){
        movieList.remove(position);
        Log.d(TAG, " : PassedBy#Subhojit -> PopularMovieAdapter - removeItem");

    }



}
