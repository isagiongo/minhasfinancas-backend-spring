package com.isagiongo.appfinancas.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isagiongo.appfinancas.api.dtos.UsuarioDTO;
import com.isagiongo.appfinancas.exceptions.AutenticacaoException;
import com.isagiongo.appfinancas.exceptions.RegraNegocioException;
import com.isagiongo.appfinancas.model.entities.Usuario;
import com.isagiongo.appfinancas.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = Usuario
				.builder()
				.nome(usuarioDTO.getNome())
				.email(usuarioDTO.getEmail())
				.senha(usuarioDTO.getSenha())
				.build();
		try {
			return new ResponseEntity(usuarioService.salvarUsuario(usuario), HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			return ResponseEntity.ok(usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
		} catch (AutenticacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
