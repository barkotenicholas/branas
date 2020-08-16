package com.demo.productcatalogue.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.demo.productcatalogue.model.ProductModel;

@Database(entities = {ProductModel.class},exportSchema = false,version = 1)
public abstract class ProductsDatabase extends RoomDatabase {

    private static final String DB_NAME = "product_db";
    private static ProductsDatabase instance;

    public static synchronized ProductsDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ProductsDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    abstract public ProductsDao productsDao();
}