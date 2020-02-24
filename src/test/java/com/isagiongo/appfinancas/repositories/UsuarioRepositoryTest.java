package com.isagiongo.appfinancas.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.isagiongo.appfinancas.model.entities.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveRetornarTrueSeUsuarioTiverEmailCadastrado() {
		Usuario user = new Usuario();
		user.setEmail("usuario@gmail.com");
		entityManager.persist(user);
		
		boolean result = usuarioRepository.existsByEmail("usuario@gmail.com");
		
		assertTrue(result);				
	}
	
	@Test
	public void deveRetornarFalseSeUsuarioNaoTiverEmailCadastrado() {
		
		boolean result = usuarioRepository.existsByEmail("usuario@gmail.com");
		
		assertFalse(result);				
	}
	
	@Test
	public void devePersistirUsuarioNaBaseDados() {
		Usuario usuario = criarUsuario();
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		assertNotNull(usuarioSalvo.getId());
	}
	
	@Test
	public void deveBuscarUsarioPorEmail() {
		Usuario usuario = criarUsuario();
		
		entityManager.persist(usuario);
		
		assertTrue(usuarioRepository.findByEmail("joanamaga@gmail.com").isPresent());
	}
	
	@Test
	public void deveRetornarVazioUsuarioQueEmailNaoExiste() {
		assertFalse(usuarioRepository.findByEmail("joanamaga@gmail.com").isPresent());
	}
	
	public static Usuario criarUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome("Joana Magalhães");
		usuario.setEmail("joanamaga@gmail.com");
		usuario.setSenha("senha1234");
		return usuario;
	}

}
