package com.example.marketplace.vistas.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;
import com.example.marketplace.controladores.ControladorImagenProducto;
import com.example.marketplace.modelos.Producto;

import java.util.ArrayList;

public class FotosProductosAdapter extends RecyclerView.Adapter<RvProductosHolder> {
    private Context contexto;
    private ArrayList<Producto> productos;
    private LayoutInflater inflate;

    private ControladorImagenProducto cip;

    public FotosProductosAdapter(Context contexto, ArrayList<Producto> productos) {
        this.contexto = contexto;
        this.productos = productos;
        inflate = LayoutInflater.from(this.contexto);
        cip = new ControladorImagenProducto();
    }




    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public LayoutInflater getInflate() {
        return inflate;
    }

    public void setInflate(LayoutInflater inflate) {
        this.inflate = inflate;
    }

    @NonNull
    @Override
    public RvProductosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoView = inflate.inflate(R.layout.item_rv_producto, parent, false);
        RvProductosHolder rph = new RvProductosHolder(productoView, this);
        return rph;
    }

    @Override
    public void onBindViewHolder(@NonNull RvProductosHolder holder, int position) {
        Producto p = this.getProductos().get(position);
        //----------------------------------------------------------------------
        holder.getTvwNombreProducto().setText("Nombre: " + p.getNombre());
        holder.getTvwPrecioProducto().setText("Precio: " + String.valueOf(p.getPrecio()));
        //---------------------------------------------------------------------
        ImageView mi_imagen = holder.getImgrvProducto();
        cip.descargarImagen(p.getId(), p.getNombre(), mi_imagen);
    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }
}
