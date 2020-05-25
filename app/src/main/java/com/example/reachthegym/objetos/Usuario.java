package com.example.reachthegym.objetos;

import android.net.Uri;

import java.util.ArrayList;

public class Usuario {

    private String dni,nombre,apellidos,telefono,direccion,contrasena,tipo,email,id,fecha_alta;
    private Uri img_url;
    private ArrayList<String> lista_rutinas;

    public Usuario() {
        this.dni = "";
        this.nombre = "";
        this.apellidos = "";
        this.telefono = "";
        this.direccion = "";
        this.contrasena = "";
        this.tipo = "cliente";
        this.email = "";
        this.id = "";
        this.fecha_alta = "";
        this.lista_rutinas = null;
    }

    public Usuario(String dni, String nombre, String apellidos, String telefono, String direccion, String contrasena,String email,String fecha_alta,String id) {
        this.dni = dni;
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contrasena = contrasena;
        this.tipo = "cliente";
        this.email = email;
        this.fecha_alta = fecha_alta;
        this.lista_rutinas = null;
    }

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getImg_url() {
        return img_url;
    }

    public void setImg_url(Uri img_url) {
        this.img_url = img_url;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo() {
        return tipo;
    }

    public ArrayList<String> getLista_rutinas() {
        return lista_rutinas;
    }

    public void setLista_rutinas(ArrayList<String> lista_rutinas) {
        this.lista_rutinas = lista_rutinas;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
