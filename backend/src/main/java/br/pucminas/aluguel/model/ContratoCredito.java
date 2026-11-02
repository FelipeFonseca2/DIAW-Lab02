package br.pucminas.aluguel.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** Crédito concedido por um banco agente e associado a um contrato de aluguel. */
@Entity
@Table(name = "contrato_credito")
public class ContratoCredito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Banco banco;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal valor;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal taxaJurosMensal;

	@Column(nullable = false)
	private Integer prazoMeses;

	protected ContratoCredito() {
	}

	public ContratoCredito(Banco banco, BigDecimal valor, BigDecimal taxaJurosMensal, Integer prazoMeses) {
		this.banco = banco;
		this.valor = valor;
		this.taxaJurosMensal = taxaJurosMensal;
		this.prazoMeses = prazoMeses;
	}

	public Long getId() {
		return id;
	}

	public Banco getBanco() {
		return banco;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public BigDecimal getTaxaJurosMensal() {
		return taxaJurosMensal;
	}

	public Integer getPrazoMeses() {
		return prazoMeses;
	}

}
