package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.VerEjercicio;
import com.example.reachthegym.objetos.Ejercicio;
import com.example.reachthegym.objetos.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListarEjercicios extends RecyclerView.Adapter<AdapterListarEjercicios.ViewHolder> {

    private ArrayList<Ejercicio> lista_ejercicios;
    private Context mContext;
    private DatabaseReference ref;
    private StorageReference sto;
    private Uri img_url;


    public AdapterListarEjercicios(ArrayList<Ejercicio> lista, Context mContext) {
        this.mContext = mContext;
        this.lista_ejercicios = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ejercicio, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        Ejercicio pojo_ejercicio = lista_ejercicios.get(position);
        holder.nombreEjercicioListar.setText(pojo_ejercicio.getNombre());
        holder.objetivoEjercicioListar.setText(pojo_ejercicio.getObjetivo());
        holder.zonaEjercicioObjetivo.setText(pojo_ejercicio.getZona());

        Glide.with(mContext).load(sto.child("centro").child("imagenes").child(pojo_ejercicio.getId_ejercicio()).getDownloadUrl()).into(holder.imageView2);
        Toast.makeText(mContext,sto.child("centro").child("imagenes").child("imagenes_ejercicios").child(pojo_ejercicio.getId_ejercicio()).getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)holder.itemView.getContext();
                VerEjercicio verEjercicio = VerEjercicio.newInstance(pojo_ejercicio.getId_ejercicio(),"");
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,verEjercicio).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return lista_ejercicios.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView2)
        CircleImageView imageView2;
        @BindView(R.id.nom_eje_listar)
        TextView nomEjeListar;
        @BindView(R.id.obje_eje_listar)
        TextView objeEjeListar;
        @BindView(R.id.desc_eje_listar)
        TextView descEjeListar;
        @BindView(R.id.nombre_ejercicio_listar)
        TextView nombreEjercicioListar;
        @BindView(R.id.objetivo_ejercicio_listar)
        TextView objetivoEjercicioListar;
        @BindView(R.id.zona_ejercicio_objetivo)
        TextView zonaEjercicioObjetivo;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
    }

    public void filtrar(ArrayList<Ejercicio> filtroEjercicio){
        this.lista_ejercicios = filtroEjercicio;
        notifyDataSetChanged();
    }
}
