package com.example.gamerfest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest {

    private InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();

        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        return resolver;
    }

    @Test
    void deberiaMostrarUsuariosEnPanelAdministrativo() throws Exception {
        BackendApiClient client = mock(BackendApiClient.class);
        AdminController controller = new AdminController(client);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver()).build();

        List<UsuarioResumen> usuarios = List.of(
                new UsuarioResumen(1L, "usuario", "USER"));

        when(client.listarUsuarios(any(HttpSession.class)))
                .thenReturn(usuarios);

        mvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attribute("usuarios", usuarios));

        verify(client).listarUsuarios(any(HttpSession.class));
    }
}