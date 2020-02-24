package com.isagiongo.appfinancas.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.isagiongo.appfinancas.exceptions.AutenticacaoException;
import com.isagiongo.appfinancas.exceptions.RegraNegocioException;
import com.isagiongo.appfinancas.model.entities.Usuario;
import com.isagiongo.appfinancas.repositories.UsuarioRepository;
import com.isagiongo.appfinancas.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if (!usuario.isPresent()) {
			throw new AutenticacaoException("Usuário não encontrado");
		}
		
		if (!usuario.get().getSenha().equals(senha)) {
			throw new AutenticacaoException("Senha inválida");
		}
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		if(usuarioRepository.existsByEmail(email)) {
			throw new RegraNegocioException("Email já cadastrado!");
		}
	}

}
