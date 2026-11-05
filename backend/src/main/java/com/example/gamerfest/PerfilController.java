package com.example.gamerfest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public ResponseEntity<PerfilUsuario> obtenerPerfil(
            Authentication authentication) {

        PerfilUsuario perfil = perfilService.buscarPorEmail(
                authentication.getName());

        if (perfil == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(perfil);
    }

    @PostMapping
    public ResponseEntity<?> actualizarPerfil(
            Authentication authentication,
            @RequestBody Map<String, Object> datos) {

        PerfilUsuario perfil = perfilService.buscarPorEmail(
                authentication.getName());

        if (perfil == null) {
            return ResponseEntity.notFound().build();
        }

        perfil.setNombre((String) datos.get("nombre"));
        perfil.setNombreUsuario((String) datos.get("nombreUsuario"));
        perfil.setAvatar((String) datos.get("avatar"));
        perfil.setJuegosFavoritos((String) datos.get("juegosFavoritos"));
        perfil.setNotificaciones(
                Boolean.TRUE.equals(datos.get("notificaciones")));

        perfilService.guardar(perfil);

        return ResponseEntity.ok(
                Map.of("mensaje", "Perfil actualizado correctamente"));
    }
}