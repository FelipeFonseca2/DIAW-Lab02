package br.pucminas.aluguel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.aluguel.dto.AvaliacaoRequest;
import br.pucminas.aluguel.dto.ContratoRequest;
import br.pucminas.aluguel.dto.PedidoRequest;
import br.pucminas.aluguel.dto.PedidoResponse;
import br.pucminas.aluguel.service.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@PostMapping
	@PreAuthorize("hasRole('CLIENTE')")
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoResponse criar(@Valid @RequestBody PedidoRequest request, @AuthenticationPrincipal Jwt jwt) {
		return pedidoService.criar(Long.valueOf(jwt.getSubject()), request);
	}

	@GetMapping
	public List<PedidoResponse> listar(@AuthenticationPrincipal Jwt jwt) {
		return pedidoService.listar(Long.valueOf(jwt.getSubject()));
	}

	@GetMapping("/{id}")
	public PedidoResponse buscar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
		return pedidoService.buscar(id, Long.valueOf(jwt.getSubject()));
	}

	/** Clientes e agentes podem modificar pedidos ainda em análise. */
	@PutMapping("/{id}")
	public PedidoResponse modificar(@PathVariable Long id, @Valid @RequestBody PedidoRequest request,
			@AuthenticationPrincipal Jwt jwt) {
		return pedidoService.modificar(id, Long.valueOf(jwt.getSubject()), request);
	}

	@PostMapping("/{id}/cancelamento")
	@PreAuthorize("hasRole('CLIENTE')")
	public PedidoResponse cancelar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
		return pedidoService.cancelar(id, Long.valueOf(jwt.getSubject()));
	}

	@PostMapping("/{id}/avaliacao")
	@PreAuthorize("hasRole('AGENTE')")
	public PedidoResponse avaliar(@PathVariable Long id, @Valid @RequestBody AvaliacaoRequest request,
			@AuthenticationPrincipal Jwt jwt) {
		return pedidoService.avaliar(id, Long.valueOf(jwt.getSubject()), request);
	}

	@PostMapping("/{id}/contrato")
	@PreAuthorize("hasRole('AGENTE')")
	public PedidoResponse executarContrato(@PathVariable Long id, @Valid @RequestBody ContratoRequest request,
			@AuthenticationPrincipal Jwt jwt) {
		return pedidoService.executarContrato(id, Long.valueOf(jwt.getSubject()), request);
	}

}
