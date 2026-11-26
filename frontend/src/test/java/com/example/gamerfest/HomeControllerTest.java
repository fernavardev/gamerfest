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

class HomeControllerTest {

    private InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();

        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        return resolver;
    }

    @Test
    void deberiaMostrarSoloEventosVigentes() throws Exception {
        BackendApiClient client = mock(BackendApiClient.class);
        HomeController controller = new HomeController(
                client, "http://localhost:8081");

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver()).build();

        Evento vigente = new Evento();
        vigente.setNombre("Evento futuro");
        vigente.setFecha(LocalDate.now().plusDays(1));

        Evento pasado = new Evento();
        pasado.setNombre("Evento pasado");
        pasado.setFecha(LocalDate.now().minusDays(1));

        when(client.listarEventos())
                .thenReturn(List.of(vigente, pasado));

        mvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute(
                        "eventos", List.of(vigente)));
    }
}