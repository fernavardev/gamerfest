package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerfilUsuarioTest {

    @Test
    void deberiaInicializarDatosYPreferenciasDelPerfil() {
        PerfilUsuario perfil = new PerfilUsuario(
                "usuario@example.com", "Usuario Test");

        assertEquals("usuario@example.com", perfil.getEmail());
        assertEquals("Usuario Test", perfil.getNombre());
        assertEquals("Usuario Test", perfil.getNombreUsuario());
        assertTrue(perfil.isNotificaciones());
    }
}