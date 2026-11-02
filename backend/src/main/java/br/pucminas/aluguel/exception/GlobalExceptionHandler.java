package br.pucminas.aluguel.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RegraNegocioException.class)
	public ResponseEntity<Map<String, String>> regraNegocio(RegraNegocioException e) {
		return erro(HttpStatus.UNPROCESSABLE_CONTENT, e.getMessage());
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> naoEncontrado(RecursoNaoEncontradoException e) {
		return erro(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, String>> credenciais(BadCredentialsException e) {
		return erro(HttpStatus.UNAUTHORIZED, e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, String>> acessoNegado(AccessDeniedException e) {
		return erro(HttpStatus.FORBIDDEN, "Acesso negado.");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> validacao(MethodArgumentNotValidException e) {
		String mensagem = e.getBindingResult().getFieldErrors().stream()
				.map(f -> f.getField() + ": " + f.getDefaultMessage())
				.collect(Collectors.joining("; "));
		return erro(HttpStatus.BAD_REQUEST, mensagem);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> integridade(DataIntegrityViolationException e) {
		return erro(HttpStatus.CONFLICT, "Registro duplicado ou em uso por outro cadastro.");
	}

	private ResponseEntity<Map<String, String>> erro(HttpStatus status, String mensagem) {
		return ResponseEntity.status(status).body(Map.of("mensagem", mensagem));
	}

}
