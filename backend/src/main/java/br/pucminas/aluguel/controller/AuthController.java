package br.pucminas.aluguel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.aluguel.dto.LoginRequest;
import br.pucminas.aluguel.dto.TokenResponse;
import br.pucminas.aluguel.dto.UsuarioResponse;
import br.pucminas.aluguel.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public TokenResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@GetMapping("/me")
	public UsuarioResponse usuarioLogado(@AuthenticationPrincipal Jwt jwt) {
		return authService.buscar(Long.valueOf(jwt.getSubject()));
	}

}
