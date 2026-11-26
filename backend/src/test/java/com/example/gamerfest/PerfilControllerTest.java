package com.example.gamerfest;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PerfilControllerTest {

    @Test
    void deberiaRetornarPerfilDelUsuarioAutenticado() throws Exception {
        PerfilService service = mock(PerfilService.class);

        PerfilController controller = new PerfilController(
                service, mock(AlmacenamientoService.class));

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        Authentication usuario = mock(Authentication.class);
        when(usuario.getName()).thenReturn("usuario@example.com");

        PerfilUsuario perfil = new PerfilUsuario(
                "usuario@example.com", "Usuario Test");

        when(service.buscarPorEmail("usuario@example.com"))
                .thenReturn(perfil);

        mvc.perform(get("/api/perfil").principal(usuario))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("usuario@example.com"))
                .andExpect(jsonPath("$.nombre").value("Usuario Test"));
    }
}