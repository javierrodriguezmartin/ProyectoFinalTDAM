package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.reachthegym.R;
import com.example.reachthegym.objetos.EjercicioEmpleado;

import java.util.ArrayList;
import java.util.List;

public class ListEjerciciosAdapter extends ArrayAdapter<EjercicioEmpleado>{


    public ListEjerciciosAdapter(Context context,ArrayList<EjercicioEmpleado> lista){
        super(context,0,lista);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        EjercicioEmpleado ejercicioEmpleado = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview_ejercicios,parent,false);
        }

        TextView nombre_ejercicio = (TextView)convertView.findViewById(R.id.nombre_ejercicio_listview);

        nombre_ejercicio.setText(ejercicioEmpleado.getNombre());

        return convertView;
    }


}
