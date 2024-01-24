package com.example.marketplace.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class MainScreenApp extends AppCompatActivity implements  SearchView.OnQueryTextListener{
    private FirebaseDatabase database;
    private ArrayList<Producto> productos;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FotosProductosAdapter adapter;
    private RecyclerView rv_productos;
    private SearchView sv_Buscador;
    private DatabaseReference myRefProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_app);
        database = FirebaseDatabase.getInstance();
        rv_productos = (RecyclerView) findViewById(R.id.rv_Productos);
        sv_Buscador = (SearchView) findViewById(R.id.sv_Buscador);

        productos = new ArrayList<Producto>();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String email = user.getEmail().replace(".","_");
//-----------------------------------------------------------
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
                adapter.setProductosOld(productos);
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

        sv_Buscador.setOnQueryTextListener(this);

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


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}