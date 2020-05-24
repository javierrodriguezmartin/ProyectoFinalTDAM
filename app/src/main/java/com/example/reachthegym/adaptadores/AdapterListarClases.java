package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.VerClase;
import com.example.reachthegym.objetos.ClaseGimnasio;
import com.example.reachthegym.objetos.Ejercicio;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListarClases extends RecyclerView.Adapter<AdapterListarClases.ViewHolder> {


    private ArrayList<ClaseGimnasio> lista_clases = new ArrayList<>();
    private ArrayList<ClaseGimnasio> lista_filtro = new ArrayList<>();
    private Context mContext;
    private StorageReference sto;
    private DatabaseReference ref;

    public AdapterListarClases(ArrayList<ClaseGimnasio> lista, Context mContext) {
        this.mContext = mContext;
        this.lista_clases = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clase, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        SharedPreferences prefs = mContext.getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        final String id_usuario = prefs.getString("id_usuario","");

        final ClaseGimnasio pojo_clase = lista_clases.get(position);

        int actual,max;

        final ArrayList<String> lista_participantes = pojo_clase.getClientes_apuntados();

        if (lista_participantes.size()==1){
            actual = 0;
        }else{
            actual = lista_participantes.size()-1;
        }

        max = pojo_clase.getCapacidad_maxima();
        String comparacion_capacidad = actual+" / "+max;

        holder.nombreClaseListar.setText(pojo_clase.getNombre());
        holder.clienteApuntadosListar.setText(comparacion_capacidad);

        sto.child("centro").child("imagenes").child("imagenes_clase").child(pojo_clase.getId_clase()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri).into(holder.imagenListarClase);
            }
        });


        holder.imagenListarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerClase verClase = VerClase.newInstance(pojo_clase.getId_clase(),"");
                AppCompatActivity activity = (AppCompatActivity)holder.itemView.getContext();
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,verClase).addToBackStack(null).commit();


            }
        });

        holder.asistirClasesListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lista_participantes.size()==1){
                    lista_participantes.add(id_usuario);
                    pojo_clase.setClientes_apuntados(lista_participantes);
                    ref.child("centro").child("clases").child(pojo_clase.getId_clase()).setValue(pojo_clase);
                    Toast.makeText(mContext, "Te has apuntado a la clase correctamente", Toast.LENGTH_SHORT).show();

                }else{
                    if (lista_participantes.size()>21){
                        Toast.makeText(mContext, "La clase está completa", Toast.LENGTH_SHORT).show();
                    }else if(estaEnLista(lista_participantes,id_usuario)){
                        Toast.makeText(mContext, "Ya estás apuntado a esta clase", Toast.LENGTH_SHORT).show();
                    }else{
                        lista_participantes.add(id_usuario);
                        pojo_clase.setClientes_apuntados(lista_participantes);
                        ref.child("centro").child("clases").child(pojo_clase.getId_clase()).setValue(pojo_clase);
                        Toast.makeText(mContext, "Te has apuntado a la clase correctamente", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return lista_clases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imagen_listar_clase)
        ImageView imagenListarClase;
        @BindView(R.id.nombre_clase_listar)
        TextView nombreClaseListar;
        @BindView(R.id.cliente_apuntados_listar)
        TextView clienteApuntadosListar;
        @BindView(R.id.huecos_clase_listar)
        TextView huecosClaseListar;
        @BindView(R.id.asistir_clases_listar)
        Button asistirClasesListar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }

    }

    public boolean estaEnLista(ArrayList<String> lista, String id){
        boolean res=false;

        for (String item: lista){
            if (item.equalsIgnoreCase(id)){
                res = true;
            }
        }

        return res;
    }

    public void filtro(String texto){

        if (texto.length() == 0){
            lista_clases.addAll(lista_filtro);
        }

    }

    public void filtrar(ArrayList<ClaseGimnasio> filtroClases){
        this.lista_clases = filtroClases;
        notifyDataSetChanged();
    }

}
