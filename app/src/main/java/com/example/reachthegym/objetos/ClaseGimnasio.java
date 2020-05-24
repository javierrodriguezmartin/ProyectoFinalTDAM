package com.example.reachthegym.objetos;

import java.util.ArrayList;

public class ClaseGimnasio  {

    private String id_clase,nombre,descripcion,aula,dia;
    private int hora_inicio,hora_finalizacion,capacidad_actual,capacidad_maxima;
    private ArrayList<String> clientes_apuntados;


    public ClaseGimnasio() {
        this.id_clase = "";
        this.nombre = "";
        this.descripcion = "";
        this.aula = "";
        this.hora_inicio = 0;
        this.hora_finalizacion = 0;
        this.capacidad_maxima = 20;
        this.dia = "";
        this.clientes_apuntados = null;
    }

    public ClaseGimnasio(String id_clase, String nombre, String descripcion, String aula, int hora_inicio, int hora_finalizacion,String dia,ArrayList<String> lista) {
        this.id_clase = id_clase;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.aula = aula;
        this.hora_inicio = hora_inicio;
        this.hora_finalizacion = hora_finalizacion;
        this.capacidad_maxima = 20;
        this.dia = dia;
        this.clientes_apuntados = lista;
    }

    public String getId_clase() {
        return id_clase;
    }

    public void setId_clase(String id_clase) {
        this.id_clase = id_clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(int hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public int getHora_finalizacion() {
        return hora_finalizacion;
    }

    public void setHora_finalizacion(int hora_finalizacion) {
        this.hora_finalizacion = hora_finalizacion;
    }

    public int getCapacidad_actual() {
        return capacidad_actual;
    }

    public void setCapacidad_actual(int capacidad_actual) {
        this.capacidad_actual = capacidad_actual;
    }

    public int getCapacidad_maxima() {
        return capacidad_maxima;
    }

    public void setCapacidad_maxima(int capacidad_maxima) {
        this.capacidad_maxima = capacidad_maxima;
    }

    public ArrayList<String> getClientes_apuntados() {
        return clientes_apuntados;
    }

    public void setClientes_apuntados(ArrayList<String> clientes_apuntados) {
        this.clientes_apuntados = clientes_apuntados;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
