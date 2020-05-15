package com.example.reachthegym.objetos;

import android.net.Uri;

public class Competicion {

    String id_competicion,id_participantes,id_ejercicios,descripcion;
    Uri img_url;

    public Competicion() {
        this.id_competicion = "";
        this.id_participantes = "";
        this.id_ejercicios = "";
        this.descripcion = "";
    }

    public Competicion(String id_competicion, String id_participantes, String id_ejercicios, String descripcion) {
        this.id_competicion = id_competicion;
        this.id_participantes = id_participantes;
        this.id_ejercicios = id_ejercicios;
        this.descripcion = descripcion;
    }

    public String getId_competicion() {
        return id_competicion;
    }

    public void setId_competicion(String id_competicion) {
        this.id_competicion = id_competicion;
    }

    public String getId_participantes() {
        return id_participantes;
    }

    public void setId_participantes(String id_participantes) {
        this.id_participantes = id_participantes;
    }

    public String getId_ejercicios() {
        return id_ejercicios;
    }

    public void setId_ejercicios(String id_ejercicios) {
        this.id_ejercicios = id_ejercicios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Uri getImg_url() {
        return img_url;
    }

    public void setImg_url(Uri img_url) {
        this.img_url = img_url;
    }
}
