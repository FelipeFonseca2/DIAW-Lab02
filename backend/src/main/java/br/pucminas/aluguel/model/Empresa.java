package br.pucminas.aluguel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa extends Agente {

	@Override
	public TipoProprietario getTipo() {
		return TipoProprietario.EMPRESA;
	}

}
