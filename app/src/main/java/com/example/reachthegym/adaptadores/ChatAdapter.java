package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Mensaje;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    private ArrayList<Mensaje> lista_mensajes ;
    private Context mContext;
    private String auxiliar;


    public ChatAdapter(ArrayList<Mensaje> lista_mensajes, Context mContext, String auxiliar) {
        this.lista_mensajes = lista_mensajes;
        this.mContext = mContext;
        this.auxiliar = auxiliar;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_char, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final Mensaje pojo_mensaje = lista_mensajes.get(position);

        if (auxiliar.equalsIgnoreCase(pojo_mensaje.getId_usuario1())){

            holder.nombreMensajeMio.setText(pojo_mensaje.getNombre());
            holder.contenidoMensajeMio.setText(pojo_mensaje.getContenido_mensaje());
            holder.cartaMia.setVisibility(View.VISIBLE);

            holder.cartaExtranjera.setVisibility(View.GONE);


        }else{

            holder.contenidoMensajeExtranjero.setText(pojo_mensaje.getContenido_mensaje());
            holder.nombreMensajeExtranjero.setText(pojo_mensaje.getNombre());

            holder.cartaMia.setVisibility(View.GONE);

            holder.cartaExtranjera.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        return lista_mensajes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nombre_mensaje_extranjero)
        TextView nombreMensajeExtranjero;
        @BindView(R.id.contenido_mensaje_extranjero)
        TextView contenidoMensajeExtranjero;
        @BindView(R.id.carta_extranjera)
        CardView cartaExtranjera;
        @BindView(R.id.nombre_mensaje_mio)
        TextView nombreMensajeMio;
        @BindView(R.id.contenido_mensaje_mio)
        TextView contenidoMensajeMio;
        @BindView(R.id.carta_mia)
        CardView cartaMia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }
    }
}
