package com.example.gamerfest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioInicializador {

    private final UsuarioRepository usuarioRepository;
    private final PerfilService perfilService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioInicializador(
            UsuarioRepository usuarioRepository,
            PerfilService perfilService,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.perfilService = perfilService;
        this.passwordEncoder = passwordEncoder;
    }

    public void inicializarUsuarios() {
        crearSiNoExiste("fernando", "Fernando", "USER");
        crearSiNoExiste("trinidad", "Trinidad", "USER");
        crearSiNoExiste("admin", "Administrador", "ADMIN");
    }

    private void crearSiNoExiste(
            String username, String nombre, String rol) {

        if (usuarioRepository.findByUsername(username).isEmpty()) {
            Usuario usuario = new Usuario(
                    username,
                    passwordEncoder.encode("123456"),
                    rol
            );

            usuarioRepository.save(usuario);
        }

        if (perfilService.buscarPorEmail(username) == null) {
            perfilService.guardar(new PerfilUsuario(username, nombre));
        }
    }
}
