package com.example.reachthegym.objetos;

import android.net.Uri;

import java.util.ArrayList;

public class EjercicioEmpleado extends Ejercicio  {
    private String id_empleado;
    private Uri img_url;
    private ArrayList<String> clientes_asignados;


    public EjercicioEmpleado() {
        this.id_empleado = "";
    }

    public EjercicioEmpleado(String id_ejercicio, String nombre, String zona, String objetivo, String descripcion, String series, String repeticiones, String id_empleado) {
        super(id_ejercicio, nombre, zona, objetivo, descripcion, series, repeticiones);
        this.id_empleado = id_empleado;
    }


    public ArrayList<String> getClientes_asignados() {
        return clientes_asignados;
    }

    public void setClientes_asignados(ArrayList<String> clientes_asignados) {
        this.clientes_asignados = clientes_asignados;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    @Override
    public Uri getImg_url() {
        return img_url;
    }

    @Override
    public void setImg_url(Uri img_url) {
        this.img_url = img_url;
    }
}
