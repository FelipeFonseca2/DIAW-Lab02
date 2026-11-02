package br.pucminas.aluguel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AvaliacaoRequest(
		@NotNull Boolean aprovado,
		@Size(max = 500) String parecer) {
}
