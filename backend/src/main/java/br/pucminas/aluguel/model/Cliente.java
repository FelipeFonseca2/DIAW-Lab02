package br.pucminas.aluguel.model;

import java.util.ArrayList;
import java.util.List;

import br.pucminas.aluguel.exception.RegraNegocioException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {

	public static final int MAX_RENDIMENTOS = 3;

	@Column(nullable = false, length = 20)
	private String rg;

	@Column(nullable = false, unique = true, length = 14)
	private String cpf;

	@Column(nullable = false)
	private String endereco;

	@Column(length = 80)
	private String profissao;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "cliente_rendimento", joinColumns = @JoinColumn(name = "cliente_id"))
	private List<Rendimento> rendimentos = new ArrayList<>();

	@Override
	public Perfil getPerfil() {
		return Perfil.CLIENTE;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public List<Rendimento> getRendimentos() {
		return List.copyOf(rendimentos);
	}

	public void setRendimentos(List<Rendimento> rendimentos) {
		if (rendimentos.size() > MAX_RENDIMENTOS) {
			throw new RegraNegocioException("O cliente pode ter no máximo " + MAX_RENDIMENTOS + " rendimentos.");
		}
		this.rendimentos.clear();
		this.rendimentos.addAll(rendimentos);
	}

}
