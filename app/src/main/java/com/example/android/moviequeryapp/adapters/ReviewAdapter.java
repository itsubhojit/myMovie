package com.example.android.moviequeryapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviequeryapp.R;
import com.example.android.moviequeryapp.models.ReviewResult;

import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.UserReviewHolder> {
    private static final String TAG = ReviewAdapter.class.getSimpleName();
    private List<ReviewResult> reviewResultList;

    public ReviewAdapter(List<ReviewResult> reviewResultList) {
        this.reviewResultList = reviewResultList;
    }

    @NonNull
    @Override
    public UserReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_layout, parent, false);
        return new UserReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewHolder holder, int position) {
        if(holder == null){

        }

        ReviewResult item = reviewResultList.get(position);
        holder.txtAuthor.setText(item.getAuthor());
        holder.txtContent.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        if(reviewResultList == null){
            return 0;
        }else{
            return reviewResultList.size();
        }
    }

    public class UserReviewHolder extends RecyclerView.ViewHolder{
        TextView txtAuthor, txtContent;
        public UserReviewHolder(View itemView) {
            super(itemView);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtContent = itemView.findViewById(R.id.txtContent);
        }
    }
}
