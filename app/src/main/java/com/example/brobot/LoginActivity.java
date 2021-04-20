package com.example.brobot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView signup;
    Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences sharedpreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);

        if (sharedpreferences.getBoolean("DarkMode",false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        assignViews();


    }

    private void assignViews(){

        signup=findViewById(R.id.logintvSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn=findViewById(R.id.loginbtnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,chatActivity.class);
                startActivity(intent);
            }
        });


    }
}
