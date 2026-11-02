package br.pucminas.aluguel.dto;

import br.pucminas.aluguel.model.Automovel;

public record AutomovelResponse(
		Long id,
		String matricula,
		Integer ano,
		String marca,
		String modelo,
		String placa,
		String proprietario) {

	public static AutomovelResponse de(Automovel automovel) {
		String proprietario = automovel.getProprietario() != null ? automovel.getProprietario().getNome() : null;
		return new AutomovelResponse(automovel.getId(), automovel.getMatricula(), automovel.getAno(),
				automovel.getMarca(), automovel.getModelo(), automovel.getPlaca(), proprietario);
	}

}
