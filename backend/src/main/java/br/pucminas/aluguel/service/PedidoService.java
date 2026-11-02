package br.pucminas.aluguel.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pucminas.aluguel.dto.AvaliacaoRequest;
import br.pucminas.aluguel.dto.ContratoRequest;
import br.pucminas.aluguel.dto.CreditoRequest;
import br.pucminas.aluguel.dto.PedidoRequest;
import br.pucminas.aluguel.dto.PedidoResponse;
import br.pucminas.aluguel.exception.RecursoNaoEncontradoException;
import br.pucminas.aluguel.exception.RegraNegocioException;
import br.pucminas.aluguel.model.Agente;
import br.pucminas.aluguel.model.Automovel;
import br.pucminas.aluguel.model.Banco;
import br.pucminas.aluguel.model.Cliente;
import br.pucminas.aluguel.model.ContratoCredito;
import br.pucminas.aluguel.model.PedidoAluguel;
import br.pucminas.aluguel.model.Usuario;
import br.pucminas.aluguel.repository.AutomovelRepository;
import br.pucminas.aluguel.repository.PedidoAluguelRepository;
import br.pucminas.aluguel.repository.UsuarioRepository;

@Service
public class PedidoService {

	private final PedidoAluguelRepository pedidoRepository;
	private final AutomovelRepository automovelRepository;
	private final UsuarioRepository usuarioRepository;

	public PedidoService(PedidoAluguelRepository pedidoRepository, AutomovelRepository automovelRepository,
			UsuarioRepository usuarioRepository) {
		this.pedidoRepository = pedidoRepository;
		this.automovelRepository = automovelRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	public PedidoResponse criar(Long clienteId, PedidoRequest request) {
		if (!(buscarUsuario(clienteId) instanceof Cliente cliente)) {
			throw new AccessDeniedException("Somente clientes podem criar pedidos.");
		}
		PedidoAluguel pedido = new PedidoAluguel(cliente, buscarAutomovel(request.automovelId()),
				request.dataInicio(), request.dataFim());
		return PedidoResponse.de(pedidoRepository.save(pedido));
	}

	/** Clientes veem apenas os próprios pedidos; agentes veem todos. */
	@Transactional(readOnly = true)
	public List<PedidoResponse> listar(Long usuarioId) {
		Usuario usuario = buscarUsuario(usuarioId);
		List<PedidoAluguel> pedidos = usuario instanceof Cliente
				? pedidoRepository.findByClienteIdOrderByDataCriacaoDesc(usuarioId)
				: pedidoRepository.findAllByOrderByDataCriacaoDesc();
		return pedidos.stream().map(PedidoResponse::de).toList();
	}

	@Transactional(readOnly = true)
	public PedidoResponse buscar(Long id, Long usuarioId) {
		return PedidoResponse.de(buscarComAcesso(id, usuarioId));
	}

	@Transactional
	public PedidoResponse modificar(Long id, Long usuarioId, PedidoRequest request) {
		PedidoAluguel pedido = buscarComAcesso(id, usuarioId);
		pedido.modificar(buscarAutomovel(request.automovelId()), request.dataInicio(), request.dataFim());
		return PedidoResponse.de(pedido);
	}

	@Transactional
	public PedidoResponse cancelar(Long id, Long clienteId) {
		PedidoAluguel pedido = buscarComAcesso(id, clienteId);
		pedido.cancelar();
		return PedidoResponse.de(pedido);
	}

	@Transactional
	public PedidoResponse avaliar(Long id, Long agenteId, AvaliacaoRequest request) {
		PedidoAluguel pedido = buscarPedido(id);
		pedido.avaliar(buscarAgente(agenteId), request.aprovado(), request.parecer());
		return PedidoResponse.de(pedido);
	}

	@Transactional
	public PedidoResponse executarContrato(Long id, Long agenteId, ContratoRequest request) {
		PedidoAluguel pedido = buscarPedido(id);
		Agente agente = buscarAgente(agenteId);
		pedido.executarContrato(agente, request.tipoProprietario(), criarCredito(agente, request.credito()));
		return PedidoResponse.de(pedidoRepository.saveAndFlush(pedido));
	}

	private ContratoCredito criarCredito(Agente agente, CreditoRequest credito) {
		if (credito == null) {
			return null;
		}
		if (!(agente instanceof Banco banco)) {
			throw new RegraNegocioException("Contratos de crédito só podem ser concedidos por bancos.");
		}
		return new ContratoCredito(banco, credito.valor(), credito.taxaJurosMensal(), credito.prazoMeses());
	}

	private PedidoAluguel buscarComAcesso(Long id, Long usuarioId) {
		PedidoAluguel pedido = buscarPedido(id);
		Usuario usuario = buscarUsuario(usuarioId);
		if (usuario instanceof Cliente && !pedido.getCliente().getId().equals(usuarioId)) {
			throw new AccessDeniedException("Acesso negado.");
		}
		return pedido;
	}

	private PedidoAluguel buscarPedido(Long id) {
		return pedidoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado."));
	}

	private Automovel buscarAutomovel(Long id) {
		return automovelRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Automóvel não encontrado."));
	}

	private Agente buscarAgente(Long id) {
		if (!(buscarUsuario(id) instanceof Agente agente)) {
			throw new AccessDeniedException("Somente agentes podem executar esta ação.");
		}
		return agente;
	}

	private Usuario buscarUsuario(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
	}

}
