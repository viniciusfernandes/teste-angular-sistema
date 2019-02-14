package br.com.teste.angular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TesteAngularAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteAngularAPIApplication.class, args);
	}
}
