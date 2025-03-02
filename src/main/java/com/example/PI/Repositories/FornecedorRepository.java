package com.example.PI.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.example.PI.Entities.FornecedorEntities;

public interface FornecedorRepository extends JpaRepository<FornecedorEntities, Long> {
    Optional<FornecedorEntities> findByCnpj(String cnpj);
    @Query("SELECT f, " +
       "(SELECT COALESCE(SUM(d.valor), 0) FROM DespesaEntities d WHERE d.fornecedor = f AND d.status = com.example.PI.Entities.DespesaEntities.StatusCompra.APROVADA) AS totalAprovado, " +
       "(SELECT COALESCE(SUM(d.valor), 0) FROM DespesaEntities d WHERE d.fornecedor = f AND d.status = com.example.PI.Entities.DespesaEntities.StatusCompra.PENDENTE) AS totalPendente " +
       "FROM FornecedorEntities f " +
       "WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :termo, '%')) " +
       "OR f.cnpj LIKE CONCAT('%', :termo, '%')")
List<Object[]> buscarPorNomeOuCnpjComTotais(@Param("termo") String termo);

}
