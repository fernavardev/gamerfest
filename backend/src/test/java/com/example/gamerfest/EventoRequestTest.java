package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventoRequestTest {

    @Test
    void deberiaAlmacenarNombreYModalidad() {
        EventoRequest request = new EventoRequest();

        request.setNombre("Torneo Gamer");
        request.setModalidad(Modalidad.ONLINE);

        assertEquals("Torneo Gamer", request.getNombre());
        assertEquals(Modalidad.ONLINE, request.getModalidad());
    }
}