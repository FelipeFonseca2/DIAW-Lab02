package br.pucminas.aluguel.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.pucminas.aluguel.model.Usuario;

@Service
public class TokenService {

	private final JwtEncoder encoder;
	private final long expiracaoHoras;

	public TokenService(JwtEncoder encoder, @Value("${app.jwt.expiracao-horas}") long expiracaoHoras) {
		this.encoder = encoder;
		this.expiracaoHoras = expiracaoHoras;
	}

	/** O subject do token é o id do usuário; o perfil vira a role (ROLE_CLIENTE / ROLE_AGENTE). */
	public String gerar(Usuario usuario) {
		Instant agora = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("aluguel")
				.issuedAt(agora)
				.expiresAt(agora.plus(expiracaoHoras, ChronoUnit.HOURS))
				.subject(usuario.getId().toString())
				.claim("login", usuario.getLogin())
				.claim("perfil", List.of(usuario.getPerfil().name()))
				.build();
		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
		return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}

}
