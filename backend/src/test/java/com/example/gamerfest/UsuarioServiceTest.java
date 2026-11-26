package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

class UsuarioServiceTest {


    @Test
    void deberiaCambiarRolDeUsuario() throws Exception {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        UsuarioService service = new UsuarioService(repository);

        Usuario usuario = new Usuario("usuario", "hash-test", "USER");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        service.cambiarRol(1L, "ADMIN", "admin");

        assertEquals("ADMIN", usuario.getRol());
        verify(repository).save(usuario);
    }
}