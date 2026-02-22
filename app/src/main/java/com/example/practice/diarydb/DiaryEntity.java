package com.example.practice.diarydb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DiaryRecords")
public class DiaryEntity {

    @PrimaryKey(autoGenerate = true)
    int id;
    String Title;
    String Notes;
    public long updatedAt;

    public DiaryEntity() {
    }

    public DiaryEntity(String title, String notes) {
        Title = title;
        Notes = notes;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }


}
