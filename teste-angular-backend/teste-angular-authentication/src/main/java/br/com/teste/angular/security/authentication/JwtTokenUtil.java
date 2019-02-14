package br.com.teste.angular.security.authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_CREATED = "created";
	static final String CLAIM_KEY_TYPE = "typ";
	static final String CLAIM_KEY_ISSUE = "iss";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * Obtém o username (email) contido no token JWT.
	 * 
	 * @param token
	 * @return String
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * Retorna a data de expiração de um token JWT.
	 * 
	 * @param token
	 * @return Date
	 * @throws JwtClaimExtractionException
	 */
	private Date getExpirationDateFromToken(String token) throws JwtClaimExtractionException {
		return getClaimsFromToken(token).getExpiration();
	}

	/**
	 * Cria um novo token (refresh).
	 * 
	 * @param token
	 * @return String
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			refreshedToken = gerarToken(claims.getSubject(), claims.get("role", String.class));
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * Retorna um novo token JWT com base nos dados do usuários.
	 * 
	 * @param userDetails
	 * @return String
	 */
	public String obterToken(UserDetails userDetails) {
		final String role = userDetails.getAuthorities().iterator().next().getAuthority();
		return gerarToken(userDetails.getUsername(), role);
	}

	/**
	 * Realiza o parse do token JWT para extrair as informações contidas no corpo
	 * dele.
	 * 
	 * @param token
	 * @return Claims
	 */
	private Claims getClaimsFromToken(String token) throws JwtClaimExtractionException {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new JwtClaimExtractionException("Falha parse do claim do token", e);
		}
	}

	/**
	 * Retorna a data de expiração com base na data atual.
	 * 
	 * @return Date
	 */
	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * Verifica se um token JTW está expirado.
	 * 
	 * @param token
	 * @return boolean
	 */
	public boolean isTokenExpirado(String token) {
		Date dataExpiracao;
		try {
			dataExpiracao = this.getExpirationDateFromToken(token);
		} catch (JwtClaimExtractionException e) {
			return true;
		}

		if (dataExpiracao == null) {
			return false;
		}
		return dataExpiracao.before(new Date());
	}

	/**
	 * Gera um novo token JWT contendo os dados (claims) fornecidos.
	 * 
	 * @param claims
	 * @return String
	 */
	private String gerarToken(String email, String role) {
		Map<String, Object> header = new HashMap<>();
		header.put("typ", "JWT");

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		claims.put("iat", new Date());

		return Jwts.builder().setHeader(header).setClaims(claims).setIssuer("teste-angular").setSubject(email)
				.setExpiration(gerarDataExpiracao()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

}
