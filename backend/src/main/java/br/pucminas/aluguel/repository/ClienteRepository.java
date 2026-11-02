package br.pucminas.aluguel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucminas.aluguel.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findAllByOrderByNomeAsc();

	boolean existsByCpf(String cpf);

	boolean existsByCpfAndIdNot(String cpf, Long id);

}
