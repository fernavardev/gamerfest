package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PerfilUsuarioTest {

    @Test
    void deberiaAlmacenarEmailYNombre() {
        PerfilUsuario perfil = new PerfilUsuario();

        perfil.setEmail("usuario@example.com");
        perfil.setNombre("Usuario Test");

        assertEquals("usuario@example.com", perfil.getEmail());
        assertEquals("Usuario Test", perfil.getNombre());
    }
}