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

class RegistroControllerTest {

    @Test
    void deberiaRegistrarUsuarioCorrectamente() throws Exception {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        PerfilService perfilService = mock(PerfilService.class);

        RegistroController controller = new RegistroController(
                repository, encoder, perfilService);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(repository.findByUsername("nuevo@example.com"))
                .thenReturn(Optional.empty());
        when(encoder.encode("password-test")).thenReturn("hash-test");

        mvc.perform(post("/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Nuevo Usuario\","
                        + "\"email\":\"nuevo@example.com\","
                        + "\"password\":\"password-test\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje")
                        .value("Usuario registrado correctamente"));

        verify(repository).save(any(Usuario.class));
        verify(perfilService).guardar(any(PerfilUsuario.class));
    }
}