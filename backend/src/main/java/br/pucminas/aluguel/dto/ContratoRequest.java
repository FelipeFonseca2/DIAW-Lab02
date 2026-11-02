package br.pucminas.aluguel.dto;

import br.pucminas.aluguel.model.TipoProprietario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** O crédito é opcional e só pode ser informado por bancos. */
public record ContratoRequest(
		@NotNull TipoProprietario tipoProprietario,
		@Valid CreditoRequest credito) {
}
