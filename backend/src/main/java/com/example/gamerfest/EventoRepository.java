package com.example.gamerfest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jakarta.persistence.criteria.Predicate;

public interface EventoRepository
        extends JpaRepository<Evento, Long>, JpaSpecificationExecutor<Evento> {

    List<Evento> findByPublicadorEmail(String publicadorEmail);

    static Specification<Evento> conFiltros(
            String nombre,
            String videojuego,
            String categoria,
            String pais,
            String ciudad,
            LocalDate fecha,
            Modalidad modalidad) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nombre")),
                        "%" + nombre.trim().toLowerCase() + "%"));
            }
            if (videojuego != null && !videojuego.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("videojuego")),
                        "%" + videojuego.trim().toLowerCase() + "%"));
            }
            if (categoria != null && !categoria.isBlank()) {
                predicates.add(cb.equal(
                        cb.lower(root.get("categoria")),
                        categoria.trim().toLowerCase()));
            }
            if (pais != null && !pais.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("pais")),
                        "%" + pais.trim().toLowerCase() + "%"));
            }
            if (ciudad != null && !ciudad.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("ciudad")),
                        "%" + ciudad.trim().toLowerCase() + "%"));
            }
            if (fecha != null) {
                predicates.add(cb.equal(root.get("fecha"), fecha));
            }
            if (modalidad != null) {
                predicates.add(cb.equal(root.get("modalidad"), modalidad));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
