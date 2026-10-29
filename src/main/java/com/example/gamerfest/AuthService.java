package com.example.gamerfest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario autenticar(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario == null ||
                !passwordEncoder.matches(password, usuario.getPassword())) {
            return null;
        }

        return usuario;
    }
}
