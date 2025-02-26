package com.example.PI.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios") // Certifique-se de que o nome da tabela está correto
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, nullable = false) // Aqui pode estar o problema!
    private String login;  // ← O campo pode ser "login" e não "usuario"

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String tipo;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }  // ← Ajuste aqui também
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
