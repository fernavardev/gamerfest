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

class EventoApiControllerTest {

    @Test
    void deberiaRetornarEventoPorId() throws Exception {
        EventoService service = mock(EventoService.class);
        EventoApiController controller = new EventoApiController(service);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        Evento evento = new Evento();
        evento.setNombre("GamerFest Test");

        when(service.buscarPorId(1L)).thenReturn(Optional.of(evento));

        mvc.perform(get("/api/eventos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("GamerFest Test"));

        verify(service).buscarPorId(1L);
    }
}