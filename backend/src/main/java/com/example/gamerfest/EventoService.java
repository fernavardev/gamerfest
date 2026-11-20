package com.example.gamerfest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
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

    public Optional<Evento> buscarPorId(Long id) {
        return eventoRepository.findById(id);
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
