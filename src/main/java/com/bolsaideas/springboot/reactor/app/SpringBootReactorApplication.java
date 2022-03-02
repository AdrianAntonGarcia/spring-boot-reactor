package com.bolsaideas.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

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
		Flux<String> names = Flux.just("Adrián Antón", "Pepe Pep", "Pedro fulano", "Ana Romeu", "María Mar",
				"Bruce Lee", "Bruce Willis");
		Flux<Usuario> users = names
				.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
				.filter(usuario -> usuario.getNombre().equalsIgnoreCase("bruce")).doOnNext(usuario -> {
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
		users.subscribe(e -> log.info(e.toString()), error -> log.error(error.getMessage()), new Runnable() {
			@Override
			public void run() {
				log.info("Ha finalizado la ejecucción del observable con éxito!");
			}
		});

		List<String> usuarios = new ArrayList<String>();
		usuarios.add("Adrián Antón");
		usuarios.add("Pepe Pep");
		usuarios.add("Pedro fulano");
		usuarios.add("Ana Romeu");
		usuarios.add("María Mar");
		usuarios.add("Bruce Lee");
		usuarios.add("Bruce Willis");

		Flux<String> nombres = Flux.fromIterable(usuarios);

	}

}
