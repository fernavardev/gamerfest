package com.example.gamerfest;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    private LocalDate fecha;
    private LocalTime hora;
    private String direccion;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private String organizador;
    @Column(columnDefinition = "TEXT")
    private String servicios;
    @Column(columnDefinition = "TEXT")
    private String expositores;
    private boolean destacado;

    private String publicadorEmail;
    private String videojuego;
    private String pais;
    private String ciudad;

    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    public Evento() {
    }

    public Evento(
            String nombre,
            String categoria,
            LocalDate fecha,
            LocalTime hora,
            String direccion,
            String descripcion,
            String organizador,
            String servicios,
            String expositores,
            boolean destacado,
            String publicadorEmail,
            String videojuego,
            String pais,
            String ciudad,
            Modalidad modalidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.organizador = organizador;
        this.servicios = servicios;
        this.expositores = expositores;
        this.destacado = destacado;
        this.publicadorEmail = publicadorEmail;
        this.videojuego = videojuego;
        this.pais = pais;
        this.ciudad = ciudad;
        this.modalidad = modalidad;
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

    public String getPublicadorEmail() {
        return publicadorEmail;
    }

    public void setPublicadorEmail(String publicadorEmail) {
        this.publicadorEmail = publicadorEmail;
    }

    public String getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(String videojuego) {
        this.videojuego = videojuego;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }
}
