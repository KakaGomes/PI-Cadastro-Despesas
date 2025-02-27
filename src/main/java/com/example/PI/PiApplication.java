package com.example.PI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.PI.Service.UsuarioService;

@SpringBootApplication
public class PiApplication implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(PiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        usuarioService.atualizarSenhasParaCriptografadas(); // Atualiza as senhas ao iniciar o sistema
        System.out.println("Senhas verificadas e atualizadas, se necessário.");
    }
}
