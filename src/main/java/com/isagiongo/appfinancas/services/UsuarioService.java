package com.isagiongo.appfinancas.services;

import com.isagiongo.appfinancas.model.entities.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
}
