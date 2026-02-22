package com.example.practice.userdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsersDao {

    @Insert
    void insert(UserEntity user);

    @Delete
    void delete(UserEntity user);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<UserEntity> getusernamebyusername(String username);
}
