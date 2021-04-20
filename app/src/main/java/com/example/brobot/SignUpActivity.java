package com.example.brobot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        assignViews();


    }

    private void assignViews(){

        signIn=findViewById(R.id.signuptvSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
