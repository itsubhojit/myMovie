package com.example.android.moviequeryapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyTrailerViewHolder> {

    @NonNull
    @Override
    public MyTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyTrailerViewHolder extends RecyclerView.ViewHolder {

        public MyTrailerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
