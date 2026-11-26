package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class JwtConstantsTest {

    @Test
    void deberiaDefinirConstantesDelProtocoloJwt() throws Exception {
        assertEquals("/login", JwtConstants.LOGIN_URL);
        assertEquals("Authorization", JwtConstants.HEADER_AUTHORIZATION_KEY);
        assertEquals("Bearer ", JwtConstants.TOKEN_BEARER_PREFIX);
        assertEquals("GamerFest", JwtConstants.TOKEN_ISSUER);
        assertEquals(3_600_000L, JwtConstants.TOKEN_EXPIRATION_TIME);
    }
}