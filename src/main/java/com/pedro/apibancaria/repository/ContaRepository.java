package com.pedro.apibancaria.repository;

import com.pedro.apibancaria.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    boolean existsByClienteId(Long clienteId);
}
