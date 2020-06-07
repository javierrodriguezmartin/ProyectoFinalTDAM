package com.example.reachthegym.objetos;

import java.util.ArrayList;

public class Ranking {

    String id_competicion,id_ranking;
    int clientes_apuntados,total_puntos;
     ArrayList<AuxiliarRanking> lista_usuarios;

    public Ranking() {
        this.id_competicion = "";
        this.total_puntos = 0;
        this.lista_usuarios = null;
        this.clientes_apuntados = 0;
    }

    public Ranking(String id_competicion,ArrayList<AuxiliarRanking> lista_usuarios) {
        this.id_competicion = id_competicion;
        this.total_puntos = 0;
        this.lista_usuarios = lista_usuarios;
        this.clientes_apuntados = 0;
    }

    public String getId_competicion() {
        return id_competicion;
    }

    public void setId_competicion(String id_competicion) {
        this.id_competicion = id_competicion;
    }


    public String getId_ranking() {
        return id_ranking;
    }

    public void setId_ranking(String id_ranking) {
        this.id_ranking = id_ranking;
    }

    public int getTotal_puntos() {
        return total_puntos;
    }

    public void setTotal_puntos(int total_puntos) {
        this.total_puntos = total_puntos;
    }

    public int getClientes_apuntados() {
        return clientes_apuntados;
    }

    public void setClientes_apuntados(int clientes_apuntados) {
        this.clientes_apuntados = clientes_apuntados;
    }

    public ArrayList<AuxiliarRanking> getLista_usuarios() {
        return lista_usuarios;
    }

    public void setLista_usuarios(ArrayList<AuxiliarRanking> lista_usuarios) {
        this.lista_usuarios = lista_usuarios;
    }
}
