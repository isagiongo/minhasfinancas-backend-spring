package com.isagiongo.appfinancas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isagiongo.appfinancas.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email);
	
}
