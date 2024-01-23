package com.example.marketplace.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.marketplace.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreenApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_app);
    }

    public void signOut(View view){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent  intent = new Intent(this, MainScreen.class);
        this.startActivity(intent);
        finish();

    }

    public void goToAdd(View view){
        Intent intent = new Intent(this, AditionScreen.class);
        this.startActivity(intent);

    }

}