package com.example.practice.userdb;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepo {

    private final UsersDao userdao;
    private final Handler handler;
    private final ExecutorService executor;
    private final Application application;

    public UserRepo(Application application) {
        Userdatabase users = Userdatabase.getInstance(application);
        this.userdao = users.getUsersDao();
        this.handler = new Handler(Looper.getMainLooper());
        this.executor = Executors.newSingleThreadExecutor();
        this.application = application;

    }

    public void addUser(UserEntity user){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userdao.insert(user);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(application, "User is created", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void deleteUser(UserEntity user){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userdao.delete(user);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(application, "User is Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public LiveData<UserEntity> getUser(String username){
        return userdao.getusernamebyusername(username);
    }
}
