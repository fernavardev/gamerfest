package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest {


    @Test
    void deberiaAutenticarUsuarioConCredencialesValidas() throws Exception {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AuthService service = new AuthService(repository, encoder);

        Usuario usuario = new Usuario("usuario", "hash-test", "USER");

        when(repository.findByUsername("usuario"))
                .thenReturn(Optional.of(usuario));
        when(encoder.matches("password-test", "hash-test"))
                .thenReturn(true);

        Usuario resultado = service.autenticar("usuario", "password-test");

        assertSame(usuario, resultado);
    }
}