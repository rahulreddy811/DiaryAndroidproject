package com.example.practice.userdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class},version = 1)
public abstract class Userdatabase extends RoomDatabase {

    public abstract UsersDao getUsersDao();
    public static Userdatabase db_instance;

    public static synchronized Userdatabase getInstance(Context context){
        if (db_instance == null){
            db_instance = Room.databaseBuilder(context.getApplicationContext(),Userdatabase.class,"User_db").fallbackToDestructiveMigration().build();
        }
        return db_instance;
    }

}
