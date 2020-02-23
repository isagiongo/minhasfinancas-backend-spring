package com.isagiongo.appfinancas.services.impl;

import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		if(usuarioRepository.existsByEmail(email)) {
			throw new RegraNegocioException("Email já cadastrado!");
		}
	}

}
