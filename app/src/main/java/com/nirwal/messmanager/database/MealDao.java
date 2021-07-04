package com.nirwal.messmanager.database;

import com.nirwal.messmanager.models.Meal;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MealDao {

    @Insert
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("DELETE FROM meal_order_table")
    void deleteAllMeals();

//    @Query("SELECT * FROM resent_table")
//    LiveData<List<File>> getAll();
}
