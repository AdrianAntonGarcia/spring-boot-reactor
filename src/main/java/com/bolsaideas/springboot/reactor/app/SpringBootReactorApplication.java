package com.bolsaideas.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		Flux<String> nombres = Flux.just("Adrián", "Pepe", "Pedro", "Ana", "María").doOnNext(e -> {
			if (e.isEmpty()) {
				throw new RuntimeException("Nombres no pueden ser vacíos");
			} else {
				System.out.println(e);
			}
		});
//		nombres.subscribe(log::info);
		nombres.subscribe(e -> log.info(e), error -> log.error(error.getMessage()), new Runnable() {
			@Override
			public void run() {
				log.info("Ha finalizado la ejecucción del observable con éxito!");
			}
		});
	}

}
