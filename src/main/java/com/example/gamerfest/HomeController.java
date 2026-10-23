package com.example.gamerfest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDate;

@Controller
public class HomeController {

    private final InMemoryUserDetailsManager usuarios;
    private final PasswordEncoder passwordEncoder;
    private final PerfilService perfilService;

    public HomeController(InMemoryUserDetailsManager usuarios,
                        PasswordEncoder passwordEncoder,
                        PerfilService perfilService) {
        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
        this.perfilService = perfilService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        model.addAttribute("eventos",
                EventoController.eventos.stream()
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

        if (usuarios.userExists(email)) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "registro";
        }

        UserDetails nuevoUsuario = User.withUsername(email)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();

        usuarios.createUser(nuevoUsuario);
        PerfilUsuario nuevoPerfil = new PerfilUsuario(email, nombre);
        perfilService.guardar(nuevoPerfil);

        return "redirect:/login?registro";
    }

    @GetMapping("/perfil")
    public String perfil(Authentication authentication, Model model) {

        String email = authentication.getName();
        PerfilUsuario perfil = perfilService.buscarPorEmail(email);

        model.addAttribute("perfil", perfil);

        return "perfil";
    }
    
    @PostMapping("/perfil")
    public String actualizarPerfil(
            Authentication authentication,
            @RequestParam String nombre,
            @RequestParam String nombreUsuario,
            @RequestParam String avatar,
            @RequestParam String juegosFavoritos,
            @RequestParam(defaultValue = "false") boolean notificaciones) {

        String email = authentication.getName();

        PerfilUsuario perfil = perfilService.buscarPorEmail(email);

        if (perfil == null) {
            return "redirect:/perfil";
        }

        perfil.setNombre(nombre);
        perfil.setNombreUsuario(nombreUsuario);
        perfil.setAvatar(avatar);
        perfil.setJuegosFavoritos(juegosFavoritos);
        perfil.setNotificaciones(notificaciones);

        perfilService.guardar(perfil);

        return "redirect:/perfil";
    }

}
