package br.com.teste.angular.eureka.testeangulareureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TesteAngularEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteAngularEurekaApplication.class, args);
	}

}

