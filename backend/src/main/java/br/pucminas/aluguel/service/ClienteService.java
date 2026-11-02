package br.pucminas.aluguel.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pucminas.aluguel.dto.ClienteRequest;
import br.pucminas.aluguel.dto.ClienteResponse;
import br.pucminas.aluguel.dto.RendimentoDTO;
import br.pucminas.aluguel.exception.RecursoNaoEncontradoException;
import br.pucminas.aluguel.exception.RegraNegocioException;
import br.pucminas.aluguel.model.Cliente;
import br.pucminas.aluguel.model.StatusPedido;
import br.pucminas.aluguel.model.Usuario;
import br.pucminas.aluguel.repository.ClienteRepository;
import br.pucminas.aluguel.repository.PedidoAluguelRepository;
import br.pucminas.aluguel.repository.UsuarioRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final UsuarioRepository usuarioRepository;
	private final PedidoAluguelRepository pedidoRepository;
	private final PasswordEncoder passwordEncoder;

	public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository,
			PedidoAluguelRepository pedidoRepository, PasswordEncoder passwordEncoder) {
		this.clienteRepository = clienteRepository;
		this.usuarioRepository = usuarioRepository;
		this.pedidoRepository = pedidoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public ClienteResponse cadastrar(ClienteRequest request) {
		if (request.senha() == null || request.senha().isBlank()) {
			throw new RegraNegocioException("A senha é obrigatória.");
		}
		if (usuarioRepository.existsByLogin(request.login())) {
			throw new RegraNegocioException("Login já está em uso.");
		}
		if (clienteRepository.existsByCpf(request.cpf())) {
			throw new RegraNegocioException("CPF já cadastrado.");
		}
		Cliente cliente = new Cliente();
		preencher(cliente, request);
		cliente.setSenha(passwordEncoder.encode(request.senha()));
		return ClienteResponse.de(clienteRepository.save(cliente));
	}

	@Transactional(readOnly = true)
	public List<ClienteResponse> listar() {
		return clienteRepository.findAllByOrderByNomeAsc().stream().map(ClienteResponse::de).toList();
	}

	/** Agentes consultam qualquer cliente; um cliente consulta apenas o próprio cadastro. */
	@Transactional(readOnly = true)
	public ClienteResponse buscar(Long id, Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
		if (usuario instanceof Cliente && !usuario.getId().equals(id)) {
			throw new AccessDeniedException("Acesso negado.");
		}
		return ClienteResponse.de(buscarCliente(id));
	}

	@Transactional
	public ClienteResponse atualizar(Long id, Long usuarioId, ClienteRequest request) {
		exigirProprioCadastro(id, usuarioId);
		Cliente cliente = buscarCliente(id);
		if (usuarioRepository.existsByLoginAndIdNot(request.login(), id)) {
			throw new RegraNegocioException("Login já está em uso.");
		}
		if (clienteRepository.existsByCpfAndIdNot(request.cpf(), id)) {
			throw new RegraNegocioException("CPF já cadastrado.");
		}
		preencher(cliente, request);
		if (request.senha() != null && !request.senha().isBlank()) {
			cliente.setSenha(passwordEncoder.encode(request.senha()));
		}
		return ClienteResponse.de(cliente);
	}

	@Transactional
	public void excluir(Long id, Long usuarioId) {
		exigirProprioCadastro(id, usuarioId);
		Cliente cliente = buscarCliente(id);
		if (pedidoRepository.existsByClienteIdAndStatus(id, StatusPedido.CONTRATADO)) {
			throw new RegraNegocioException("Clientes com contratos executados não podem ser excluídos.");
		}
		pedidoRepository.deleteByClienteId(id);
		clienteRepository.delete(cliente);
	}

	private void preencher(Cliente cliente, ClienteRequest request) {
		cliente.setLogin(request.login());
		cliente.setNome(request.nome());
		cliente.setRg(request.rg());
		cliente.setCpf(request.cpf());
		cliente.setEndereco(request.endereco());
		cliente.setProfissao(request.profissao());
		List<RendimentoDTO> rendimentos = request.rendimentos() == null ? List.of() : request.rendimentos();
		cliente.setRendimentos(rendimentos.stream().map(RendimentoDTO::toModel).toList());
	}

	private void exigirProprioCadastro(Long id, Long usuarioId) {
		if (!id.equals(usuarioId)) {
			throw new AccessDeniedException("Acesso negado.");
		}
	}

	private Cliente buscarCliente(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));
	}

}
