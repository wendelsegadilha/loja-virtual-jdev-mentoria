package xyz.wendelsegadilha.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import xyz.wendelsegadilha.lojavirtual.model.Usuario;
import xyz.wendelsegadilha.lojavirtual.repository.UsuarioRepository;

public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		/* Recebe o login para consulta */
		Usuario usuario = usuarioRepository.findUserByLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());

	}

}
