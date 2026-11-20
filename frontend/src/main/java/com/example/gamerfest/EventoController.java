package com.example.gamerfest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class EventoController {

    private final BackendApiClient backendApiClient;
    private final String backendPublicUrl;

    public EventoController(
            BackendApiClient backendApiClient,
            @Value("${backend.public-url}") String backendPublicUrl) {
        this.backendApiClient = backendApiClient;
        this.backendPublicUrl = backendPublicUrl;
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
            HttpSession session,
            Model model) {

        Evento evento = backendApiClient.buscarEventoPorId(session, id);

        if (evento == null) {
            return "redirect:/eventos";
        }

        List<EventoMediaVista> media = new ArrayList<>();
        for (EventoMedia item : backendApiClient.listarMedia(session, id)) {
            media.add(new EventoMediaVista(item, urlPublica(item.getRuta())));
        }

        List<ComentarioEvento> comentarios =
                backendApiClient.listarComentarios(session, id);
        Double promedio = null;
        if (!comentarios.isEmpty()) {
            promedio = Math.round(
                    comentarios.stream()
                            .mapToInt(ComentarioEvento::getValoracion)
                            .average()
                            .orElse(0) * 10.0) / 10.0;
        }

        model.addAttribute("evento", evento);
        model.addAttribute("media", media);
        model.addAttribute("comentarios", comentarios);
        model.addAttribute("promedioValoracion", promedio);

        return "detalle-evento";
    }

    @PostMapping("/eventos/{id}/comentarios")
    public String comentarEvento(
            @PathVariable Long id,
            HttpSession session,
            @RequestParam String texto,
            @RequestParam int valoracion,
            RedirectAttributes redirectAttributes) {

        backendApiClient.agregarComentario(session, id, texto, valoracion);
        redirectAttributes.addFlashAttribute(
                "mensaje", "Comentario publicado.");
        return "redirect:/eventos/" + id;
    }

    private String urlPublica(String ruta) {
        if (ruta == null || ruta.isBlank()) {
            return null;
        }
        if (ruta.startsWith("http://") || ruta.startsWith("https://")) {
            return ruta;
        }
        String base = backendPublicUrl.endsWith("/")
                ? backendPublicUrl.substring(0, backendPublicUrl.length() - 1)
                : backendPublicUrl;
        return ruta.startsWith("/") ? base + ruta : base + "/" + ruta;
    }
}
