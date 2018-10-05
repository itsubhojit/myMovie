package com.example.android.moviequeryapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.android.moviequeryapp.models.MovieList;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteMovieDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavotiteMovieEntry.TABLE_NAME + " (" +
                FavoriteContract.FavotiteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " +
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_RATING + " REAL NOT NULL, " +
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavotiteMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addToFavorite(MovieList movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_NAME, movie.getOriginalTitle());
        values.put(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_RATING, movie.getVoteAverage());
        values.put(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
        values.put(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());

        db.insert(FavoriteContract.FavotiteMovieEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavotiteMovieEntry.TABLE_NAME,
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_ID+ "=" + id, null);
    }

    public List<MovieList> getAllFavorite(){
        String[] columns = {
                FavoriteContract.FavotiteMovieEntry._ID,
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_ID,
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_NAME,
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_RATING,
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_POSTER_PATH,
                FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_OVERVIEW

        };
        String sortOrder =
                FavoriteContract.FavotiteMovieEntry._ID + " ASC";
        List<MovieList> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavotiteMovieEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                MovieList movie = new MovieList();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_ID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_NAME)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_RATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavotiteMovieEntry.COLUMN_MOVIE_OVERVIEW)));

                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }




}
