package com.example.practice.userdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {
  private UserRepo repo;


    public UserViewModel(@NonNull Application application) {
        super(application);
        this.repo = new UserRepo(application);
    }

    public LiveData<UserEntity> getUser(String username){
        return repo.getUser(username);
    }

    public void adduser(UserEntity user){
       repo.addUser(user);
    }

    public void deleteuser(UserEntity user){
        repo.deleteUser(user);
    }
}
