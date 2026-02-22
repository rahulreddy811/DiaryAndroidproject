package com.example.practice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import com.example.practice.databinding.ActivityCreatenoteBinding;
import com.example.practice.diarydb.DiaryEntity;
import com.example.practice.diarydb.DiaryViewModel;

public class CreateNote extends AppCompatActivity {

    private ActivityCreatenoteBinding binding;
    private DiaryViewModel viewmodel;
    private DiaryEntity diaryEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_createnote);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewmodel = new ViewModelProvider(this).get(DiaryViewModel.class);

        binding.setCreatenote(this);
        binding.setDiaryentry(new DiaryEntity("",""));
        binding.setLifecycleOwner(this);
    }

    public void onClickSave(View view){
        diaryEntity = binding.getDiaryentry();
        viewmodel.insert(diaryEntity);


        SharedPreferences sharedPreferences = getSharedPreferences("new_prefs",MODE_PRIVATE);
        int counter = sharedPreferences.getInt("counter", 0);
        counter++;
        sharedPreferences.edit()
                        .putInt("counter",counter)
                        .apply();

        SharedPreferences sharedPreferences1 = getSharedPreferences("new_prefs1",MODE_PRIVATE);
        int points = sharedPreferences1.getInt("points",0);
        points += 10;
        sharedPreferences1.edit()
                        .putInt("points",points)
                        .apply();
        finish();
    }
}