package com.example.android.moviequeryapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.moviequeryapp.R;
import com.example.android.moviequeryapp.db.FavoriteTaskDB;
import com.example.android.moviequeryapp.db.FavoriteTaskEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteHolder>{
    private List<FavoriteTaskEntry> favoriteTaskEntryList;
    private Context mContext;
    private String imageUrl = "http://image.tmdb.org/t/p/w342//";
    private FavoriteTaskDB mDb;


    public FavoriteMovieAdapter(Context mContext, List<FavoriteTaskEntry> list) {
        this.mContext = mContext;
        this.favoriteTaskEntryList = list;
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.single_image_text_view, parent, false);

        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.FavoriteHolder holder, int position) {
        FavoriteTaskEntry item = favoriteTaskEntryList.get(position);

        holder.txtTitle.setText(item.getTitle());
        String url = imageUrl + item.getPosterPath();
        Picasso.get().load(url).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        if(favoriteTaskEntryList == null){
            return 0;
        }else{
            return favoriteTaskEntryList.size();
        }
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        ImageView imgPoster;
        LinearLayout linearLayout;
        public FavoriteHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.gridItem_movieTitle);
            imgPoster = itemView.findViewById(R.id.gridItem_moviePoster);
            linearLayout = itemView.findViewById(R.id.parentLinearLayout);
        }
    }


    public void setTasks(List<FavoriteTaskEntry> taskEntries) {
        favoriteTaskEntryList = taskEntries;
        notifyDataSetChanged();
    }
}
