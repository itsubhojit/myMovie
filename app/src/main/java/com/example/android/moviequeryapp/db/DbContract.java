package com.example.android.moviequeryapp.db;

import android.provider.BaseColumns;

public class DbContract{
    public static final class FavotiteMovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "favourite_movies";
        public static final String COLUMN_MOVIE_NAME = "original_title";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_RATING = "vote_average";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
    }
}
