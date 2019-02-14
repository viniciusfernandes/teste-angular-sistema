package br.com.teste.angular.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.teste.angular.security.authentication.SenhaUtils;
import br.com.teste.angular.security.authentication.entity.Usuario;
import br.com.teste.angular.security.authentication.entity.contant.PerfilEnum;
import br.com.teste.angular.security.authentication.service.UsuarioService;

@SpringBootApplication
public class TesteAngularAuthenticationApplication {

	@Autowired
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(TesteAngularAuthenticationApplication.class, args);
	}

	public CommandLineRunner commandLineRunner() {
		return args -> {

			usuarioService.deleteAll();
			
			Usuario usuario = new Usuario();
			usuario.setEmail("usuario@email.com");
			usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
			usuario.setSenha(SenhaUtils.gerarBCrypt("123456"));
			usuarioService.inserir(usuario);

			Usuario admin = new Usuario();
			admin.setEmail("admin@email.com");
			admin.setPerfil(PerfilEnum.ROLE_ADMIN);
			admin.setSenha(SenhaUtils.gerarBCrypt("123456"));
			usuarioService.inserir(admin);

		};
	}
}
