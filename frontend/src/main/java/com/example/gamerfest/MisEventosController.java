package com.example.gamerfest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class MisEventosController {

    private final BackendApiClient backendApiClient;
    private final String backendPublicUrl;

    public MisEventosController(
            BackendApiClient backendApiClient,
            @Value("${backend.public-url}") String backendPublicUrl) {
        this.backendApiClient = backendApiClient;
        this.backendPublicUrl = backendPublicUrl;
    }

    @GetMapping("/mis-eventos")
    public String misEventos(HttpSession session, Model model) {
        List<Evento> eventos = backendApiClient.listarMisEventos(session);
        Map<Long, List<EventoMediaVista>> mediaPorEvento = new LinkedHashMap<>();

        for (Evento evento : eventos) {
            List<EventoMediaVista> vistas = new ArrayList<>();
            for (EventoMedia media : backendApiClient.listarMedia(session, evento.getId())) {
                vistas.add(new EventoMediaVista(media, urlPublica(media.getRuta())));
            }
            mediaPorEvento.put(evento.getId(), vistas);
        }

        model.addAttribute("eventos", eventos);
        model.addAttribute("mediaPorEvento", mediaPorEvento);
        return "mis-eventos";
    }

    @GetMapping("/mis-eventos/nuevo")
    public String formularioCrearEvento() {
        return "crear-evento";
    }

    @PostMapping("/mis-eventos/nuevo")
    public String crearEvento(
            HttpSession session,
            @RequestParam String nombre,
            @RequestParam String categoria,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
            @RequestParam String direccion,
            @RequestParam String descripcion,
            @RequestParam String organizador,
            @RequestParam(required = false) String servicios,
            @RequestParam(required = false) String expositores,
            @RequestParam(defaultValue = "false") boolean destacado,
            @RequestParam String videojuego,
            @RequestParam String pais,
            @RequestParam String ciudad,
            @RequestParam String modalidad,
            RedirectAttributes redirectAttributes) {

        Evento evento = new Evento();
        evento.setNombre(nombre);
        evento.setCategoria(categoria);
        evento.setFecha(fecha);
        evento.setHora(hora);
        evento.setDireccion(direccion);
        evento.setDescripcion(descripcion);
        evento.setOrganizador(organizador);
        evento.setServicios(servicios == null ? "" : servicios);
        evento.setExpositores(expositores == null ? "" : expositores);
        evento.setDestacado(destacado);
        evento.setVideojuego(videojuego);
        evento.setPais(pais);
        evento.setCiudad(ciudad);
        evento.setModalidad(modalidad);

        backendApiClient.crearEvento(session, evento);
        redirectAttributes.addFlashAttribute(
                "mensaje", "Evento creado correctamente.");
        return "redirect:/mis-eventos";
    }

    @PostMapping("/mis-eventos/{id}/media")
    public String subirMedia(
            HttpSession session,
            @PathVariable Long id,
            @RequestParam MultipartFile archivo,
            RedirectAttributes redirectAttributes) {

        backendApiClient.subirMedia(session, id, archivo);
        redirectAttributes.addFlashAttribute(
                "mensaje", "Archivo subido correctamente.");
        return "redirect:/mis-eventos";
    }

    @PostMapping("/mis-eventos/{id}/media/{mediaId}/eliminar")
    public String eliminarMedia(
            HttpSession session,
            @PathVariable Long id,
            @PathVariable Long mediaId,
            RedirectAttributes redirectAttributes) {

        backendApiClient.eliminarMedia(session, id, mediaId);
        redirectAttributes.addFlashAttribute(
                "mensaje", "Archivo eliminado.");
        return "redirect:/mis-eventos";
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
