package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;

class EventoServiceTest {


    @Test
    void deberiaListarTodosLosEventos() throws Exception {
        EventoRepository repository = mock(EventoRepository.class);

        EventoService service = new EventoService(
                repository,
                mock(EventoMediaRepository.class),
                mock(ComentarioEventoRepository.class),
                mock(AlmacenamientoService.class),
                "http://localhost:8080");

        Evento evento = new Evento();
        evento.setNombre("GamerFest Test");

        when(repository.findAll()).thenReturn(List.of(evento));

        List<Evento> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("GamerFest Test", resultado.get(0).getNombre());
        verify(repository).findAll();
    }
}