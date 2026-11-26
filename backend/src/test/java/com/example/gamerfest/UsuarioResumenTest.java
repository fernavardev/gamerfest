package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioResumenTest {

    @Test
    void deberiaContenerIdNombreDeUsuarioYRol() {
        UsuarioResumen usuario = new UsuarioResumen(1L, "usuario", "USER");

        assertEquals(Long.valueOf(1L), usuario.id());
        assertEquals("usuario", usuario.username());
        assertEquals("USER", usuario.rol());
    }
}