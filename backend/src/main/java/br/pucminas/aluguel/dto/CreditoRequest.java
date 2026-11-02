package br.pucminas.aluguel.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreditoRequest(
		@NotNull @Positive BigDecimal valor,
		@NotNull @PositiveOrZero BigDecimal taxaJurosMensal,
		@NotNull @Positive Integer prazoMeses) {
}
