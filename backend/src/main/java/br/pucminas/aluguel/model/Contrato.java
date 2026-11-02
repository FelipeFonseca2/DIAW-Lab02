package br.pucminas.aluguel.model;

import java.time.LocalDate;

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
@Table(name = "contrato")
public class Contrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Agente agente;

	@Column(nullable = false)
	private LocalDate dataAssinatura;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoProprietario tipoProprietario;

	@OneToOne(cascade = CascadeType.ALL)
	private ContratoCredito contratoCredito;

	protected Contrato() {
	}

	public Contrato(Agente agente, TipoProprietario tipoProprietario, ContratoCredito contratoCredito) {
		this.agente = agente;
		this.tipoProprietario = tipoProprietario;
		this.contratoCredito = contratoCredito;
		this.dataAssinatura = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public Agente getAgente() {
		return agente;
	}

	public LocalDate getDataAssinatura() {
		return dataAssinatura;
	}

	public TipoProprietario getTipoProprietario() {
		return tipoProprietario;
	}

	public ContratoCredito getContratoCredito() {
		return contratoCredito;
	}

}
