package com.example.reachthegym.objetos;

import android.net.Uri;

public class Ejercicio {

    private String id_ejercicio,nombre,zona,objetivo,descripción;
    private int series,repeticiones,minutos,segundos;
    private Uri img_url;


    public Ejercicio() {
        this.id_ejercicio = "";
        this.zona = "";
        this.objetivo = "";
        this.descripción = "";
        this.series = 0;
        this.repeticiones = 0;
        this.minutos = 0;
        this.segundos = 0;
    }

    public Ejercicio(String id_ejercicio,String nombre, String zona, String objetivo, String descripción,int series, int repeticiones,int minutos,int segundos) {
        this.id_ejercicio = id_ejercicio;
        this.nombre = nombre;
        this.zona = zona;
        this.objetivo = objetivo;
        this.descripción = descripción;
        this.series = series;
        this.repeticiones = repeticiones;
        this.minutos = minutos;
        this.segundos = segundos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
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

    public Uri getImg_url() {
        return img_url;
    }

    public void setImg_url(Uri img_url) {
        this.img_url = img_url;
    }
}
