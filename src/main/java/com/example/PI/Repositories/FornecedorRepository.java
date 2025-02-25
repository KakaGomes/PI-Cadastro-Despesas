package com.example.PI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PI.Entities.FornecedorEntities;

public interface FornecedorRepository extends JpaRepository<FornecedorEntities, Long> {
}
