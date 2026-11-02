package br.pucminas.aluguel.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record PedidoRequest(
		@NotNull Long automovelId,
		@NotNull LocalDate dataInicio,
		@NotNull LocalDate dataFim) {
}
