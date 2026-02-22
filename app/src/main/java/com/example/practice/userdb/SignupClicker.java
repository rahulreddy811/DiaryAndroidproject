package com.example.practice.userdb;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.practice.MainActivity2;

public class SignupClicker {
    Context context;

    public SignupClicker(Context context) {
        this.context = context;
    }

    public void fragenter(View view){

        Intent intent =  new Intent(context, MainActivity2.class);
        context.startActivity(intent);
    }
}
