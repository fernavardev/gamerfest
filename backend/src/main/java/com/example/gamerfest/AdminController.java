package com.example.gamerfest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public List<UsuarioResumen> listarUsuarios(Authentication authentication) {
        return usuarioService.listarUsuarios(authentication.getName());
    }

    @PatchMapping("/usuarios/{id}/rol")
    public ResponseEntity<Void> cambiarRol(
            @PathVariable Long id,
            @RequestBody Map<String, String> datos,
            Authentication authentication) {

        usuarioService.cambiarRol(
                id,
                datos.get("rol"),
                authentication.getName());

        return ResponseEntity.noContent().build();
    }
}