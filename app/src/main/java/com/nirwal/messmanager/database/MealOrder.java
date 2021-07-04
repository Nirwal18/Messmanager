package com.nirwal.messmanager.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="meal_order_table")
public class MealOrder {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int order_type;

    //under construction


    public MealOrder(int id) {


    }
}
