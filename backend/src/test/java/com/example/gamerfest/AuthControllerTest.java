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

class AuthControllerTest {

    @Test
    void deberiaRetornarTokenYRolAlIniciarSesion() throws Exception {
        AuthService authService = mock(AuthService.class);
        JwtService jwtService = mock(JwtService.class);

        AuthController controller =
                new AuthController(authService, jwtService);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        Usuario usuario = new Usuario("usuario", "hash-test", "USER");

        when(authService.autenticar("usuario", "password-test"))
                .thenReturn(usuario);
        when(jwtService.generarToken(usuario)).thenReturn("token-test");

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"usuario\",\"password\":\"password-test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-test"))
                .andExpect(jsonPath("$.rol").value("USER"));
    }
}