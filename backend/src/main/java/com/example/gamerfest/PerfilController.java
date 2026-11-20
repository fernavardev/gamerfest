package com.example.gamerfest;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private final PerfilService perfilService;
    private final AlmacenamientoService almacenamientoService;

    public PerfilController(
            PerfilService perfilService,
            AlmacenamientoService almacenamientoService) {
        this.perfilService = perfilService;
        this.almacenamientoService = almacenamientoService;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> actualizarPerfil(
            Authentication authentication,
            @RequestParam String nombre,
            @RequestParam String nombreUsuario,
            @RequestParam(required = false) MultipartFile avatar,
            @RequestParam(required = false) String juegosFavoritos,
            @RequestParam(defaultValue = "false") boolean notificaciones) {

        PerfilUsuario perfil = perfilService.buscarPorEmail(
                authentication.getName());

        if (perfil == null) {
            return ResponseEntity.notFound().build();
        }

        perfil.setNombre(nombre);
        perfil.setNombreUsuario(nombreUsuario);
        perfil.setJuegosFavoritos(
                juegosFavoritos == null ? "" : juegosFavoritos);
        perfil.setNotificaciones(notificaciones);

        if (avatar != null && !avatar.isEmpty()) {
            perfil.setAvatar(almacenamientoService.guardarImagen(avatar));
        }

        perfilService.guardar(perfil);

        return ResponseEntity.ok(
                Map.of("mensaje", "Perfil actualizado correctamente"));
    }
}
