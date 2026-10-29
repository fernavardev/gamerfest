package com.example.gamerfest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<PerfilUsuario, Long> {

    PerfilUsuario findByEmail(String email);

}