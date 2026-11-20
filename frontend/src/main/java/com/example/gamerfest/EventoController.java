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

    private final BackendApiClient backendApiClient;

    public EventoController(BackendApiClient backendApiClient) {
        this.backendApiClient = backendApiClient;
    }

    @GetMapping("/eventos")
    public String listarEventos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String videojuego,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha,
            @RequestParam(required = false) String modalidad,
            Model model) {

        List<Evento> resultados = backendApiClient.listarEventos(
                nombre, videojuego, categoria, pais, ciudad, fecha, modalidad);

        model.addAttribute("eventos", resultados);
        model.addAttribute("nombre", nombre);
        model.addAttribute("videojuego", videojuego);
        model.addAttribute("categoria", categoria);
        model.addAttribute("pais", pais);
        model.addAttribute("ciudad", ciudad);
        model.addAttribute("fecha", fecha);
        model.addAttribute("modalidad", modalidad);

        return "eventos";
    }

    @GetMapping("/eventos/{id}")
    public String detalleEvento(
            @PathVariable Long id,
            Model model) {

        Evento evento = backendApiClient.buscarEventoPorId(id);

        if (evento == null) {
            return "redirect:/eventos";
        }

        model.addAttribute("evento", evento);

        return "detalle-evento";
    }
}
