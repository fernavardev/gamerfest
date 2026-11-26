package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PerfilServiceTest {


    @Test
    void deberiaBuscarPerfilPorEmail() throws Exception {
        PerfilRepository repository = mock(PerfilRepository.class);
        PerfilService service = new PerfilService(repository);

        PerfilUsuario perfil = new PerfilUsuario(
                "usuario@example.com", "Usuario Test");

        when(repository.findByEmail("usuario@example.com"))
                .thenReturn(perfil);

        PerfilUsuario resultado =
                service.buscarPorEmail("usuario@example.com");

        assertSame(perfil, resultado);
        verify(repository).findByEmail("usuario@example.com");
    }
}