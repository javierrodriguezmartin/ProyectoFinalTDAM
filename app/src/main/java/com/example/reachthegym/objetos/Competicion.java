package com.example.reachthegym.objetos;

import android.net.Uri;

import java.util.ArrayList;

public class Competicion {

    String id_competicion,nombre_ejercicio1,nombre_ejercicio2,descripcion,nombre;
    Uri img_url;
    private ArrayList<String> lista_participantes;


    public Competicion(){
        this.id_competicion = "";
        this.nombre_ejercicio1 = "";
        this.nombre_ejercicio2 = "";
        this.descripcion = "";
        this.img_url = null;
    }

    public Competicion(String id_competicion, String nombre_ejercicio1, String nombre_ejercicio2, String descripcion,String nombre) {
        this.id_competicion = id_competicion;
        this.nombre_ejercicio1 = nombre_ejercicio1;
        this.nombre_ejercicio2 = nombre_ejercicio2;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_competicion() {
        return id_competicion;
    }

    public void setId_competicion(String id_competicion) {
        this.id_competicion = id_competicion;
    }

    public String getNombre_ejercicio1() {
        return nombre_ejercicio1;
    }

    public void setNombre_ejercicio1(String nombre_ejercicio1) {
        this.nombre_ejercicio1 = nombre_ejercicio1;
    }

    public String getNombre_ejercicio2() {
        return nombre_ejercicio2;
    }

    public void setNombre_ejercicio2(String nombre_ejercicio2) {
        this.nombre_ejercicio2 = nombre_ejercicio2;
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

    public ArrayList<String> getLista_participantes() {
        return lista_participantes;
    }

    public void setLista_participantes(ArrayList<String> lista_participantes) {
        this.lista_participantes = lista_participantes;
    }
}
