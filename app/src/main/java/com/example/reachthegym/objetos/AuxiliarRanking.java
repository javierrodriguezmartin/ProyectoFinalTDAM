package com.example.reachthegym.objetos;

public class AuxiliarRanking {

    String id_usuario;
    int total_puntos;

    public AuxiliarRanking() {
        this.id_usuario = "";
        this.total_puntos = 0;
    }

    public AuxiliarRanking(String id_usuario) {
        this.id_usuario = id_usuario;
        this.total_puntos = 0;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getTotal_puntos() {
        return total_puntos;
    }

    public void setTotal_puntos(int total_puntos) {
        this.total_puntos = total_puntos;
    }
}
