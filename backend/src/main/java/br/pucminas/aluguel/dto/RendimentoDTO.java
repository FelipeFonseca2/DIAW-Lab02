package br.pucminas.aluguel.dto;

import java.math.BigDecimal;

import br.pucminas.aluguel.model.Rendimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RendimentoDTO(
		@NotBlank String entidadeEmpregadora,
		@NotNull @PositiveOrZero BigDecimal valor) {

	public static RendimentoDTO de(Rendimento rendimento) {
		return new RendimentoDTO(rendimento.getEntidadeEmpregadora(), rendimento.getValor());
	}

	public Rendimento toModel() {
		return new Rendimento(entidadeEmpregadora, valor);
	}

}
