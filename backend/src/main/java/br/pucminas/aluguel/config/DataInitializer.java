package br.pucminas.aluguel.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.pucminas.aluguel.model.Agente;
import br.pucminas.aluguel.model.Automovel;
import br.pucminas.aluguel.model.Banco;
import br.pucminas.aluguel.model.Cliente;
import br.pucminas.aluguel.model.Empresa;
import br.pucminas.aluguel.model.Rendimento;
import br.pucminas.aluguel.repository.AutomovelRepository;
import br.pucminas.aluguel.repository.UsuarioRepository;

/** Popula um banco vazio com agentes, um cliente e automóveis de demonstração. */
@Component
public class DataInitializer implements CommandLineRunner {

	private final UsuarioRepository usuarioRepository;
	private final AutomovelRepository automovelRepository;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(UsuarioRepository usuarioRepository, AutomovelRepository automovelRepository,
			PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.automovelRepository = automovelRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (usuarioRepository.count() > 0) {
			return;
		}

		Empresa empresa = agente(new Empresa(), "empresa", "Locadora Movimento", "12.345.678/0001-90");
		Banco banco = agente(new Banco(), "banco", "Banco Horizonte", "98.765.432/0001-10");

		Cliente cliente = new Cliente();
		cliente.setLogin("cliente");
		cliente.setSenha(passwordEncoder.encode("cliente123"));
		cliente.setNome("Maria Souza");
		cliente.setRg("MG-12.345.678");
		cliente.setCpf("123.456.789-09");
		cliente.setEndereco("Rua das Flores, 100 - Belo Horizonte/MG");
		cliente.setProfissao("Engenheira");
		cliente.setRendimentos(List.of(new Rendimento("Construtora Alfa", new BigDecimal("8500.00"))));
		usuarioRepository.save(cliente);

		automovelRepository.saveAll(List.of(
				new Automovel("MAT-0001", 2024, "Chevrolet", "Onix", "RTA1B23", empresa),
				new Automovel("MAT-0002", 2023, "Volkswagen", "T-Cross", "QWE4C56", empresa),
				new Automovel("MAT-0003", 2025, "Toyota", "Corolla", "BRA2E19", banco),
				new Automovel("MAT-0004", 2022, "Fiat", "Pulse", "PUC7D89", empresa)));
	}

	private <T extends Agente> T agente(T agente, String login, String nome, String cnpj) {
		agente.setLogin(login);
		agente.setSenha(passwordEncoder.encode(login + "123"));
		agente.setNome(nome);
		agente.setCnpj(cnpj);
		return usuarioRepository.save(agente);
	}

}
