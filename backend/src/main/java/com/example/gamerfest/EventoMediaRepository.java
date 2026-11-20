package com.example.gamerfest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoMediaRepository extends JpaRepository<EventoMedia, Long> {

    List<EventoMedia> findByEventoId(Long eventoId);
}
