package br.pucminas.aluguel.dto;

import java.util.List;

import br.pucminas.aluguel.model.Cliente;

public record ClienteResponse(
		Long id,
		String login,
		String nome,
		String rg,
		String cpf,
		String endereco,
		String profissao,
		List<RendimentoDTO> rendimentos) {

	public static ClienteResponse de(Cliente cliente) {
		return new ClienteResponse(cliente.getId(), cliente.getLogin(), cliente.getNome(), cliente.getRg(),
				cliente.getCpf(), cliente.getEndereco(), cliente.getProfissao(),
				cliente.getRendimentos().stream().map(RendimentoDTO::de).toList());
	}

}
