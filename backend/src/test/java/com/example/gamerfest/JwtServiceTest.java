package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class JwtServiceTest {


    @Test
    void deberiaGenerarYValidarToken() throws Exception {
        byte[] bytes = new byte[32];
        new java.security.SecureRandom().nextBytes(bytes);

        String clave = java.util.Base64.getEncoder()
                .encodeToString(bytes);

        JwtService service = new JwtService(clave);
        Usuario usuario = new Usuario("usuario", "hash-test", "ADMIN");

        String token = service.generarToken(usuario);
        var claims = service.validarToken(token);

        assertNotNull(token);
        assertEquals("usuario", claims.getSubject());
        assertEquals("ADMIN", claims.get("rol", String.class));
        assertEquals(JwtConstants.TOKEN_ISSUER, claims.getIssuer());
    }
}