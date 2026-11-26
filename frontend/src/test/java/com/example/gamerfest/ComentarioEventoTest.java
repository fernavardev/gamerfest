package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComentarioEventoTest {

    @Test
    void deberiaAlmacenarTextoYValoracion() {
        ComentarioEvento comentario = new ComentarioEvento();

        comentario.setTexto("Excelente evento");
        comentario.setValoracion(5);

        assertEquals("Excelente evento", comentario.getTexto());
        assertEquals(5, comentario.getValoracion());
    }
}