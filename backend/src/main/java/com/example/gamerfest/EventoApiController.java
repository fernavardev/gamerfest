package com.example.gamerfest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos")
public class EventoApiController {

    private final EventoService eventoService;

    public EventoApiController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> listarEventos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String videojuego,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha,
            @RequestParam(required = false) Modalidad modalidad) {

        return eventoService.buscar(
                nombre, videojuego, categoria, pais, ciudad, fecha, modalidad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> detalleEvento(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
