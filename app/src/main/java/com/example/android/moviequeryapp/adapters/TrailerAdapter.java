package com.example.android.moviequeryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviequeryapp.R;
import com.example.android.moviequeryapp.models.MovieList;
import com.example.android.moviequeryapp.models.TrailerList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyTrailerViewHolder> {
    private Context mContext;
    private List<TrailerList> trailerListItems;

    public TrailerAdapter(Context context, List<TrailerList> list) {
        this.mContext = context;
        this.trailerListItems = list;
    }

    @NonNull
    @Override
    public MyTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_layout, parent, false);
        return new MyTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTrailerViewHolder holder, int position) {
        holder.title.setText(trailerListItems.get(position).getName());
        /*String poster = "https://image.tmdb.org/t/p/w500" + movieLists.get(position).getPosterPath();
        Picasso.get().load(poster).into(holder.thumbnail);*/

    }

    @Override
    public int getItemCount() {
        if(trailerListItems == null){
            return 0;
        }else{
            return trailerListItems.size();
        }
    }

    public class MyTrailerViewHolder extends RecyclerView.ViewHolder {
       TextView title, rating;
       ImageView thumbnail;
        public MyTrailerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trailer_title);
//            rating = itemView.findViewById(R.id.trailer_rating);
            thumbnail = itemView.findViewById(R.id.trailer_poster);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        int videoId = trailerListItems.get(pos).getId();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoId));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("VIDEO_ID", videoId);
                        mContext.startActivity(intent);

                        Toast.makeText(v.getContext(), "You clicked "
                                + trailerListItems.get(pos).getId(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
