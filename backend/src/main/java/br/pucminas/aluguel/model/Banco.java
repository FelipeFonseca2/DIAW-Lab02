package br.pucminas.aluguel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "banco")
public class Banco extends Agente {

	@Override
	public TipoProprietario getTipo() {
		return TipoProprietario.BANCO;
	}

}
