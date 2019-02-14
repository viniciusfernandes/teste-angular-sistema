package br.com.teste.angular.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "br.com.teste.angular.client.service.client" })
public class TesteAngularClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteAngularClientApplication.class, args);
	}

}
