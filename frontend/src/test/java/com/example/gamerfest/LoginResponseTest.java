package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    void deberiaContenerTokenYRol() {
        LoginResponse respuesta = new LoginResponse("token-test", "ADMIN");

        assertEquals("token-test", respuesta.token());
        assertEquals("ADMIN", respuesta.rol());
    }
}