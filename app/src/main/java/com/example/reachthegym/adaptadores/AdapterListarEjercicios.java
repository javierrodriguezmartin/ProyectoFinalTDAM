package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Ejercicio;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class AdapterListarEjercicios extends RecyclerView.Adapter<AdapterListarEjercicios.ViewHolder> {
    private ArrayList<Ejercicio> lista_ejercicios;
    private Context mContext;
    private DatabaseReference ref;
    private StorageReference sto;

    public AdapterListarEjercicios(ArrayList<Ejercicio> lista, Context mContext) {
        this.mContext = mContext;
        this.lista_ejercicios = lista;
    }


    @NonNull
    @Override
    public AdapterListarEjercicios.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ejercicio, parent, false);
        AdapterListarEjercicios.ViewHolder viewHolder = new AdapterListarEjercicios.ViewHolder(v);
        ButterKnife.bind(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListarEjercicios.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lista_ejercicios.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }
    }
}
