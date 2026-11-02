package br.pucminas.aluguel.dto;

import java.util.List;

import br.pucminas.aluguel.model.Cliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** A senha é obrigatória no cadastro e opcional na atualização (vazia mantém a atual). */
public record ClienteRequest(
		@NotBlank @Size(max = 60) String login,
		@Size(max = 72) String senha,
		@NotBlank @Size(max = 120) String nome,
		@NotBlank @Size(max = 20) String rg,
		@NotBlank @Size(max = 14) String cpf,
		@NotBlank @Size(max = 255) String endereco,
		@Size(max = 80) String profissao,
		@Valid @Size(max = Cliente.MAX_RENDIMENTOS) List<RendimentoDTO> rendimentos) {
}
