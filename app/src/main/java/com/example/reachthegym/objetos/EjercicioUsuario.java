package com.example.reachthegym.objetos;

import android.net.Uri;

public class EjercicioUsuario {

    private String id_ejercicio,id_usuario,nombre,zona,objetivo,descripcion;
    private int series,repeticiones,minutos,segundos;
    private Uri img_url;

    public EjercicioUsuario() {
        this.id_ejercicio = "";
        this.nombre = "";
        this.zona = "";
        this.objetivo = "";
        this.descripcion  = "";
        this.series = 0;
        this.repeticiones = 0;
        this.minutos = 0;
        this.segundos = 0;
    }

    public EjercicioUsuario(String id_ejercicio,String id_usuario, String nombre, String zona, String objetivo, String descripcion) {
        this.id_ejercicio = id_ejercicio;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.zona = zona;
        this.objetivo = objetivo;
        this.descripcion = descripcion;
        this.series = 0;
        this.repeticiones = 0;
        this.minutos = 0;
        this.segundos = 0;
    }

    public String getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(String id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public Uri getImg_url() {
        return img_url;
    }

    public void setImg_url(Uri img_url) {
        this.img_url = img_url;
    }
}
