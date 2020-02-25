package com.isagiongo.appfinancas.api.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsuarioDTO {

	private String nome;
	private String email;
	private String senha;
}
