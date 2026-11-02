package br.pucminas.aluguel.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pucminas.aluguel.dto.LoginRequest;
import br.pucminas.aluguel.dto.TokenResponse;
import br.pucminas.aluguel.dto.UsuarioResponse;
import br.pucminas.aluguel.exception.RecursoNaoEncontradoException;
import br.pucminas.aluguel.model.Usuario;
import br.pucminas.aluguel.repository.UsuarioRepository;

@Service
public class AuthService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
	}

	@Transactional(readOnly = true)
	public TokenResponse login(LoginRequest request) {
		Usuario usuario = usuarioRepository.findByLogin(request.login())
				.filter(u -> passwordEncoder.matches(request.senha(), u.getSenha()))
				.orElseThrow(() -> new BadCredentialsException("Usuário ou senha inválidos."));
		return new TokenResponse(tokenService.gerar(usuario), UsuarioResponse.de(usuario));
	}

	@Transactional(readOnly = true)
	public UsuarioResponse buscar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.map(UsuarioResponse::de)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
	}

}
