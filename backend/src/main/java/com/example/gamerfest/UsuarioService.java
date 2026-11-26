package com.example.gamerfest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResumen> listarUsuarios(String usernameActual) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> !usuario.getUsername().equals(usernameActual))
                .map(usuario -> new UsuarioResumen(
                        usuario.getId(),
                        usuario.getUsername(),
                        usuario.getRol()
                ))
                .toList();
    }

    public void cambiarRol(Long id, String rol, String usernameActual) {

        if (!"ADMIN".equals(rol) && !"USER".equals(rol)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Rol no válido");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (usuario.getUsername().equals(usernameActual)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No puedes modificar tu propio rol");
        }

        usuario.setRol(rol);
        usuarioRepository.save(usuario);
    }
}