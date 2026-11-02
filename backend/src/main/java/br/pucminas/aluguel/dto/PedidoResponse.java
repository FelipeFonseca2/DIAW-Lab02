package br.pucminas.aluguel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.pucminas.aluguel.model.PedidoAluguel;
import br.pucminas.aluguel.model.StatusPedido;

public record PedidoResponse(
		Long id,
		Long clienteId,
		String clienteNome,
		AutomovelResponse automovel,
		LocalDate dataInicio,
		LocalDate dataFim,
		LocalDateTime dataCriacao,
		StatusPedido status,
		String parecer,
		String agenteAvaliador,
		ContratoResponse contrato) {

	public static PedidoResponse de(PedidoAluguel pedido) {
		String avaliador = pedido.getAgenteAvaliador() != null ? pedido.getAgenteAvaliador().getNome() : null;
		ContratoResponse contrato = pedido.getContrato() != null ? ContratoResponse.de(pedido.getContrato()) : null;
		return new PedidoResponse(pedido.getId(), pedido.getCliente().getId(), pedido.getCliente().getNome(),
				AutomovelResponse.de(pedido.getAutomovel()), pedido.getDataInicio(), pedido.getDataFim(),
				pedido.getDataCriacao(), pedido.getStatus(), pedido.getParecer(), avaliador, contrato);
	}

}
