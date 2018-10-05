package com.example.android.moviequeryapp.adapters;
import android.view.LayoutInflater;
import android.content.Context;

import com.example.android.moviequeryapp.models.MovieModel;

import java.util.List;

public class SingleViewAdapter  { //extends BaseAdapter
    private static final String TAG = SingleViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<MovieModel> movieModelList;
    private LayoutInflater inflater;

/*
    public SingleViewAdapter(Context c, List<MovieListResult> movieList) {
        mContext = c;
        movieModelList = movieList;
        inflater = LayoutInflater.from(c);
        Log.d(TAG, " : PassedBy#Subhojit -> SingleViewAdapter - CONSTRUCTOR CALLED");
    }

    public int getCount() {
        if(movieModelList.size() == 0)
            return 0;
        else
        Log.d(TAG, " : PassedBy#Subhojit -> getCount - CALLED");
        return movieModelList.size();
    }

    public Object getItem(int position) {
        return movieModelList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View customView;
        TextView movieTitle;
        ImageView movieImage;
        String imageUrl = "http://image.tmdb.org/t/p/w342//";

        if (convertView == null) {
            customView = new View(mContext);
            customView = inflater.inflate(R.layout.single_image_text_view, parent, false);
        }else{
            customView = convertView;
        }

        movieTitle = customView.findViewById(R.id.gridItem_movieTitle);
//        movieImage = customView.findViewById(R.id.gridItem_moviePoster);
//        TextView itemNumber = customView.findViewById(R.id.gridItem_id);
        if(position %2 == 1)
        {
            // Set a background color for List / Grid View regular row/item
//            movieImage.setBackgroundColor(Color.parseColor("#302C2C"));
        }
        else {
            // Set the background color for alternate row/item
//            movieImage.setBackgroundColor(Color.parseColor("#626B65"));
        }

//        itemNumber.setText("At no." + (position+1));
        movieTitle.setText(movieModelList.get(position).getTitle());

//        Image Resolutions : "w92", "w154", "w185", "w342", "w500", "w780", or "original"
//        Picasso.get().load(imageUrl + movieModelList.get(position).getPoster_path()).into(movieImage);

        return customView;
    }

    public void updateMovies(ArrayList<MovieModel> items){
        this.movieModelList = items;
    }*/
}
