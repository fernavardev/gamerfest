package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

class MiUserDetailsServiceTest {


    @Test
    void deberiaCargarUsuarioPorNombre() throws Exception {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        MiUserDetailsService service = new MiUserDetailsService(repository);

        Usuario usuario = new Usuario("usuario", "hash-test", "USER");

        when(repository.findByUsername("usuario"))
                .thenReturn(Optional.of(usuario));

        var resultado = service.loadUserByUsername("usuario");

        assertSame(usuario, resultado);
        assertEquals("usuario", resultado.getUsername());
    }
}