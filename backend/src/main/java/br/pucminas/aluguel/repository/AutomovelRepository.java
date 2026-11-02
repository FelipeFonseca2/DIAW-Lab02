package br.pucminas.aluguel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucminas.aluguel.model.Automovel;

public interface AutomovelRepository extends JpaRepository<Automovel, Long> {

	List<Automovel> findAllByOrderByMarcaAscModeloAsc();

}
