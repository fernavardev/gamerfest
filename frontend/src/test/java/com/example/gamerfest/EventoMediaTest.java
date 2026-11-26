package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventoMediaTest {

    @Test
    void deberiaDistinguirImagenesDeVideos() {
        EventoMedia media = new EventoMedia();

        media.setContentType("image/png");

        assertTrue(media.isImagen());
        assertFalse(media.isVideo());

        media.setContentType("video/mp4");

        assertTrue(media.isVideo());
        assertFalse(media.isImagen());
    }
}