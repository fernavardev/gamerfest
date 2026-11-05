
package com.example.gamerfest;

import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public void guardar(PerfilUsuario perfil) {
        PerfilUsuario existente = perfilRepository.findByEmail(perfil.getEmail());

        if (existente != null) {
            perfil.setId(existente.getId());
        }

        perfilRepository.save(perfil);
    }

    public PerfilUsuario buscarPorEmail(String email) {
        return perfilRepository.findByEmail(email);
    }
}
