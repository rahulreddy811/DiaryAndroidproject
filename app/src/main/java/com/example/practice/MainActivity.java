package com.example.practice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.practice.databinding.ActivityMainBinding;
import com.example.practice.userdb.SignupClicker;
import com.example.practice.userdb.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SignupClicker clicker;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clicker = new SignupClicker(this);
        binding.setSignuplink(clicker);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setLogin(this);

    }

    public void onLoginClick(){

        String username = binding.editTextText.getText().toString().trim();
        String password = binding.editTextTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Enter Username and Password ", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.getUser(username).observe(this,user -> {
            
            if (user == null){
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            } else if (user.getUsername().equals(username)&& user.getPassword().equals(password)) {
                Intent intent = new Intent(this,NotesPage.class);
                intent.putExtra("Username",username);
                intent.putExtra("Password",password);
                startActivity(intent);

                SharedPreferences sharedPreferences = getSharedPreferences("my_prefs",MODE_PRIVATE);
                sharedPreferences.edit()
                                .putString("username",username)
                                .apply();

                finish();
            }else {

                Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show();
            }
        });

    }

}