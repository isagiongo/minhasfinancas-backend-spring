package com.isagiongo.appfinancas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isagiongo.appfinancas.model.entities.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
