package com.isagiongo.appfinancas.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.isagiongo.appfinancas.exceptions.RegraNegocioException;
import com.isagiongo.appfinancas.model.entities.Usuario;
import com.isagiongo.appfinancas.repositories.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void deveValidarEmail() {
		usuarioRepository.deleteAll();
		
		assertDoesNotThrow(() -> usuarioService.validarEmail("teste@gmail.com"));
	}
	
	@Test
	public void deveRetornarExceptionSeEmailExistir() {
		Usuario usuario = new Usuario();
		usuario.setEmail("usuario@gmail.com");
		usuarioRepository.save(usuario);
		
		assertThrows(RegraNegocioException.class,() -> usuarioService.validarEmail("usuario@gmail.com"));
	}

}
