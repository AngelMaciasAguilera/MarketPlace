package com.example.marketplace.vistas;

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
import com.example.marketplace.controladores.ConversorImagenProducto;
import com.example.marketplace.modelos.Producto;
import com.example.marketplace.vistas.recyclerView.RvProductosHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class ManagementProductScreen extends AppCompatActivity {


    private ControladorProducto cp;
    private ControladorImagenProducto cip;
    private EditText edt_MIdProducto;
    private EditText edt_MNombreProducto;

    private EditText edt_MPrecioProducto;

    private ImageView imgMProducto;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;
    private Producto p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_product_screen);
        imgMProducto = (ImageView) findViewById(R.id.imgMProducto);
        Intent intent = getIntent();
        if (intent != null) {
            p = (Producto) intent.getSerializableExtra(RvProductosHolder.EXTRA_DETALLES_PRODUCTO);
            byte[] fotobinaria = (byte[]) intent.getByteArrayExtra(RvProductosHolder.EXTRA_IMAGEN2_PRODUCTO);
            Bitmap fotobitmap = ConversorImagenProducto.bytes_to_bitmap(fotobinaria, 200, 200);
            imgMProducto.setImageBitmap(fotobitmap);
        } else {
            p = new Producto();
        }
        //----------------------------------------------------------------
        edt_MIdProducto = (EditText) findViewById(R.id.edtM_idProducto);
        edt_MNombreProducto = (EditText) findViewById(R.id.edtM_NombreProducto);
        edt_MPrecioProducto = (EditText) findViewById(R.id.edtM_PrecioProducto);
        //----------------------------------------------------------------
        edt_MIdProducto.setText(p.getId());
        edt_MNombreProducto.setText(p.getNombre());
        edt_MPrecioProducto.setText(String.valueOf(p.getPrecio()));
        cp = new ControladorProducto();
        cip = new ControladorImagenProducto();
    }


    public void deleteProduct(View view) {
        cp.eliminarProducto(p.getId());
        cip.borrarFoto(p.getId(), p.getNombre());
        this.startActivity(new Intent(this, MainScreenApp.class));
    }


    public void cancelProductManagement(View view) {
        Intent intent = new Intent(this, MainScreenApp.class);
        this.startActivity(intent);

    }

    public void changeImage(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

    public void updateProduct(View view) {
        String id = String.valueOf(edt_MIdProducto.getText());
        String nombre = String.valueOf(edt_MNombreProducto.getText());
        double precio = Double.valueOf(String.valueOf(edt_MPrecioProducto.getText()));
        Producto p1 = new Producto(id, nombre, precio);
        cp.actualizarProducto(p1);
        if (imagen_seleccionada != null) {
            cip.borrarFoto(p.getId(), p.getNombre());
            cip.insertarImagen(imgMProducto, p.getId(),nombre);
        }

        this.startActivity(new Intent(this, MainScreenApp.class));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                imgMProducto.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
