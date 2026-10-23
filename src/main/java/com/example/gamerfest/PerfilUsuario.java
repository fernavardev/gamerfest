
package com.example.gamerfest;

public class PerfilUsuario {

    private String email;
    private String nombreUsuario;
    private String nombre;
    private String avatar;
    private String juegosFavoritos;
    private boolean notificaciones;

    public PerfilUsuario(String email, String nombre) {
        this.email = email;
        this.nombreUsuario = nombre;
        this.nombre = nombre;
        this.avatar = "";
        this.juegosFavoritos = "";
        this.notificaciones = true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJuegosFavoritos() {
        return juegosFavoritos;
    }

    public void setJuegosFavoritos(String juegosFavoritos) {
        this.juegosFavoritos = juegosFavoritos;
    }

    public boolean isNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }
}
