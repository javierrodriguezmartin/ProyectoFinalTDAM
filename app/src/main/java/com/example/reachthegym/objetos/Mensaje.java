package com.example.reachthegym.objetos;

public class Mensaje {

    String id_usuario1,contenido_mensaje,nombre,id_chat;

    public Mensaje() {
        this.id_usuario1 = "";
        this.contenido_mensaje = "";
        this.nombre = "";
        this.id_chat = "";
    }

    public Mensaje(String id_usuario1, String contenido_mensaje, String nombre,String id_chat) {
        this.id_usuario1 = id_usuario1;
        this.contenido_mensaje = contenido_mensaje;
        this.nombre = nombre;
        this.id_chat = id_chat;
    }


    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_usuario1() {
        return id_usuario1;
    }

    public void setId_usuario1(String id_usuario1) {
        this.id_usuario1 = id_usuario1;
    }

    public String getContenido_mensaje() {
        return contenido_mensaje;
    }

    public void setContenido_mensaje(String contenido_mensaje) {
        this.contenido_mensaje = contenido_mensaje;
    }
}
