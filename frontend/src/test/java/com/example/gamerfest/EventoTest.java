package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    @Test
    void deberiaAlmacenarNombreYModalidad() {
        Evento evento = new Evento();

        evento.setNombre("Torneo Gamer");
        evento.setModalidad("ONLINE");

        assertEquals("Torneo Gamer", evento.getNombre());
        assertEquals("ONLINE", evento.getModalidad());
    }
}