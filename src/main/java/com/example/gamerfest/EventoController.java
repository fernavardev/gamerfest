
package com.example.gamerfest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/eventos")
    public String listarEventos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha,
            @RequestParam(required = false) String ubicacion,
            Model model) {

        List<Evento> resultados = eventoService.listarTodos();

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

        Evento evento = eventoService.buscarPorId(id).orElse(null);

        if (evento == null) {
            return "redirect:/eventos";
        }

        model.addAttribute("evento", evento);

        return "detalle-evento";
    }
}
