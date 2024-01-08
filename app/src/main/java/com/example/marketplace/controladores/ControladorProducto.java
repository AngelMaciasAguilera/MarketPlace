package com.example.marketplace.controladores;

import androidx.annotation.NonNull;

import com.example.marketplace.modelos.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControladorProducto {
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private FirebaseAuth auth;
    public ControladorProducto() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Usuarios").child(user.getEmail());
        auth = FirebaseAuth.getInstance();
    }

    public void insertarContenidoFoto(@NonNull Producto producto){
        myRef.child(producto.getId()).setValue(producto);
    }

}
