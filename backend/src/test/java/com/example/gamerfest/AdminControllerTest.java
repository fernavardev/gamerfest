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

class AdminControllerTest {

    @Test
    void deberiaCambiarRolYResponderSinContenido() throws Exception {
        UsuarioService service = mock(UsuarioService.class);
        AdminController controller = new AdminController(service);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        Authentication admin = mock(Authentication.class);
        when(admin.getName()).thenReturn("admin");

        mvc.perform(patch("/api/admin/usuarios/1/rol")
                .principal(admin)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rol\":\"ADMIN\"}"))
                .andExpect(status().isNoContent());

        verify(service).cambiarRol(1L, "ADMIN", "admin");
    }
}