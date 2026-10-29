package com.example.gamerfest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final JwtCookieService jwtCookieService;

    public AuthController(
            AuthService authService,
            JwtService jwtService,
            JwtCookieService jwtCookieService) {

        this.authService = authService;
        this.jwtService = jwtService;
        this.jwtCookieService = jwtCookieService;
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String loginForm(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {

        Usuario usuario = authService.autenticar(username, password);
        if (usuario == null) {
            return "redirect:/login?error";
        }

        String token = jwtService.generarToken(usuario);
        jwtCookieService.escribirToken(response, token);
        return "redirect:/eventos";
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> loginJson(
            @RequestBody Map<String, String> credenciales,
            HttpServletResponse response) {

        Usuario usuario = authService.autenticar(
                credenciales.get("username"),
                credenciales.get("password"));

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }

        String token = jwtService.generarToken(usuario);
        jwtCookieService.escribirToken(response, token);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
