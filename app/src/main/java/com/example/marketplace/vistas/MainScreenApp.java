package com.example.marketplace.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.marketplace.R;
import com.example.marketplace.modelos.Producto;
import com.example.marketplace.vistas.recyclerView.FotosProductosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainScreenApp extends AppCompatActivity {
    private FirebaseDatabase database;
    private ArrayList<Producto> productos;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FotosProductosAdapter adapter;
    private RecyclerView rv_productos;
    private DatabaseReference myRefProductos;
    private EditText edtBuscarProducto;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_app);
        database = FirebaseDatabase.getInstance();
        rv_productos = (RecyclerView) findViewById(R.id.rv_Productos);

        productos = new ArrayList<Producto>();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = user.getEmail().replace(".", "_");
        rellenarRecyclerView();
        edtBuscarProducto = (EditText) findViewById(R.id.edtBuscarProducto);
        edtBuscarProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoABuscar = String.valueOf(edtBuscarProducto.getText());
                if (textoABuscar.isEmpty()){
                    rellenarRecyclerView();
                }else{
                    myRefProductos = database.getReference("Usuarios").child(email);
                    myRefProductos.addValueEventListener(new ValueEventListener() {
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<Producto> productos = new ArrayList<Producto>();
                            for (DataSnapshot keynode : snapshot.getChildren()) {
                                Producto a = keynode.getValue(Producto.class);
                                if (a.getNombre().contains(textoABuscar)) {
                                    productos.add(keynode.getValue(Producto.class));
                                }
                            }
                            adapter.setProductos(productos);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.i("firebase1", String.valueOf(error.toException()));
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//-----------------------------------------------------------

    }

    public void signOut(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, MainScreen.class);
        this.startActivity(intent);
        finish();

    }

    public void goToAdd(View view) {
        Intent intent = new Intent(this, AditionScreen.class);
        this.startActivity(intent);

    }

    //---------------------------------------------------------------------------------
    public void rellenarRecyclerView() {
        adapter = new FotosProductosAdapter(this, productos);
        rv_productos.setAdapter(adapter);
        myRefProductos = database.getReference("Usuarios").child(email);
        myRefProductos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // adapter.getProductos().clear();
                ArrayList<Producto> productos = new ArrayList<Producto>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Producto a = (Producto) dataSnapshot.getValue(Producto.class);
                    productos.add(a);
                }
                adapter.setProductos(productos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("firebase1", String.valueOf(error.toException()));
            }
        });

//-------------------------------------------------------------
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            rv_productos.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            // In portrait
            rv_productos.setLayoutManager(new LinearLayoutManager(this));
        }


    }

}