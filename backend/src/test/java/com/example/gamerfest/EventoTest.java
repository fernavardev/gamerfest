package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventoTest {

    @Test
    void deberiaAlmacenarNombreYCategoria() {
        Evento evento = new Evento();

        evento.setNombre("GamerFest");
        evento.setCategoria("Torneo");

        assertEquals("GamerFest", evento.getNombre());
        assertEquals("Torneo", evento.getCategoria());
    }
}