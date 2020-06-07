package com.example.reachthegym.objetos;

import android.net.Uri;

public class Ejercicio {

    private String id_ejercicio,nombre,zona,objetivo,descripcion,id_empleado,series,repeticiones;
    private Uri img_url;


    public Ejercicio() {
        this.id_ejercicio = "";
        this.zona = "";
        this.objetivo = "";
        this.descripcion = "";
        this.id_empleado = "";
        this.repeticiones="";
        this.series="";

    }

    public Ejercicio(String id_ejercicio,String nombre, String zona, String objetivo, String descripcion,String series,String repeticiones) {
        this.id_ejercicio = id_ejercicio;
        this.nombre = nombre;
        this.zona = zona;
        this.objetivo = objetivo;
        this.descripcion = descripcion;
        this.id_empleado = id_empleado;
        this.series=series;
        this.repeticiones=repeticiones;

    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(String id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripción(String descripcion) {
        this.descripcion = descripcion;
    }

    public Uri getImg_url() {
        return img_url;
    }

    public void setImg_url(Uri img_url) {
        this.img_url = img_url;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }
}
