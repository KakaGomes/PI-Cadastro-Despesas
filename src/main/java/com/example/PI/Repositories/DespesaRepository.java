package com.example.PI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PI.Entities.DespesaEntities;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntities, Long> {
}
