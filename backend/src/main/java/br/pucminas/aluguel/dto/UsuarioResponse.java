package br.pucminas.aluguel.dto;

import br.pucminas.aluguel.model.Agente;
import br.pucminas.aluguel.model.Perfil;
import br.pucminas.aluguel.model.TipoProprietario;
import br.pucminas.aluguel.model.Usuario;

public record UsuarioResponse(Long id, String login, String nome, Perfil perfil, TipoProprietario tipoAgente) {

	public static UsuarioResponse de(Usuario usuario) {
		TipoProprietario tipo = usuario instanceof Agente agente ? agente.getTipo() : null;
		return new UsuarioResponse(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getPerfil(), tipo);
	}

}
