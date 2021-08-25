package com.example.alnoorstore.database;

import android.content.Context;
import com.example.alnoorstore.model.Product;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Product.class, version = 1)
public abstract class StoreDatabase extends RoomDatabase {

    private static final String DB_NAME = "store_db";
    private static StoreDatabase instance;

    public static synchronized StoreDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), StoreDatabase.class, DB_NAME)
                    .build();
        }

        return instance;
    }

    public abstract ProductDao productDao();
}
