package com.tuan.exportpdf;


import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {model.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class modelRoomDB extends RoomDatabase {
    public abstract modelDao modelDao();
    private static volatile modelRoomDB INSTANCE;
    public static modelRoomDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (modelRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            modelRoomDB.class, "perfect_bmi.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}