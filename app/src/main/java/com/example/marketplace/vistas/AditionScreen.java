package com.example.marketplace.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.marketplace.R;
import com.example.marketplace.controladores.ControladorProducto;
import com.example.marketplace.modelos.Producto;

public class AditionScreen extends AppCompatActivity {
    private EditText edtIdProducto,edtNombreProducto,edtPrecioProducto;
    private ImageView imgvwProducto;
    private ControladorProducto contrlProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adition_screen);
        edtIdProducto = findViewById(R.id.edtAddId);
        edtNombreProducto = findViewById(R.id.edtAddNombre);
        edtPrecioProducto = findViewById(R.id.edtAddPrecio);
        imgvwProducto = findViewById(R.id.imgvwProducto);
        contrlProducto = new ControladorProducto();

    }

    public void cancelAdition(View view){
        Intent intent = new Intent(this, MainScreenApp.class);
        this.startActivity(intent);
    }

    public void addProduct(View view){
        String idProducto = String.valueOf(edtIdProducto.getText());
        String nombreProducto = String.valueOf(edtNombreProducto.getText());
        double precio = Double.parseDouble(String.valueOf(edtPrecioProducto.getText()));
        Producto producto = new Producto(idProducto,nombreProducto,precio);

    }


}