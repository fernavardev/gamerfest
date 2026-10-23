package com.example.gamerfest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    private final Map<String, PerfilUsuario> perfiles = new ConcurrentHashMap<>();

    public PerfilService() {
        guardar(new PerfilUsuario("fernando", "Fernando"));
        guardar(new PerfilUsuario("trinidad", "Trinidad"));
        guardar(new PerfilUsuario("admin", "Administrador"));
    }

    public void guardar(PerfilUsuario perfil) {
        perfiles.put(perfil.getEmail(), perfil);
    }

    public PerfilUsuario buscarPorEmail(String email) {
        return perfiles.get(email);
    }
}
