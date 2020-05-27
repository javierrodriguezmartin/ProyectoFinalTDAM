package com.example.reachthegym.objetos;

import java.util.ArrayList;

public class Rutina {
    private String id_rutina,nombre,creador;
    private ArrayList<EjercicioEmpleado> lista_ejercicios;
    private boolean hecha;

    public Rutina(){
        this.id_rutina = "";
        this.nombre = "";
        this.lista_ejercicios = null;
        this.creador = "";
        this.hecha = false;
    }

    public Rutina(String id_rutina, String nombre, ArrayList<EjercicioEmpleado> lista_ejercicios,String creador) {
        this.id_rutina = id_rutina;
        this.nombre = nombre;
        this.lista_ejercicios = lista_ejercicios;
        this.creador = creador;
        this.hecha = false;
    }

    public boolean isHecha() {
        return hecha;
    }

    public void setHecha(boolean hecha) {
        this.hecha = hecha;
    }

    public String getId_rutina() {
        return id_rutina;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public void setId_rutina(String id_rutina) {
        this.id_rutina = id_rutina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<EjercicioEmpleado> getLista_ejercicios() {
        return lista_ejercicios;
    }

    public void setLista_ejercicios(ArrayList<EjercicioEmpleado> lista_ejercicios) {
        this.lista_ejercicios = lista_ejercicios;
    }
}
