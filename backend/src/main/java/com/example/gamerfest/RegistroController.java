package com.example.gamerfest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistroController {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;
    private final PerfilService perfilService;

    public RegistroController(
            UsuarioRepository usuarios,
            PasswordEncoder passwordEncoder,
            PerfilService perfilService) {

        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
        this.perfilService = perfilService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(
            @RequestBody Map<String, String> datos) {

        String nombre = datos.get("nombre");
        String email = datos.get("email");
        String password = datos.get("password");

        // validacion de formato del correo antes de consultar la base de datos
        if (email == null || !email.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Formato de correo inválido."));
        }

        if (usuarios.findByUsername(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo ya está registrado."));
        }

        Usuario nuevoUsuario = new Usuario(
                email,
                passwordEncoder.encode(password),
                "USER");

        usuarios.save(nuevoUsuario);

        PerfilUsuario nuevoPerfil = new PerfilUsuario(email, nombre);
        perfilService.guardar(nuevoPerfil);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario registrado correctamente"));
    }
}