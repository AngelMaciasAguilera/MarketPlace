package com.example.marketplace.controladores;

import androidx.annotation.NonNull;

import com.example.marketplace.modelos.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ControladorProducto {
    private String email;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private FirebaseAuth auth;

    public ControladorProducto() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        email = user.getEmail();
        email = email.replace(".","_");
        myRef = firebaseDatabase.getReference("Usuarios").child(email);
    }

    public void insertarProducto(@NonNull Producto producto) {
        myRef.child("Producto: "+producto.getId()).setValue(producto);
    }

    public void eliminarProducto(String idProducto){
        myRef.child("Producto: "+idProducto).removeValue();
    }

    public void actualizarProducto(Producto p){
        eliminarProducto(p.getId());
        insertarProducto(p);
    }

}

