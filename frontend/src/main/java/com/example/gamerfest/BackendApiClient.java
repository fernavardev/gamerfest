package com.example.gamerfest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;

@Component
public class BackendApiClient {

    private final RestClient restClient;
    private final TokenStore tokenStore;

    public BackendApiClient(
            RestClient.Builder restClientBuilder,
            @Value("${backend.url}") String backendUrl,
            TokenStore tokenStore) {

        this.restClient = restClientBuilder
                .baseUrl(backendUrl)
                .build();

        this.tokenStore = tokenStore;
    }

    public List<Evento> listarEventos() {

        Evento[] eventos = restClient.get()
                .uri("/api/eventos")
                .retrieve()
                .body(Evento[].class);

        return eventos == null ? List.of() : Arrays.asList(eventos);
    }

    public Evento buscarEventoPorId(Long id) {

        try {
            return restClient.get()
                    .uri("/api/eventos/{id}", id)
                    .retrieve()
                    .body(Evento.class);

        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public String login(String username, String password) {

        Map<?, ?> respuesta = restClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "username", username,
                        "password", password))
                .retrieve()
                .body(Map.class);

        if (respuesta == null || respuesta.get("token") == null) {
            throw new IllegalStateException(
                    "El backend no devolvió un token JWT.");
        }

        return respuesta.get("token").toString();
    }

    public void registrarUsuario(
            String nombre,
            String email,
            String password) {

        restClient.post()
                .uri("/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "nombre", nombre,
                        "email", email,
                        "password", password))
                .retrieve()
                .toBodilessEntity();
    }

    public PerfilUsuario obtenerPerfil(HttpSession session) {

        String token = tokenStore.requireToken(session);

        return restClient.get()
                .uri("/api/perfil")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(PerfilUsuario.class);
    }

    public void actualizarPerfil(
            HttpSession session,
            String nombre,
            String nombreUsuario,
            String avatar,
            String juegosFavoritos,
            boolean notificaciones) {

        String token = tokenStore.requireToken(session);

        restClient.post()
                .uri("/api/perfil")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "nombre", nombre,
                        "nombreUsuario", nombreUsuario,
                        "avatar", avatar,
                        "juegosFavoritos", juegosFavoritos,
                        "notificaciones", notificaciones))
                .retrieve()
                .toBodilessEntity();
    }
}