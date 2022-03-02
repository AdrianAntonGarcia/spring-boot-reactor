package com.bolsaideas.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsaideas.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Flux<String> nombres = Flux.just("Adrián", "Ana", "Pedro").doOnNext(elemento -> System.out.println(elemento));
//		nombres.subscribe();
		Flux<Usuario> usuarios = Flux.just("Adrián", "Pepe", "Pedro", "Ana", "María")
				.map(nombre -> new Usuario(nombre.toUpperCase(), null)).doOnNext(usuario -> {
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden ser vacíos");
					} else {
						System.out.println(usuario.getNombre());
					}
				}).map(u -> {
					String nombre = u.getNombre().toLowerCase();
					u.setNombre(nombre);
					return u;
				});
//		nombres.subscribe(log::info);
		usuarios.subscribe(e -> log.info(e.getNombre()), error -> log.error(error.getMessage()), new Runnable() {
			@Override
			public void run() {
				log.info("Ha finalizado la ejecucción del observable con éxito!");
			}
		});
	}

}
