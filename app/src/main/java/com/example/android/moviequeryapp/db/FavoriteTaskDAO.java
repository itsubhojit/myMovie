package com.example.android.moviequeryapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteTaskDAO {

    @Query("SELECT * FROM favoriteTask")
    List<FavoriteTaskEntry> loadAllTasks();

    @Query("SELECT * FROM favoriteTask WHERE id = :movie_id")
    List<FavoriteTaskEntry> checkMovieById(int movie_id);

    @Insert
    void insertTask(FavoriteTaskEntry taskEntry);

/*    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(FavoriteTaskEntry taskEntry);*/

    @Delete
    void deleteTask(FavoriteTaskEntry taskEntry);

}
