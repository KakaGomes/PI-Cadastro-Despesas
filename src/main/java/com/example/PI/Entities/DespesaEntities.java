package com.example.PI.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compras") // Certifique-se de que a tabela correta está sendo usada
public class DespesaEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private FornecedorEntities fornecedor;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    private String numeroPedido;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_pedido", nullable = false)
    private LocalDate dataPedido;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCompra status = StatusCompra.PENDENTE;

    @Column(name = "comprador", nullable = false)
    private String comprador;

    public enum StatusCompra {
        PENDENTE, APROVADA, CANCELADA
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FornecedorEntities getFornecedor() { return fornecedor; }
    public void setFornecedor(FornecedorEntities fornecedor) { this.fornecedor = fornecedor; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }

    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }

    public LocalDate getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDate dataEntrega) { this.dataEntrega = dataEntrega; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public StatusCompra getStatus() { return status; }
    public void setStatus(StatusCompra status) { this.status = status; }

    public String getComprador() { return comprador; }
    public void setComprador(String comprador) { this.comprador = comprador; }
}
