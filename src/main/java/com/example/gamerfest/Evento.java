package com.example.gamerfest;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {

    private Long id;
    private String nombre;
    private String categoria;
    private LocalDate fecha;
    private LocalTime hora;
    private String ubicacion;
    private String direccion;
    private String descripcion;
    private String organizador;
    private String servicios;
    private String expositores;
    private boolean destacado;
    public Evento(Long id, String nombre, String categoria, LocalDate fecha, LocalTime hora, String ubicacion,
            String direccion, String descripcion, String organizador, String servicios, String expositores,
            boolean destacado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.hora = hora;
        this.ubicacion = ubicacion;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.organizador = organizador;
        this.servicios = servicios;
        this.expositores = expositores;
        this.destacado = destacado;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getOrganizador() {
        return organizador;
    }
    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }
    public String getServicios() {
        return servicios;
    }
    public void setServicios(String servicios) {
        this.servicios = servicios;
    }
    public String getExpositores() {
        return expositores;
    }
    public void setExpositores(String expositores) {
        this.expositores = expositores;
    }
    public boolean isDestacado() {
        return destacado;
    }
    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

}
