package com.example.gamerfest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final BackendApiClient backendApiClient;

    public AdminController(BackendApiClient backendApiClient) {
        this.backendApiClient = backendApiClient;
    }

    @GetMapping("/admin")
    public String panelAdministrativo(
            HttpSession session, Model model) {

        try {
            model.addAttribute("usuarios",
                    backendApiClient.listarUsuarios(session));

            return "admin";

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/admin/usuarios/{id}/rol")
    public String cambiarRol(
            @PathVariable Long id,
            @RequestParam String rol,
            HttpSession session) {

        try {
            backendApiClient.cambiarRol(session, id, rol);

            return "redirect:/admin";

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}