package com.isagiongo.appfinancas.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.isagiongo.appfinancas.exceptions.AutenticacaoException;
import com.isagiongo.appfinancas.exceptions.RegraNegocioException;
import com.isagiongo.appfinancas.model.entities.Usuario;
import com.isagiongo.appfinancas.repositories.UsuarioRepository;
import com.isagiongo.appfinancas.services.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	private UsuarioServiceImpl usuarioService;
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
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
	
	@Test
	public void deveAutenticarUsuario() {
		Usuario usuario = criaUsuario();
		
		when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
		
		Usuario resultado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());
		
		assertNotNull(resultado);
	}
	
	@Test
	public void deveRetornarExcecaoSeUsuarioNaoForEncontrado() {
		Usuario usuario = criaUsuario();
		
		when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(AutenticacaoException.class,
				()-> usuarioService.autenticar(usuario.getEmail(), usuario.getSenha()));
		
		assertTrue(exception.getMessage().contains("Usuário não encontrado"));
	}
	
	@Test
	public void deveRetornarExcecaoSeSenhaNaoEstiverCorreta() {
		Usuario usuario = criaUsuario();
		
		when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
		
		Exception exception = assertThrows(AutenticacaoException.class,
				()-> usuarioService.autenticar(usuario.getEmail(), "55555"));
		
		assertTrue(exception.getMessage().contains("Senha inválida"));
	}
	
	@Test
	public void deveSalvarUsuario() {
		doNothing().when(usuarioService).validarEmail("teste@gmail.com");
		Usuario usuario = criaUsuario();
		
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
		
		Usuario usuarioSalvo = usuarioService.salvarUsuario(new Usuario());
		
		assertEquals(1, usuarioSalvo.getId());
		assertEquals("Isadora", usuarioSalvo.getNome());
		assertEquals("isa@gmail.com", usuarioSalvo.getEmail());
		assertEquals("1234", usuarioSalvo.getSenha());
	}
	
	@Test
	public void naoDeveSalvarUsuarioParaEmailInvalido() {
		Usuario usuario = criaUsuario();
		
		doThrow(RegraNegocioException.class).when(usuarioService).validarEmail("isa@gmail.com");
		
		assertThrows(RegraNegocioException.class, () -> usuarioService.salvarUsuario(usuario));
		
		verify(usuarioRepository, never()).save(usuario);
	}
	
	public static Usuario criaUsuario() {
		return Usuario.builder().nome("Isadora").email("isa@gmail.com").id(1L).senha("1234").build();
	}

}
