package com.isagiongo.appfinancas.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.isagiongo.appfinancas.exceptions.RegraNegocioException;
import com.isagiongo.appfinancas.repositories.UsuarioRepository;
import com.isagiongo.appfinancas.services.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	private UsuarioService usuarioService;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@BeforeEach
	public void setUp() {
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
	}
	
	@Test
	public void deveValidarEmail() {
		when(usuarioRepository.existsByEmail("teste@gmail.com")).thenReturn(Boolean.FALSE);
		
		assertDoesNotThrow(() -> usuarioService.validarEmail("teste@gmail.com"));
	}
	
	@Test
	public void deveRetornarExceptionSeEmailExistir() {
		when(usuarioRepository.existsByEmail("usuario@gmail.com")).thenReturn(Boolean.TRUE);
		
		assertThrows(RegraNegocioException.class,() -> usuarioService.validarEmail("usuario@gmail.com"));
	}

}
