package com.example.gamerfest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioEventoRepository extends JpaRepository<ComentarioEvento, Long> {

    List<ComentarioEvento> findByEventoIdOrderByCreadoEnDesc(Long eventoId);
}
