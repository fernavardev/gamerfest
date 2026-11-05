package com.example.gamerfest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GamerfestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamerfestApplication.class, args);
	}

	@Bean
	CommandLineRunner inicializarDatos(
			EventoService eventoService,
			UsuarioInicializador usuarioInicializador) {

		return args -> {
			eventoService.inicializarEventos();
			usuarioInicializador.inicializarUsuarios();
		};
	}
}
