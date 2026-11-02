package br.pucminas.aluguel.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.pucminas.aluguel.model.Contrato;
import br.pucminas.aluguel.model.ContratoCredito;
import br.pucminas.aluguel.model.TipoProprietario;

public record ContratoResponse(
		Long id,
		LocalDate dataAssinatura,
		TipoProprietario tipoProprietario,
		String agente,
		Credito credito) {

	public record Credito(String banco, BigDecimal valor, BigDecimal taxaJurosMensal, Integer prazoMeses) {
	}

	public static ContratoResponse de(Contrato contrato) {
		ContratoCredito c = contrato.getContratoCredito();
		Credito credito = c == null ? null
				: new Credito(c.getBanco().getNome(), c.getValor(), c.getTaxaJurosMensal(), c.getPrazoMeses());
		return new ContratoResponse(contrato.getId(), contrato.getDataAssinatura(), contrato.getTipoProprietario(),
				contrato.getAgente().getNome(), credito);
	}

}
