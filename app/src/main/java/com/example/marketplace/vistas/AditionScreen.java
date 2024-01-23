package com.example.marketplace.vistas;

import static com.example.marketplace.controladores.ControladorImagenProducto.NUEVA_IMAGEN;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marketplace.R;
import com.example.marketplace.controladores.ControladorImagenProducto;
import com.example.marketplace.controladores.ControladorProducto;
import com.example.marketplace.modelos.Producto;

public class AditionScreen extends AppCompatActivity {
    private EditText edtIdProducto,edtNombreProducto,edtPrecioProducto;
    private ImageView imgvwProducto;
    private ControladorProducto contrlProducto;
    private ControladorImagenProducto contrlImagenProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adition_screen);
        edtIdProducto = findViewById(R.id.edtAddId);
        edtNombreProducto = findViewById(R.id.edtAddNombre);
        edtPrecioProducto = findViewById(R.id.edtAddPrecio);
        imgvwProducto = findViewById(R.id.imgvwProducto);
        contrlProducto = new ControladorProducto();
        contrlImagenProducto = new ControladorImagenProducto();

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
        contrlProducto.insertarProducto(producto);
        contrlImagenProducto.insertarImagen(imgvwProducto,idProducto,nombreProducto);
        limpiarCampos();

    }

    public void cambiarImagen(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

    private void limpiarCampos(){
        edtIdProducto.getText().clear();
        edtNombreProducto.getText().clear();
        edtPrecioProducto.getText().clear();
        imgvwProducto.setImageResource(R.drawable.defaultimage);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            Uri imgSelected = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgSelected);
                imgvwProducto.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                imgvwProducto.setImageResource(R.drawable.imagenf);
            }
        }
    }


}