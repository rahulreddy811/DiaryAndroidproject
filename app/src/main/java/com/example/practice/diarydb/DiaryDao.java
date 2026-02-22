package com.example.practice.diarydb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiaryDao {
    @Insert
    void insert(DiaryEntity diaryEntity);
    @Delete
    void delete(DiaryEntity diaryEntity);
    @Query("SELECT * FROM DiaryRecords ORDER BY updatedAt DESC")
    LiveData<List<DiaryEntity>> getAllNotes();
    @Query("SELECT Notes FROM DiaryRecords WHERE Title = :Title")
    LiveData<String>getContentByTitle(String Title);

}
