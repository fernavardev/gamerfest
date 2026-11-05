package com.example.gamerfest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/eventos")
public class EventoApiController {

    private final EventoService eventoService;

    public EventoApiController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> listarEventos() {
        return eventoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> detalleEvento(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}