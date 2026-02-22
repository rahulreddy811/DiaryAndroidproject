package com.example.practice.diarydb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {
    DiaryRepository repo;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        repo = new DiaryRepository(application);
    }

    public void insert(DiaryEntity diary){
        repo.addNote(diary);
    }

    public void delete(DiaryEntity diary){
        repo.deleteNote(diary);
    }

    public LiveData<List<DiaryEntity>> getAllTitle(){
       return repo.getAllNotes();
    }
    public LiveData<String> getContent(String title){
        return repo.getNotes(title);
    }
}
