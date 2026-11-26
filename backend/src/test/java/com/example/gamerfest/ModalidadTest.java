package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModalidadTest {

    @Test
    void deberiaReconocerModalidadPresencial() {
        Modalidad modalidad = Modalidad.valueOf("PRESENCIAL");

        assertEquals(Modalidad.PRESENCIAL, modalidad);
    }
}