package com.isagiongo.appfinancas.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.isagiongo.appfinancas.model.entities.Usuario;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@BeforeEach
	public void setUp() {
		usuarioRepository.deleteAll();
	}
	
	@Test
	public void deveRetornarTrueSeUsuarioTiverEmailCadastrado() {
		Usuario user = new Usuario();
		user.setEmail("usuario@gmail.com");
		
		usuarioRepository.save(user);
		
		boolean result = usuarioRepository.existsByEmail("usuario@gmail.com");
		
		assertTrue(result);				
	}
	
	@Test
	public void deveRetornarFalseSeUsuarioNaoTiverEmailCadastrado() {
		
		boolean result = usuarioRepository.existsByEmail("usuario@gmail.com");
		
		assertFalse(result);				
	}

}
