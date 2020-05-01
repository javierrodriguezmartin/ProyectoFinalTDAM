package com.example.reachthegym.objetos;

public class EjercicioEmpleado extends Ejercicio  {
    private String id_empleado;

    public EjercicioEmpleado() {
        this.id_empleado = "";
    }

    public EjercicioEmpleado(String id_ejercicio, String nombre, String zona, String objetivo, String descripcion, String id_empleado) {
        super(id_ejercicio, nombre, zona, objetivo, descripcion);
        this.id_empleado = id_empleado;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }
}
