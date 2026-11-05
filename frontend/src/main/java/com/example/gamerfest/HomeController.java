package com.example.gamerfest;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final BackendApiClient backendApiClient;

    public HomeController(BackendApiClient backendApiClient) {
        this.backendApiClient = backendApiClient;
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

        return "perfil";
    }

    @PostMapping("/perfil")
    public String actualizarPerfil(
            HttpSession session,
            @RequestParam String nombre,
            @RequestParam String nombreUsuario,
            @RequestParam String avatar,
            @RequestParam String juegosFavoritos,
            @RequestParam(defaultValue = "false") boolean notificaciones) {

        backendApiClient.actualizarPerfil(
                session,
                nombre,
                nombreUsuario,
                avatar,
                juegosFavoritos,
                notificaciones);

        return "redirect:/perfil";
    }

}
