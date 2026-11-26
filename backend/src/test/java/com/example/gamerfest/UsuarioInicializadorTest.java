package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

class UsuarioInicializadorTest {


    @Test
    void deberiaInicializarTresUsuariosIncluyendoAdministrador() throws Exception {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PerfilService perfilService = mock(PerfilService.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        UsuarioInicializador inicializador =
                new UsuarioInicializador(repository, perfilService, encoder);

        when(repository.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("hash-test");

        inicializador.inicializarUsuarios();

        ArgumentCaptor<Usuario> captor =
                ArgumentCaptor.forClass(Usuario.class);

        verify(repository, times(3)).save(captor.capture());
        verify(perfilService, times(3)).guardar(any(PerfilUsuario.class));

        List<Usuario> usuarios = captor.getAllValues();

        assertEquals(3, usuarios.size());
        assertTrue(usuarios.stream().anyMatch(usuario ->
                "admin".equals(usuario.getUsername())
                && "ADMIN".equals(usuario.getRol())));
    }
}