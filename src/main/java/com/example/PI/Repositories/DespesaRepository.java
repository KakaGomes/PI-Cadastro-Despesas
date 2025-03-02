package com.example.PI.Repositories;

import com.example.PI.Entities.DespesaEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntities, Long> {

    List<DespesaEntities> findByStatus(DespesaEntities.StatusCompra status);

    @Query("SELECT d FROM DespesaEntities d " +
           "WHERE (:fornecedor IS NULL OR LOWER(d.fornecedor.nome) LIKE LOWER(CONCAT('%', :fornecedor, '%'))) " +
           "AND (:inicio IS NULL OR d.dataPedido >= :inicio) " +
           "AND (:fim IS NULL OR d.dataPedido <= :fim)")
    List<DespesaEntities> filtrarCompras(
        @Param("fornecedor") String fornecedor,
        @Param("inicio") LocalDate inicio,
        @Param("fim") LocalDate fim);
}
