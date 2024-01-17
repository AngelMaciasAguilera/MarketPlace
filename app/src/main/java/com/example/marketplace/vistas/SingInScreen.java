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

public class SingInScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtNombre;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_screen);
        mAuth = FirebaseAuth.getInstance();
        edtNombre = findViewById(R.id.edtNombre);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
    }

    public void cancelar(View view) {
        Intent intent = new Intent(this, MainScreen.class);
        this.startActivity(intent);
    }

    public void registrarUsuario(View view) {
        if (insertarUsuario() == true) {
            limpiarCampos();
            Intent intent = new Intent(this, MainScreen.class);
            this.startActivity(intent);
        }

    }

    private boolean insertarUsuario() {
        boolean registroCorrecto = true;
        boolean camposVacios = false;
        camposVacios = String.valueOf(edtNombre.getText()).isEmpty() && String.valueOf(edtEmail.getText()).isEmpty() &&
                String.valueOf(edtPassword.getText()).isEmpty() && String.valueOf(edtPasswordConfirm.getText()).isEmpty();
        //Obtengo los string de las contraseñas para compararlas
        String password = String.valueOf(edtPassword.getText());
        String passwordCon = String.valueOf(edtPasswordConfirm.getText()
        );


        if (camposVacios) {
            Toast.makeText(this, "Debes rellenar todos los campos del registro", Toast.LENGTH_LONG).show();
            registroCorrecto = false;
        } else {
            if (String.valueOf(edtEmail.getText()).contains("@") == false) {
                edtEmail.setError("El email debe contener un @");
                registroCorrecto = false;

            } else {
                if (String.valueOf(edtPassword.getText()).length() < 6) {
                    edtPassword.getText().clear();
                    edtPasswordConfirm.getText().clear();
                    edtPassword.setError("Una contraseña debe tener como minimo 6 caracteres");
                    registroCorrecto = false;
                } else {
                    if (!password.equals(passwordCon)) {
                        edtPasswordConfirm.getText().clear();
                        edtPassword.getText().clear();
                        edtPasswordConfirm.setError("Las contraseñas no son iguales");
                        registroCorrecto = false;

                    } else {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(String.valueOf(edtEmail.getText()), String.valueOf(edtPassword.getText()))
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("firebase1", "logeo correcto");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(SingInScreen.this, "Autenticación correcta.",
                                                    Toast.LENGTH_LONG).show();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("firebase1", "no ha funcionado el logeo", task.getException());
                                            Toast.makeText(SingInScreen.this, "Autenticación incorrecta.",
                                                    Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });
                    }
                }
            }

        }
        return registroCorrecto;
    }

    private void limpiarCampos() {
        edtEmail.getText().clear();
        edtNombre.getText().clear();
        edtPassword.getText().clear();
        edtPasswordConfirm.getText().clear();
    }

}
