package com.example.reachthegym.objetos;

public class BuscarCompañero {

    String id_anuncio,id_creador,descripcion,horario;

    public BuscarCompañero() {
        this.id_anuncio = "";
        this.id_creador = "";
        this.descripcion = "";
        this.horario = "";
    }

    public BuscarCompañero(String id_anuncio, String id_creador, String descripcion, String horario) {
        this.id_anuncio = id_anuncio;
        this.id_creador = id_creador;
        this.descripcion = descripcion;
        this.horario = horario;
    }

    public String getId_anuncio() {
        return id_anuncio;
    }

    public void setId_anuncio(String id_anuncio) {
        this.id_anuncio = id_anuncio;
    }

    public String getId_creador() {
        return id_creador;
    }

    public void setId_creador(String id_creador) {
        this.id_creador = id_creador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
