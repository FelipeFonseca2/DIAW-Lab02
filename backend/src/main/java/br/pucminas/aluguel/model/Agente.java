package br.pucminas.aluguel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** Agentes (empresas e bancos) modificam e avaliam os pedidos de aluguel. */
@Entity
@Table(name = "agente")
public abstract class Agente extends Usuario {

	@Column(nullable = false, unique = true, length = 18)
	private String cnpj;

	@Override
	public Perfil getPerfil() {
		return Perfil.AGENTE;
	}

	/** Tipo de proprietário que este agente representa em um contrato. */
	public abstract TipoProprietario getTipo();

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
