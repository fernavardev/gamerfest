package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnlacesCompartirTest {

    @Test
    void deberiaAlmacenarUrlWeb() {
        EnlacesCompartir enlaces = new EnlacesCompartir();

        enlaces.setWebUrl("https://example.com/eventos/1");

        assertEquals("https://example.com/eventos/1", enlaces.getWebUrl());
    }
}