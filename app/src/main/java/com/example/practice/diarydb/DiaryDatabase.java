package com.example.practice.diarydb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DiaryEntity.class}, version = 1, exportSchema = false)
public abstract class DiaryDatabase extends RoomDatabase {

    private static volatile DiaryDatabase INSTANCE;

    public abstract DiaryDao diaryDao();

    public static DiaryDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (DiaryDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    DiaryDatabase.class,
                                    "app.db"
                            )
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    // After insert → set updatedAt
                                    db.execSQL(
                                            "CREATE TRIGGER diary_set_updated_at_insert " +
                                                    "AFTER INSERT ON DiaryRecords " +
                                                    "BEGIN " +
                                                    "UPDATE DiaryRecords " +
                                                    "SET updatedAt = strftime('%s','now') " +
                                                    "WHERE id = NEW.id; " +
                                                    "END;"
                                    );

                                    // Before update of Title or Notes → change NEW.updatedAt
                                    db.execSQL(
                                            "CREATE TRIGGER diary_set_updated_at_update " +
                                                    "BEFORE UPDATE OF Title, Notes ON DiaryRecords " +
                                                    "BEGIN " +
                                                    "SELECT NEW.updatedAt = strftime('%s','now'); " +
                                                    "END;"
                                    );
                                }
                            })
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}