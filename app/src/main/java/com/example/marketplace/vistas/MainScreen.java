package com.example.marketplace.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.marketplace.R;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    public void loginScreen(View view){
        Intent intent = new Intent(this, LoginScreen.class);
        this.startActivity(intent);
    }

    public void signinScreen(View view){
        Intent intent = new Intent(this, SingInScreen.class);
        this.startActivity(intent);
    }
}