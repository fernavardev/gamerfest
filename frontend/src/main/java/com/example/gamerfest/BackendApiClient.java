package com.example.gamerfest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

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
        return listarEventos(null, null, null, null, null, null, null);
    }

    public List<Evento> listarEventos(
            String nombre,
            String videojuego,
            String categoria,
            String pais,
            String ciudad,
            LocalDate fecha,
            String modalidad) {

        Evento[] eventos = restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/api/eventos");
                    if (nombre != null && !nombre.isBlank()) {
                        uriBuilder.queryParam("nombre", nombre.trim());
                    }
                    if (videojuego != null && !videojuego.isBlank()) {
                        uriBuilder.queryParam("videojuego", videojuego.trim());
                    }
                    if (categoria != null && !categoria.isBlank()) {
                        uriBuilder.queryParam("categoria", categoria.trim());
                    }
                    if (pais != null && !pais.isBlank()) {
                        uriBuilder.queryParam("pais", pais.trim());
                    }
                    if (ciudad != null && !ciudad.isBlank()) {
                        uriBuilder.queryParam("ciudad", ciudad.trim());
                    }
                    if (fecha != null) {
                        uriBuilder.queryParam("fecha", fecha.toString());
                    }
                    if (modalidad != null && !modalidad.isBlank()) {
                        uriBuilder.queryParam("modalidad", modalidad.trim());
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .body(Evento[].class);

        return eventos == null ? List.of() : Arrays.asList(eventos);
    }

    public Evento buscarEventoPorId(HttpSession session, Long id) {

        String token = tokenStore.requireToken(session);

        try {
            return restClient.get()
                    .uri("/api/eventos/{id}", id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
            MultipartFile avatar,
            String juegosFavoritos,
            boolean notificaciones) {

        String token = tokenStore.requireToken(session);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("nombre", nombre);
        body.add("nombreUsuario", nombreUsuario);
        body.add("juegosFavoritos", juegosFavoritos == null ? "" : juegosFavoritos);
        body.add("notificaciones", String.valueOf(notificaciones));

        if (avatar != null && !avatar.isEmpty()) {
            try {
                ByteArrayResource recurso = new ByteArrayResource(
                        avatar.getBytes()) {
                    @Override
                    public String getFilename() {
                        return avatar.getOriginalFilename();
                    }
                };
                body.add("avatar", recurso);
            } catch (java.io.IOException e) {
                throw new IllegalStateException(
                        "No se pudo leer el archivo de avatar", e);
            }
        }

        restClient.post()
                .uri("/api/perfil")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}