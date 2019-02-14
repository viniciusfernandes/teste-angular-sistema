package br.com.teste.angular.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class TesteAngularZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteAngularZuulApplication.class, args);
	}

}

