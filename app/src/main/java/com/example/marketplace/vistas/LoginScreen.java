package com.example.marketplace.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marketplace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtLoginEmail;
    private EditText edtLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);


    }


    public void cancelarLogueo(View view) {
        Intent intent = new Intent(this, MainScreen.class);
        this.startActivity(intent);
    }

    public void entrar(View view) {
        String email = String.valueOf(edtLoginEmail.getText());
        String password = String.valueOf(edtLoginPassword.getText());
        if (email.isEmpty() == false && password.isEmpty() == false) {
            if (email.contains("@")) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("firebase1", "logeo correcto");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginScreen.this, MainScreenApp.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("firebase1", "no ha funcionado el logeo", task.getException());
                                    Toast.makeText(LoginScreen.this, "El usuario o contraseña introducidos no pertenecen a ningun usuario",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "El email no es valido debe contener un @", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes introducir una contraseña y una contraseña", Toast.LENGTH_SHORT).show();
        }
    }
}



