package com.example.reachthegym.objetos;

import android.net.Uri;

public class Ejercicio {

    private String id_ejercicio,nombre,zona,objetivo,descripcion;
    private Uri img_url;


    public Ejercicio() {
        this.id_ejercicio = "";
        this.zona = "";
        this.objetivo = "";
        this.descripcion = "";

    }

    public Ejercicio(String id_ejercicio,String nombre, String zona, String objetivo, String descripcion) {
        this.id_ejercicio = id_ejercicio;
        this.nombre = nombre;
        this.zona = zona;
        this.objetivo = objetivo;
        this.descripcion = descripcion;
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
}