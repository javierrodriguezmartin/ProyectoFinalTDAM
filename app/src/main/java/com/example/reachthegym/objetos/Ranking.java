package com.example.reachthegym.objetos;

import java.util.ArrayList;

public class Ranking {

    String id_competicion,puntos_ejercicio1,puntos_ejercicio2;
    int clientes_apuntados;
     ArrayList<String> lista_usuarios;

    public Ranking(String id_competicion,ArrayList<String> lista_usuarios) {
        this.id_competicion = id_competicion;
        this.puntos_ejercicio1 = puntos_ejercicio1;
        this.puntos_ejercicio2 = puntos_ejercicio2;
        this.lista_usuarios = lista_usuarios;
        this.clientes_apuntados = 0;
    }

    public String getId_competicion() {
        return id_competicion;
    }

    public void setId_competicion(String id_competicion) {
        this.id_competicion = id_competicion;
    }

    public String getPuntos_ejercicio1() {
        return puntos_ejercicio1;
    }

    public void setPuntos_ejercicio1(String puntos_ejercicio1) {
        this.puntos_ejercicio1 = puntos_ejercicio1;
    }

    public String getPuntos_ejercicio2() {
        return puntos_ejercicio2;
    }

    public void setPuntos_ejercicio2(String puntos_ejercicio2) {
        this.puntos_ejercicio2 = puntos_ejercicio2;
    }

    public int getClientes_apuntados() {
        return clientes_apuntados;
    }

    public void setClientes_apuntados(int clientes_apuntados) {
        this.clientes_apuntados = clientes_apuntados;
    }

    public ArrayList<String> getLista_usuarios() {
        return lista_usuarios;
    }

    public void setLista_usuarios(ArrayList<String> lista_usuarios) {
        this.lista_usuarios = lista_usuarios;
    }
}
