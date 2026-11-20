package com.example.gamerfest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final BackendApiClient backendApiClient;
    private final String backendPublicUrl;

    public HomeController(
            BackendApiClient backendApiClient,
            @Value("${backend.public-url}") String backendPublicUrl) {
        this.backendApiClient = backendApiClient;
        this.backendPublicUrl = backendPublicUrl;
    }

    @GetMapping({ "/", "/home" })
    public String home(Model model) {

        model.addAttribute("eventos",
                backendApiClient.listarEventos().stream()
                        .filter(evento -> !evento.getFecha()
                                .isBefore(LocalDate.now()))
                        .toList());

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        try {
            backendApiClient.registrarUsuario(nombre, email, password);

            return "redirect:/login?registro";

        } catch (org.springframework.web.client.HttpClientErrorException.Conflict e) {

            model.addAttribute("error", "El correo ya está registrado.");
            return "registro";
        }
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {

        PerfilUsuario perfil = backendApiClient.obtenerPerfil(session);

        model.addAttribute("perfil", perfil);
        if (perfil != null) {
            model.addAttribute("avatarUrl", urlPublica(perfil.getAvatar()));
        }

        return "perfil";
    }

    @PostMapping("/perfil")
    public String actualizarPerfil(
            HttpSession session,
            @RequestParam String nombre,
            @RequestParam String nombreUsuario,
            @RequestParam(required = false) MultipartFile avatar,
            @RequestParam(required = false) String juegosFavoritos,
            @RequestParam(defaultValue = "false") boolean notificaciones) {

        backendApiClient.actualizarPerfil(
                session,
                nombre,
                nombreUsuario,
                avatar,
                juegosFavoritos == null ? "" : juegosFavoritos,
                notificaciones);

        return "redirect:/perfil";
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
