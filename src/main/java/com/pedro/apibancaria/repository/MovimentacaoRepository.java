package com.pedro.apibancaria.repository;

import com.pedro.apibancaria.entities.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
}
