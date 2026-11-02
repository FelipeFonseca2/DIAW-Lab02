package br.pucminas.aluguel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.pucminas.aluguel.exception.RegraNegocioException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido_aluguel")
public class PedidoAluguel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Cliente cliente;

	@ManyToOne(optional = false)
	private Automovel automovel;

	@Column(nullable = false)
	private LocalDate dataInicio;

	@Column(nullable = false)
	private LocalDate dataFim;

	@Column(nullable = false)
	private LocalDateTime dataCriacao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusPedido status;

	@Column(length = 500)
	private String parecer;

	@ManyToOne
	private Agente agenteAvaliador;

	@OneToOne(cascade = CascadeType.ALL)
	private Contrato contrato;

	protected PedidoAluguel() {
	}

	public PedidoAluguel(Cliente cliente, Automovel automovel, LocalDate dataInicio, LocalDate dataFim) {
		validarPeriodo(dataInicio, dataFim);
		this.cliente = cliente;
		this.automovel = automovel;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.dataCriacao = LocalDateTime.now();
		this.status = StatusPedido.EM_ANALISE;
	}

	public void modificar(Automovel automovel, LocalDate dataInicio, LocalDate dataFim) {
		exigirStatus(StatusPedido.EM_ANALISE, "Somente pedidos em análise podem ser modificados.");
		validarPeriodo(dataInicio, dataFim);
		this.automovel = automovel;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public void cancelar() {
		if (status != StatusPedido.EM_ANALISE && status != StatusPedido.APROVADO) {
			throw new RegraNegocioException("Somente pedidos em análise ou aprovados podem ser cancelados.");
		}
		this.status = StatusPedido.CANCELADO;
	}

	/** Parecer financeiro do agente: pedidos aprovados ficam disponíveis para execução do contrato. */
	public void avaliar(Agente agente, boolean aprovado, String parecer) {
		exigirStatus(StatusPedido.EM_ANALISE, "Somente pedidos em análise podem ser avaliados.");
		this.agenteAvaliador = agente;
		this.parecer = parecer;
		this.status = aprovado ? StatusPedido.APROVADO : StatusPedido.REPROVADO;
	}

	public void executarContrato(Agente agente, TipoProprietario tipoProprietario, ContratoCredito credito) {
		exigirStatus(StatusPedido.APROVADO, "Somente pedidos aprovados podem ter o contrato executado.");
		if (tipoProprietario != TipoProprietario.CLIENTE && tipoProprietario != agente.getTipo()) {
			throw new RegraNegocioException("O automóvel só pode ser registrado em nome do cliente ou do próprio agente.");
		}
		if (credito != null && credito.getBanco() != agente) {
			throw new RegraNegocioException("O crédito deve ser concedido pelo banco que executa o contrato.");
		}
		this.contrato = new Contrato(agente, tipoProprietario, credito);
		this.automovel.setProprietario(tipoProprietario == TipoProprietario.CLIENTE ? cliente : agente);
		this.status = StatusPedido.CONTRATADO;
	}

	private void exigirStatus(StatusPedido esperado, String mensagem) {
		if (status != esperado) {
			throw new RegraNegocioException(mensagem);
		}
	}

	private static void validarPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		if (dataFim.isBefore(dataInicio)) {
			throw new RegraNegocioException("A data de fim deve ser igual ou posterior à data de início.");
		}
	}

	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Automovel getAutomovel() {
		return automovel;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public String getParecer() {
		return parecer;
	}

	public Agente getAgenteAvaliador() {
		return agenteAvaliador;
	}

	public Contrato getContrato() {
		return contrato;
	}

}
