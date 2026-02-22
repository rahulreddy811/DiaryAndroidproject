package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.practice.databinding.ActivityMain2Binding;
import com.example.practice.userdb.UserEntity;
import com.example.practice.userdb.UserViewModel;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setSignupClick(this);
    }

    public void signup(){

        String Username = binding.username.getText().toString().trim();
        String Password = binding.password.getText().toString().trim();

        if (Username.isEmpty() || Password.isEmpty()){
            Toast.makeText(this, "Enter Username and Password", Toast.LENGTH_SHORT).show();
            return;
        }


        viewModel.getUser(Username).observe(this,user -> {

            if (user != null){
                Toast.makeText(this, "User already Exists", Toast.LENGTH_SHORT).show();
            } else  {

                UserEntity users = new UserEntity(Username,Password);
                viewModel.adduser(users);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}