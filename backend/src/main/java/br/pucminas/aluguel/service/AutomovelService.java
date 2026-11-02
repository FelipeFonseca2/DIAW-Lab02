package br.pucminas.aluguel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pucminas.aluguel.dto.AutomovelResponse;
import br.pucminas.aluguel.repository.AutomovelRepository;

@Service
public class AutomovelService {

	private final AutomovelRepository automovelRepository;

	public AutomovelService(AutomovelRepository automovelRepository) {
		this.automovelRepository = automovelRepository;
	}

	@Transactional(readOnly = true)
	public List<AutomovelResponse> listar() {
		return automovelRepository.findAllByOrderByMarcaAscModeloAsc().stream().map(AutomovelResponse::de).toList();
	}

}
