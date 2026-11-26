package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ComentarioRequestTest {

    @Test
    void deberiaAlmacenarTextoYValoracion() {
        ComentarioRequest request = new ComentarioRequest();

        request.setTexto("Buen evento");
        request.setValoracion(4);

        assertEquals("Buen evento", request.getTexto());
        assertEquals(4, request.getValoracion());
    }
}