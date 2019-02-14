package br.com.teste.angular.security.authentication.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.angular.security.authentication.JwtLogin;
import br.com.teste.angular.security.authentication.JwtToken;
import br.com.teste.angular.security.authentication.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Gera e retorna um novo token JWT.
	 * 
	 * @param login
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 */
	@PostMapping
	public ResponseEntity<Response<JwtToken>> gerarTokenJwt(@Valid @RequestBody JwtLogin login, BindingResult result)
			throws AuthenticationException {
		Response<JwtToken> response = new Response<JwtToken>();

		if (result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		log.info("Gerando token para o email {}.", login.getEmail());
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
		JwtToken jwtToken = new JwtToken(jwtTokenUtil.obterToken(userDetails));

		for (GrantedAuthority auth : userDetails.getAuthorities()) {
			jwtToken.addRole(auth.getAuthority());
		}

		response.setData(jwtToken);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/token/expirado")
	public ResponseEntity<Response<Boolean>> verificarTokeExpirado(@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		Response<Boolean> resp = new Response<>();

		boolean valido = token != null && token.length() > 7
				&& !jwtTokenUtil.isTokenExpirado(token = token.substring(7));

		resp.setData(valido);

		if (valido) {
			return ResponseEntity.ok().body(resp);
		} else {
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Gera um novo token com uma nova data de expiração.
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
	@PostMapping(value = "/refresh")
	public ResponseEntity<Response<JwtToken>> refreshTokenJwt(HttpServletRequest request) {
		log.info("Gerando refresh token JWT.");
		Response<JwtToken> response = new Response<JwtToken>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}

		if (!token.isPresent()) {
			response.getErrors().add("Token não informado.");
		} else if (jwtTokenUtil.isTokenExpirado(token.get())) {
			response.getErrors().add("Token inválido ou expirado.");
		}

		if (!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}

		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		response.setData(new JwtToken(refreshedToken));

		return ResponseEntity.ok(response);
	}

}
