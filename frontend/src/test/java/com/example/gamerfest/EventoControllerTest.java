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

class EventoControllerTest {

    private InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();

        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        return resolver;
    }

    @Test
    void deberiaMostrarEventosFiltradosPorNombre() throws Exception {
        BackendApiClient client = mock(BackendApiClient.class);
        EventoController controller = new EventoController(
                client, "http://localhost:8081");

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver()).build();

        Evento evento = new Evento();
        evento.setNombre("GamerFest Test");

        List<Evento> eventos = List.of(evento);

        when(client.listarEventos(
                "GamerFest", null, null, null, null, null, null))
                .thenReturn(eventos);

        mvc.perform(get("/eventos").param("nombre", "GamerFest"))
                .andExpect(status().isOk())
                .andExpect(view().name("eventos"))
                .andExpect(model().attribute("eventos", eventos))
                .andExpect(model().attribute("nombre", "GamerFest"));
    }
}