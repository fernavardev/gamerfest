package com.example.gamerfest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EventoController {

    public static final List<Evento> eventos = List.of(

        new Evento(
            1L,
            "GamerFest Santiago",
            "Videojuegos",
            LocalDate.of(2026, 11, 14),
            LocalTime.of(10, 0),
            "Santiago",
            "Centro de Eventos GamerFest",
            "Torneos, videojuegos y actividades para la comunidad gamer.",
            "GamerFest Chile",
            "Estacionamiento disponible, acceso mediante transporte público, zona de alimentación y alojamientos cercanos",
            "Estudios independientes y tiendas gamer",
            true
        ),

        new Evento(
            2L,
            "Copa Esports Chile",
            "Esports",
            LocalDate.of(2026, 12, 5),
            LocalTime.of(12, 0),
            "Valparaíso",
            "Centro de Convenciones",
            "Competencia de equipos de videojuegos.",
            "Liga Esports Chile",
            "Estacionamientos cercanos, locomoción colectiva, servicios de comida y opciones de alojamiento en la ciudad",
            "Equipos profesionales y marcas tecnológicas",
            true
        ),

        new Evento(
            3L,
            "Encuentro Retro Gamer",
            "Retro",
            LocalDate.of(2027, 1, 16),
            LocalTime.of(11, 0),
            "Concepción",
            "Centro Cultural",
            "Exhibición de consolas y videojuegos clásicos.",
            "Comunidad Retro Chile",
            "Estacionamiento público, acceso en transporte público, cafetería y alternativas de alojamiento cercanas",
            "Coleccionistas y tiendas retro",
            false
        )
    );

    @GetMapping("/eventos")
    public String listarEventos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha,
            @RequestParam(required = false) String ubicacion,
            Model model) {

        List<Evento> resultados = eventos;

        if (categoria != null && !categoria.isBlank()) {
            resultados = resultados.stream()
                    .filter(evento -> evento.getCategoria()
                            .equalsIgnoreCase(categoria))
                    .toList();
        }

        if (fecha != null) {
            resultados = resultados.stream()
                    .filter(evento -> evento.getFecha().equals(fecha))
                    .toList();
        }

        if (ubicacion != null && !ubicacion.isBlank()) {
            resultados = resultados.stream()
                    .filter(evento -> evento.getUbicacion()
                            .toLowerCase()
                            .contains(ubicacion.trim().toLowerCase()))
                    .toList();
        }

        model.addAttribute("eventos", resultados);
        model.addAttribute("categoria", categoria);
        model.addAttribute("fecha", fecha);
        model.addAttribute("ubicacion", ubicacion);

        return "eventos";
    }
    
    @GetMapping("/eventos/{id}")
    public String detalleEvento(
            @PathVariable Long id,
            Model model) {

        Evento evento = eventos.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (evento == null) {
            return "redirect:/eventos";
        }

        model.addAttribute("evento", evento);

        return "detalle-evento";
    }
}
