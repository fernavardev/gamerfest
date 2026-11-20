package com.example.gamerfest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoMediaRepository eventoMediaRepository;
    private final ComentarioEventoRepository comentarioEventoRepository;
    private final AlmacenamientoService almacenamientoService;

    public EventoService(
            EventoRepository eventoRepository,
            EventoMediaRepository eventoMediaRepository,
            ComentarioEventoRepository comentarioEventoRepository,
            AlmacenamientoService almacenamientoService) {
        this.eventoRepository = eventoRepository;
        this.eventoMediaRepository = eventoMediaRepository;
        this.comentarioEventoRepository = comentarioEventoRepository;
        this.almacenamientoService = almacenamientoService;
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public List<Evento> buscar(
            String nombre,
            String videojuego,
            String categoria,
            String pais,
            String ciudad,
            LocalDate fecha,
            Modalidad modalidad) {

        return eventoRepository.findAll(EventoRepository.conFiltros(
                nombre, videojuego, categoria, pais, ciudad, fecha, modalidad));
    }

    public List<Evento> listarPorPublicador(String publicadorEmail) {
        return eventoRepository.findByPublicadorEmail(publicadorEmail);
    }

    public Optional<Evento> buscarPorId(Long id) {
        return eventoRepository.findById(id);
    }

    public Evento crear(Evento datos, String publicadorEmail) {
        Evento evento = new Evento(
                datos.getNombre(),
                datos.getCategoria(),
                datos.getFecha(),
                datos.getHora(),
                datos.getDireccion(),
                datos.getDescripcion(),
                datos.getOrganizador(),
                datos.getServicios(),
                datos.getExpositores(),
                datos.isDestacado(),
                publicadorEmail,
                datos.getVideojuego(),
                datos.getPais(),
                datos.getCiudad(),
                datos.getModalidad());

        return eventoRepository.save(evento);
    }

    public Evento exigirEvento(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento no encontrado"));
    }

    public void exigirEsDueno(Evento evento, String email) {
        if (evento.getPublicadorEmail() == null
                || !evento.getPublicadorEmail().equalsIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No tenés permiso sobre este evento");
        }
    }

    public List<EventoMedia> listarMedia(Long eventoId) {
        exigirEvento(eventoId);
        return eventoMediaRepository.findByEventoId(eventoId);
    }

    public EventoMedia agregarMedia(
            Long eventoId,
            String email,
            MultipartFile archivo) {

        Evento evento = exigirEvento(eventoId);
        exigirEsDueno(evento, email);

        String ruta = almacenamientoService.guardarMedia(archivo);
        EventoMedia media = new EventoMedia(
                evento,
                ruta,
                archivo.getOriginalFilename(),
                archivo.getContentType());

        return eventoMediaRepository.save(media);
    }

    public void eliminarMedia(Long eventoId, Long mediaId, String email) {
        Evento evento = exigirEvento(eventoId);
        exigirEsDueno(evento, email);

        EventoMedia media = eventoMediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Media no encontrada"));

        if (media.getEvento() == null
                || !media.getEvento().getId().equals(eventoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Media no encontrada");
        }

        eventoMediaRepository.delete(media);
    }

    public List<ComentarioEvento> listarComentarios(Long eventoId) {
        exigirEvento(eventoId);
        return comentarioEventoRepository.findByEventoIdOrderByCreadoEnDesc(eventoId);
    }

    public Double promedioValoracion(Long eventoId) {
        List<ComentarioEvento> comentarios = listarComentarios(eventoId);
        if (comentarios.isEmpty()) {
            return null;
        }
        double suma = comentarios.stream()
                .mapToInt(ComentarioEvento::getValoracion)
                .average()
                .orElse(0);
        return Math.round(suma * 10.0) / 10.0;
    }

    public ComentarioEvento agregarComentario(
            Long eventoId,
            String autorEmail,
            String texto,
            int valoracion) {

        if (texto == null || texto.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El comentario no puede estar vacío");
        }
        if (valoracion < 1 || valoracion > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "La valoración debe ser entre 1 y 5");
        }

        Evento evento = exigirEvento(eventoId);
        ComentarioEvento comentario = new ComentarioEvento(
                evento,
                autorEmail,
                texto.trim(),
                valoracion);

        return comentarioEventoRepository.save(comentario);
    }

    public void inicializarEventos() {
        if (eventoRepository.count() > 0) {
            return;
        }

        eventoRepository.saveAll(List.of(
                new Evento(
                        "GamerFest Santiago", "Videojuegos",
                        LocalDate.of(2026, 11, 14), LocalTime.of(10, 0),
                        "Centro de Eventos GamerFest",
                        "Torneos, videojuegos y actividades para la comunidad gamer.",
                        "GamerFest Chile",
                        "Estacionamiento disponible, acceso mediante transporte público, zona de alimentación y alojamientos cercanos",
                        "Estudios independientes y tiendas gamer", true,
                        "admin", "League of Legends", "Chile", "Santiago",
                        Modalidad.PRESENCIAL),
                new Evento(
                        "Copa Esports Chile", "Esports",
                        LocalDate.of(2026, 12, 5), LocalTime.of(12, 0),
                        "Centro de Convenciones",
                        "Competencia de equipos de videojuegos.",
                        "Liga Esports Chile",
                        "Estacionamientos cercanos, locomoción colectiva, servicios de comida y opciones de alojamiento en la ciudad",
                        "Equipos profesionales y marcas tecnológicas", true,
                        "admin", "Valorant", "Chile", "Valparaíso",
                        Modalidad.PRESENCIAL),
                new Evento(
                        "Encuentro Retro Gamer", "Retro",
                        LocalDate.of(2027, 1, 16), LocalTime.of(11, 0),
                        "Centro Cultural",
                        "Exhibición de consolas y videojuegos clásicos.",
                        "Comunidad Retro Chile",
                        "Estacionamiento público, acceso en transporte público, cafetería y alternativas de alojamiento cercanas",
                        "Coleccionistas y tiendas retro", false,
                        "admin", "Super Mario Bros", "Chile", "Concepción",
                        Modalidad.ONLINE)));
    }
}
