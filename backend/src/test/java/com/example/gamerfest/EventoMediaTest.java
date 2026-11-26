package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventoMediaTest {

    @Test
    void deberiaAlmacenarRutaYTipoDeContenido() {
        EventoMedia media = new EventoMedia();

        media.setRuta("/uploads/evento.png");
        media.setContentType("image/png");

        assertEquals("/uploads/evento.png", media.getRuta());
        assertEquals("image/png", media.getContentType());
    }
}