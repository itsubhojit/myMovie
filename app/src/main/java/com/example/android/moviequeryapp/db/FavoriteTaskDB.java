package com.example.android.moviequeryapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavoriteTaskEntry.class}, version = 2, exportSchema = false)
public abstract class FavoriteTaskDB extends RoomDatabase {

    private static FavoriteTaskDB favTaskDB;
    private static final String DATABASE_NAME = "favoritemovies";
    private static final Object LOCK = new Object();


    public static FavoriteTaskDB getInstance(Context context) {
        if (favTaskDB == null) {
            synchronized (LOCK) {
//                Log.d(LOG_TAG, "Creating new database instance");
                favTaskDB = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteTaskDB.class, FavoriteTaskDB.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        // COMPLETED (2) call allowMainThreadQueries before building the instance
                        // Queries should be done in a separate thread to avoid locking the UI
                        // We will allow this ONLY TEMPORALLY to see that our DB is working
                        .allowMainThreadQueries()
                        .build();
            }
        }
//        Log.d(LOG_TAG, "Getting the database instance");
        return favTaskDB;
    }

    public abstract FavoriteTaskDAO favoriteTaskDAO();
}
