package br.com.alura.projetofipe.demo;

import br.com.alura.projetofipe.demo.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetofipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjetofipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
