package br.com.gabriel.desafiofipe;

import br.com.gabriel.desafiofipe.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafiofipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafiofipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.start();
	}
}
