package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {

    @Test
    void deberiaActualizarRolYAutoridades() {
        Usuario usuario = new Usuario("usuario", "password-test", "USER");

        usuario.setRol("ADMIN");

        assertEquals("ADMIN", usuario.getRol());
        assertEquals("ROLE_ADMIN",
                usuario.getAuthorities().iterator().next().getAuthority());
    }
}