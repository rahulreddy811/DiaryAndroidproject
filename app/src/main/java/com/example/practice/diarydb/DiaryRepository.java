package com.example.practice.diarydb;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiaryRepository {

    private final DiaryDao diarydao;
    private final Handler handler;
    private final ExecutorService executor;
    private final Application application;

    public DiaryRepository(Application application) {
        DiaryDatabase diary = com.example.practice.diarydb.DiaryDatabase.getInstance(application);
        this.diarydao = diary.diaryDao();
        this.handler = new Handler(Looper.getMainLooper());
        this.executor = Executors.newSingleThreadExecutor();
        this.application = application;
    }

    public void addNote(DiaryEntity diary){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                diarydao.insert(diary);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(application, "Note Has Been Inserted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void deleteNote(DiaryEntity diary){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                diarydao.delete(diary);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(application, "Note Has Been Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public LiveData<List<DiaryEntity>> getAllNotes(){
        return diarydao.getAllNotes();
    }

    public LiveData<String>getNotes(String title){

        return diarydao.getContentByTitle(title);
    }
}
