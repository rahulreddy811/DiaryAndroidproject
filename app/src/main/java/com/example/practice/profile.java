package com.example.practice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class profile extends AppCompatActivity {

    ImageView profileImage;
    TextView count,user, point;
    Button add_btn;

    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {

                            // show selected image
                            Glide.with(this)
                                    .load(uri)
                                    .circleCrop()
                                    .into(profileImage);

                            // save it for profile screen
                            SharedPreferences prefs =
                                    getSharedPreferences("my_prefs", MODE_PRIVATE);

                            prefs.edit()
                                    .putString("profile_image_uri", uri.toString())
                                    .apply();
                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profileImage = findViewById(R.id.imageView5);
        count = findViewById(R.id.textView9);
        user = findViewById(R.id.textView6);
        add_btn = findViewById(R.id.button5);
        point = findViewById(R.id.textView13);

        add_btn.setOnClickListener(v -> {
            galleryLauncher.launch("image/*");
        });


        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String imageUri = prefs.getString("profile_image_uri", null);

        if (imageUri != null) {
            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .circleCrop()
                    .into(profileImage);
        } else {
            Glide.with(this)
                    .load(R.drawable.profile)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .circleCrop()
                    .into(profileImage);
        }


        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs",MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);

        SharedPreferences sharedPreferences1 = getSharedPreferences("new_prefs",MODE_PRIVATE);
        int counter = sharedPreferences1.getInt("counter",0);

        SharedPreferences sharedPreferences2 = getSharedPreferences("new_prefs1",MODE_PRIVATE);
        int points = sharedPreferences2.getInt("points",0);

        user.setText(username);
        count.setText(String.valueOf(counter));
        point.setText(String.valueOf(points));

    }

}