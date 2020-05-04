package com.example.reachthegym.objetos;

import android.net.Uri;

public class UsuarioEmpleado extends Usuario {

    private String id_empleado;
    private Uri img_url;

    public UsuarioEmpleado() {
        this.id_empleado = "";
    }

    public UsuarioEmpleado(String dni, String nombre, String apellidos, String telefono, String direccion, String contrasena, String email, String fecha_alta, String id,String id_empleado) {
        super(dni, nombre, apellidos, telefono, direccion, contrasena, email, fecha_alta, id);
        this.id_empleado = id_empleado;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    @Override
    public Uri getImg_url() {
        return img_url;
    }

    @Override
    public void setImg_url(Uri img_url) {
        this.img_url = img_url;
    }
}
