package br.pucminas.aluguel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucminas.aluguel.model.PedidoAluguel;
import br.pucminas.aluguel.model.StatusPedido;

public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, Long> {

	List<PedidoAluguel> findAllByOrderByDataCriacaoDesc();

	List<PedidoAluguel> findByClienteIdOrderByDataCriacaoDesc(Long clienteId);

	boolean existsByClienteIdAndStatus(Long clienteId, StatusPedido status);

	void deleteByClienteId(Long clienteId);

}
