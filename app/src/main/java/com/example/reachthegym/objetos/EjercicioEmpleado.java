package com.example.reachthegym.objetos;

import android.net.Uri;

public class EjercicioEmpleado extends Ejercicio  {
    private String id_empleado;
    private Uri img_url;


    public EjercicioEmpleado() {
        this.id_empleado = "";
    }

    public EjercicioEmpleado(String id_ejercicio, String nombre, String zona, String objetivo, String descripcion, String id_empleado) {
        super(id_ejercicio, nombre, zona, objetivo, descripcion);
        this.id_empleado = id_empleado;
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
