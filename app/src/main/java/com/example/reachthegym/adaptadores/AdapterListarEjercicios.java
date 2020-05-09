package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.objetos.Ejercicio;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterListarEjercicios extends RecyclerView.Adapter<AdapterListarEjercicios.ViewHolder> {
    private ArrayList<Ejercicio> lista_ejercicios = new ArrayList<>();
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListarEjercicios.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
