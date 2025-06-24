package br.com.flavio.microservice.fornecedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class FornecdorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FornecdorApplication.class, args);
	}

}
