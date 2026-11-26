package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

class TokenStoreTest {

    @Test
    void deberiaRequerirAlmacenarYRecuperarToken() throws Exception {
        TokenStore tokenStore = new TokenStore();
        MockHttpSession session = new MockHttpSession();

        assertThrows(
                AuthenticationCredentialsNotFoundException.class,
                () -> tokenStore.requireToken(session));

        tokenStore.save(session, "token-test");

        assertEquals("token-test", tokenStore.requireToken(session));
    }
}