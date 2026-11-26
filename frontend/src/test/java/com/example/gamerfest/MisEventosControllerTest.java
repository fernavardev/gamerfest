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

class MisEventosControllerTest {

    private InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();

        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        return resolver;
    }

    @Test
    void deberiaMostrarEventosDelUsuario() throws Exception {
        BackendApiClient client = mock(BackendApiClient.class);
        MisEventosController controller = new MisEventosController(
                client, "http://localhost:8081");

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver()).build();

        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNombre("Mi evento");

        List<Evento> eventos = List.of(evento);
        EnlacesCompartir enlaces = new EnlacesCompartir();

        when(client.listarMisEventos(any(HttpSession.class)))
                .thenReturn(eventos);
        when(client.listarMedia(any(HttpSession.class), eq(1L)))
                .thenReturn(List.of());
        when(client.obtenerEnlacesCompartir(
                any(HttpSession.class), eq(1L)))
                .thenReturn(enlaces);

        mvc.perform(get("/mis-eventos"))
                .andExpect(status().isOk())
                .andExpect(view().name("mis-eventos"))
                .andExpect(model().attribute("eventos", eventos))
                .andExpect(model().attribute(
                        "mediaPorEvento", Map.of(1L, List.of())))
                .andExpect(model().attribute(
                        "compartirPorEvento", Map.of(1L, enlaces)));
    }
}