package br.pucminas.aluguel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.aluguel.dto.AutomovelResponse;
import br.pucminas.aluguel.service.AutomovelService;

@RestController
@RequestMapping("/api/automoveis")
public class AutomovelController {

	private final AutomovelService automovelService;

	public AutomovelController(AutomovelService automovelService) {
		this.automovelService = automovelService;
	}

	@GetMapping
	public List<AutomovelResponse> listar() {
		return automovelService.listar();
	}

}
