package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventoMediaVistaTest {

    @Test
    void deberiaContenerRecursoMultimediaYUrl() {
        EventoMedia media = new EventoMedia();
        String url = "http://localhost:8081/uploads/evento.png";

        EventoMediaVista vista = new EventoMediaVista(media, url);

        assertSame(media, vista.media());
        assertEquals(url, vista.url());
    }
}