package com.example.reachthegym.objetos;

import java.util.ArrayList;

public class Rutina {
    private String id_rutina,nombre;
    private ArrayList<EjercicioEmpleado> lista_ejercicios;

    public Rutina(){
        this.id_rutina = "";
        this.nombre = "";
        this.lista_ejercicios = null;
    }

    public Rutina(String id_rutina, String nombre, ArrayList<EjercicioEmpleado> lista_ejercicios) {
        this.id_rutina = id_rutina;
        this.nombre = nombre;
        this.lista_ejercicios = lista_ejercicios;
    }

    public String getId_rutina() {
        return id_rutina;
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
