package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class WebSecurityConfigTest {

    @Test
    void deberiaCodificarYVerificarContrasenas() throws Exception {
        WebSecurityConfig config = new WebSecurityConfig();

        PasswordEncoder encoder = config.passwordEncoder();

        String password = "password-test";
        String hash = encoder.encode(password);

        assertInstanceOf(BCryptPasswordEncoder.class, encoder);
        assertNotEquals(password, hash);
        assertTrue(encoder.matches(password, hash));
        assertFalse(encoder.matches("password-incorrecta", hash));
    }
}