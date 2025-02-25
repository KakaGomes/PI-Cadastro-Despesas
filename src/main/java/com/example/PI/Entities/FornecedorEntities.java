package com.example.PI.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fornecedores")
public class FornecedorEntities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL)
    private List<DespesaEntities> despesas;

    public FornecedorEntities() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public List<DespesaEntities> getDespesas() { return despesas; }
    public void setDespesas(List<DespesaEntities> despesas) { this.despesas = despesas; }
}
