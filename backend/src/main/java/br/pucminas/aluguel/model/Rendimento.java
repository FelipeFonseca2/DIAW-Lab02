package br.pucminas.aluguel.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Rendimento {

	@Column(nullable = false, length = 120)
	private String entidadeEmpregadora;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal valor;

	protected Rendimento() {
	}

	public Rendimento(String entidadeEmpregadora, BigDecimal valor) {
		this.entidadeEmpregadora = entidadeEmpregadora;
		this.valor = valor;
	}

	public String getEntidadeEmpregadora() {
		return entidadeEmpregadora;
	}

	public BigDecimal getValor() {
		return valor;
	}

}
