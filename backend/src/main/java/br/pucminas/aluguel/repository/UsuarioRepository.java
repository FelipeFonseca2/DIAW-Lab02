package br.pucminas.aluguel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucminas.aluguel.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByLogin(String login);

	boolean existsByLogin(String login);

	boolean existsByLoginAndIdNot(String login, Long id);

}
