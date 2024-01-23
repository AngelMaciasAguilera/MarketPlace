package com.example.marketplace.controladores;

import static com.example.marketplace.controladores.ConversorImagenProducto.bytes_to_bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.marketplace.R;
import com.example.marketplace.modelos.Producto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ControladorImagenProducto {
    private FirebaseStorage storage;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private StorageReference storageRef;

    public static final int NUEVA_IMAGEN = 1;

    public ControladorImagenProducto() {
        myRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        storageRef = storage.getReference();
    }

    public void insertarImagen(ImageView imagenProducto, String idProducto,String nombreProducto){
        //-------------------------convierto el imgvwProducto a Bitmap----------------------------------
        imagenProducto.setDrawingCacheEnabled(true);
        imagenProducto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imagenProducto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();
        StorageReference imgFotoRef = storageRef.child(user.getEmail()+"/"+idProducto+"/"+ nombreProducto+".png");
        UploadTask uploadTask = imgFotoRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("firebase1","la foto no se ha subido correctamente");

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i("firebase1","la foto se ha subido correctamente");
                // ...
            }
        });

    }
    public void descargarImagen(String idProducto,String nombreProducto,ImageView imagenDescargada){
        StorageReference islandRef = storageRef.child(user.getEmail()+"/"+idProducto+"/"+ nombreProducto+".png");

        final long tam_foto = 10240 * 1024;
        islandRef.getBytes(tam_foto).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.i("firebase1","la foto se ha descargado correctamente");
                Bitmap imagenbitmapdescargada = bytes_to_bitmap(bytes, 200, 200);
                imagenDescargada.setImageBitmap(imagenbitmapdescargada);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                byte[] bytes = null;
                Log.i("firebase1","la foto no se pudo descargar");
                int errorCode = ((StorageException) exception).getErrorCode();
                String errorMessage = exception.getMessage();
                Log.i("firebase1",errorMessage);
                Log.i("firebase1","error code" + String.valueOf(errorCode));
                imagenDescargada.setImageResource(R.drawable.imgerror);
            }
        });
    }

    //--------------------------------------------------------------------------------
    public void borrarFoto(String idProducto, String nombreProducto) {
        StorageReference islandRef = storageRef.child(user.getEmail()+"/"+idProducto+"/"+ nombreProducto+".png");
        // Delete the file
        islandRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                Log.i("firebase1","Foto borrada correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("firebase1","la foto no se pudo borrar ya que se ha eliminado o no existe");
            }
        });
    }


}
