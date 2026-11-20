package com.example.gamerfest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/mios")
    public List<Evento> misEventos(Authentication authentication) {
        return eventoService.listarPorPublicador(authentication.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> detalleEvento(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evento> crearEvento(
            Authentication authentication,
            @RequestBody Evento datos) {

        Evento creado = eventoService.crear(datos, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}/media")
    public List<EventoMedia> listarMedia(@PathVariable Long id) {
        return eventoService.listarMedia(id);
    }

    @PostMapping(path = "/{id}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventoMedia> agregarMedia(
            Authentication authentication,
            @PathVariable Long id,
            @RequestParam MultipartFile archivo) {

        EventoMedia media = eventoService.agregarMedia(
                id, authentication.getName(), archivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(media);
    }

    @DeleteMapping("/{id}/media/{mediaId}")
    public ResponseEntity<Void> eliminarMedia(
            Authentication authentication,
            @PathVariable Long id,
            @PathVariable Long mediaId) {

        eventoService.eliminarMedia(id, mediaId, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
